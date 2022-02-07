package com.study.jpa.study.jpa.config;

import com.study.jpa.study.jpa.servlet.HelloServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

public class ServletRegistrationConfig {
    public ServletRegistrationBean<HelloServlet> getServletRegistrationBean() {
        HelloServlet helloServlet = new HelloServlet();
        ServletRegistrationBean<HelloServlet> registrationBean = new ServletRegistrationBean<>(helloServlet);
        registrationBean.addUrlMappings("/Hello");
        return registrationBean;
    }
}
