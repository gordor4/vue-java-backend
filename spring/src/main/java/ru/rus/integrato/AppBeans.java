package ru.rus.integrato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.rus.integrato.filter.AuthFilter;
import ru.rus.integrato.utils.KeyGenerator;

@Configuration
@EnableTransactionManagement
@ComponentScan("ru.rus.integrato")
@EntityScan("ru.rus.integrato.domain")
@EnableJpaRepositories("ru.rus.integrato.repository")
public class AppBeans {
    @Autowired
    private KeyGenerator keyGenerator;

    @Bean
    public FilterRegistrationBean<AuthFilter> jwtFilter(){
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(keyGenerator));
        registrationBean.addUrlPatterns("/users/*", "/board/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8081");
        config.addAllowedHeader("accept");
        config.addAllowedHeader("authorization");
        config.addAllowedHeader("content-type");
        config.addAllowedHeader("x-requested-with");
        config.addAllowedHeader("Access-Control-Max-Age");
        config.setMaxAge(1L);

        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
