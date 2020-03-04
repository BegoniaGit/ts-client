package site.yan.core.configer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.yan.core.api.ExportAPI;
import site.yan.core.helper.RecordContextHolder;

@Configuration
@EnableConfigurationProperties(Properties.class)
@ConditionalOnProperty(prefix = "ts", name="enable" ,havingValue="true")
public class Boot {

    // register export Api "/trace"
    @Bean
    public ExportAPI exportAPI(){
        return new ExportAPI();
    }

    @Bean
    public RecordContextHolder recordContextHolder(){
        return new RecordContextHolder();
    }
}
