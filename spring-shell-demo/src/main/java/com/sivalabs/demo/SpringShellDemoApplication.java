package com.sivalabs.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
public class SpringShellDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringShellDemoApplication.class, args);
    }

}
