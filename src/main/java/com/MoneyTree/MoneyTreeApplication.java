package com.MoneyTree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.MoneyTree.repository")
@EntityScan(basePackages = "com.MoneyTree.model")
public class MoneyTreeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoneyTreeApplication.class, args);
    }
}