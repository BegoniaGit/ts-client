package site.yan.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.yan.mysql.interceptor.TSQueryInterceptor;
import site.yan.web.filter.TSWebFilter;

import javax.servlet.Filter;

@Configuration
public class Start {

    @Bean
    public Filter getFilter() {
        return new TSWebFilter();
    }

}
