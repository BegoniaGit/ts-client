package site.yan.app.config;

import org.springframework.context.annotation.Bean;
import site.yan.web.filter.TSWebFilter;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class Start {

    @Bean
    public Filter getFilter(){
        return new TSWebFilter();
    }
}
