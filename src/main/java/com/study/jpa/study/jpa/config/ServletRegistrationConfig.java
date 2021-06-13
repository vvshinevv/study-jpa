package com.study.jpa.study.jpa.config;

import com.study.jpa.study.jpa.servlet.HelloServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class ServletRegistrationConfig {


    public ServletRegistrationBean<HelloServlet> getServletRegistrationBean() {
        HelloServlet helloServlet = new HelloServlet();

        ServletRegistrationBean<HelloServlet> registrationBean = new ServletRegistrationBean<>(helloServlet);

        registrationBean.addUrlMappings("/Hello");

        return registrationBean;
    }
}
