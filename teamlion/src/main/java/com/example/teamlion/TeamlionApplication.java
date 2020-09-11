package com.example.teamlion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication
public class TeamlionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamlionApplication.class, args);
        log.info("Application running");
    }
}
