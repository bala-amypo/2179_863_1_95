package com.example.demo.config;

import com.example.demo.servlet.SimpleHelloServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<SimpleHelloServlet> helloServlet() {
        return new ServletRegistrationBean<>(
                new SimpleHelloServlet(),
                "/hello-servlet"
        );
    }
}
