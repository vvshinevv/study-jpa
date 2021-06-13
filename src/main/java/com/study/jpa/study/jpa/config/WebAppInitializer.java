package com.study.jpa.study.jpa.config;

import com.study.jpa.study.jpa.servlet.HelloServlet;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
public class WebAppInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext ctx
                = new AnnotationConfigWebApplicationContext();

        ctx.setServletContext(container);
        ServletRegistration.Dynamic servlet = container.addServlet("helloServlet", new HelloServlet());
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/Hello");
    }
}
