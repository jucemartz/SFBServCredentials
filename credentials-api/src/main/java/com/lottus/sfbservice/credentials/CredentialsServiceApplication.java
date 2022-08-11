package com.lottus.sfbservice.credentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lottus.sfbservice.credentials*"})
@EnableFeignClients
public class CredentialsServiceApplication {


    /**
     * main.
     *
     * @param args the array of string.
     */
    public static void main(String[] args) {
        SpringApplication.run(CredentialsServiceApplication.class, args);
    }

}
