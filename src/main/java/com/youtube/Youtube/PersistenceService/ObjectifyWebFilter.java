package com.youtube.Youtube.PersistenceService;


import com.googlecode.objectify.ObjectifyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectifyWebFilter extends ObjectifyFilter {

    @Bean
    public FilterRegistrationBean<ObjectifyWebFilter> objectifyFilter() {
        FilterRegistrationBean<ObjectifyWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ObjectifyWebFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
