package com.artemgggi.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApplication  {

    public static void main(String[] args) {
        //System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication.run(WebshopApplication.class, args);
    }
}
