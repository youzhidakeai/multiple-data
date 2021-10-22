package com.dc.multiple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MultipleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleApplication.class, args);
    }

}
