package com.email.data.inserter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.email.data.inserter.*")
public class DataInserterApplication {

    public static void main(String[] args) {

        SpringApplication.run(DataInserterApplication.class,args);
    }
}
