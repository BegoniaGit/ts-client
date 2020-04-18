package site.yan.core.configer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.yan.core.api.ExportAPI;
import site.yan.core.data.Host;
import site.yan.core.delayed.RecordStash;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.TimeStamp;

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

    // register export Api "/trace"
    @Bean
    public ExportAPI exportAPI() {
        return new ExportAPI();
    }

    @Bean
    public RecordContextHolder recordContextHolder() {
        long startStamp = TimeStamp.stamp();
        if (properties.isEnable() == false) {
            logger.info("追踪系统被禁止启动 !");
            return null;
        }
        logger.info("追踪系统开始启动...");
        // 设置配置参数值
        TSProperties.setValues(properties);
        if (!checkProperties()) {
            return null;
        }

        // 获取本机 ip
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException exc) {
            logger.error("获取本级通信地址异常", exc);
            exc.printStackTrace();
        }

        // config the server information
        Host serverHost = new Host(TSProperties.getServerName(), address.getHostAddress(), serverPort);
        RecordContextHolder.setHost(serverHost);
        RecordContextHolder.setStage(TSProperties.getStage());
        logger.info("获取本级通信地址完成");

        // 延迟队列数据转移到疾苦缓存区线程启动
        RecordStash.receive();

        // 主动上报数据线程
        if (TSProperties.isAutoReport()) {
            logger.info("启动主动上报记录数据线程完成");
            RecordStash.send();
        }
        logger.info("追踪系统基础配置启动成功，耗时 " + (TimeStamp.stamp() - startStamp) + " 毫秒");
        return new RecordContextHolder();
    }

    private boolean checkProperties() {

        if (TSProperties.isAutoReport() && !TSProperties.getAutoReportUrl().startsWith("http")) {
            logger.error("配置参数检查:主动上报数据应设置正确的上报url链接");
            return false;
        }
        logger.info("配置参数检查完成");
        return true;
    }
}
