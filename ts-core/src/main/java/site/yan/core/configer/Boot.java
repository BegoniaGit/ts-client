package site.yan.core.configer;

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

    // register export Api "/trace"
    @Bean
    public ExportAPI exportAPI() {
        return new ExportAPI();
    }

    @Bean
    public RecordContextHolder recordContextHolder() {
        TSProperties.setValues(properties.isEnable(), properties.getServerName(), properties.getStage());
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // config the server information
        Host serverHost = new Host(TSProperties.getServerName(), address.getHostAddress(), serverPort);
        RecordContextHolder.setHost(serverHost);
        RecordContextHolder.setStage(TSProperties.getStage());

        // start record stash
        RecordStash.receive();
        RecordStash.send();
        return new RecordContextHolder();
    }
}
