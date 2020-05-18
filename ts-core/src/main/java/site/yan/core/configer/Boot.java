package site.yan.core.configer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.yan.core.api.TraceApiBoot;
import site.yan.core.data.Host;
import site.yan.core.delayed.RecordStash;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.SpringUtil;
import site.yan.core.utils.TimeUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableConfigurationProperties(Properties.class)
@ConditionalOnProperty(prefix = "ts", name = "enable", havingValue = "true")
public class Boot {

    @Autowired
    Properties properties;

    @Value("${server.port}")
    private Integer serverPort;

    Logger logger = LoggerFactory.getLogger(Boot.class);

    @Bean
    public ApplicationContextAware applicationContextAware() {
        return new SpringUtil();
    }

    @Bean
    public RecordContextHolder recordContextHolder() throws Exception {
        long startStamp = TimeUtil.stamp();
        if (properties.isEnable() == false) {
            logger.info("The tracking system is forbidden to start!");
            return null;
        }
        logger.info("The tracking system starts...");
        // Set configuration parameter values
        TSProperties.setValues(properties);
        if (!checkProperties()) {
            logger.info("parameter configuration error，tracking system termination");
            throw new Exception("parameter configuration error，tracking system termination");
        }

        // Get local ip
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException exc) {
            logger.error("Abnormal access to this level of communication address", exc);
            throw exc;
        }

        // config the server information
        Host serverHost = new Host(TSProperties.getServerName(), address.getHostAddress(), serverPort);
        RecordContextHolder.setHost(serverHost);
        RecordContextHolder.setStage(TSProperties.getStage());
        logger.info("Complete the communication address of this host");

        // register export Api "/trace"
        if (!TSProperties.isAutoReport()) {
            TraceApiBoot.start(TSProperties.getTracePort());
        }

        // Delaying the transfer of queue data to the start of the thread in the painful cache
        RecordStash.receive();

        // Actively report data thread
        if (TSProperties.isAutoReport()) {
            if ("native".equals(TSProperties.getMode()) && !TSProperties.getAutoReportUrl().startsWith("http")) {
                logger.info("incorrectly reported data address setting error");
            }
            RecordStash.send();
            logger.info("Finish to start the active report data logging thread");
        }
        logger.info("The basic configuration of the tracking system started successfully，time consuming " + (TimeUtil.stamp() - startStamp) + " ms");
        return new RecordContextHolder();
    }

    private boolean checkProperties() {

        if (TSProperties.isAutoReport() && !TSProperties.getAutoReportUrl().startsWith("http")) {
            logger.error("Configuration parameter check: Active reporting data should be set to the correct reporting URL link");
            return false;
        }
        logger.info("Complete configuration parameter check");
        return true;
    }

    @Bean
    public Queue Queue() {
        return new Queue(TSProperties.getQueueName());
    }
}
