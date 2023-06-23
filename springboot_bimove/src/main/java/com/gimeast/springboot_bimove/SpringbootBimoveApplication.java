package com.gimeast.springboot_bimove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootBimoveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBimoveApplication.class, args);
    }

}
