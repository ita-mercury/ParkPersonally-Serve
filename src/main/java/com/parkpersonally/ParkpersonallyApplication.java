package com.parkpersonally;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableSwagger2
@SpringBootApplication
@EnableWebSecurity
public class ParkpersonallyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkpersonallyApplication.class, args);
    }

}
