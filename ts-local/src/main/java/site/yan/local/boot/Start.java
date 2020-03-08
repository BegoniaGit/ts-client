package site.yan.local.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.yan.local.advice.AnnotatedMethodTracedAdvice;

@Configuration
public class Start {

    @Bean
    public AnnotatedMethodTracedAdvice getAnnotatedMethodTracedAdvice() {
        return new AnnotatedMethodTracedAdvice();
    }
}
