package com.email.core.validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.email.core.validator.*")
public class EmailCoreApplication {


    public static void main(String[] args) {

        SpringApplication.run(EmailCoreApplication.class,args);
    }
}
