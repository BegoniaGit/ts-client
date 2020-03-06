package site.yan.mysql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TSMySQLBoot {

    @Bean
    public String getTSMySQLStatementInterceptor(){
        new TSMySQLStatementInterceptor();
        return new String();
    }
}
