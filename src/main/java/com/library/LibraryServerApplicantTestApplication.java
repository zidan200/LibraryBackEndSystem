package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.library.util.LoggingInterceptor;

@SpringBootApplication
public class LibraryServerApplicantTestApplication extends WebMvcConfigurerAdapter
{

    public static void main(String[] args)
    {
        SpringApplication.run(LibraryServerApplicantTestApplication.class, args);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }

}
