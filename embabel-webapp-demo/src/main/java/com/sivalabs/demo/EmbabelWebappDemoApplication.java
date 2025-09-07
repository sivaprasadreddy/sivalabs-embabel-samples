package com.sivalabs.demo;

import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.config.annotation.LocalModels;
import com.embabel.agent.config.annotation.LoggingThemes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAgents(
    loggingTheme = LoggingThemes.STAR_WARS,
    localModels = {LocalModels.OLLAMA, LocalModels.DOCKER}
)
public class EmbabelWebappDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbabelWebappDemoApplication.class, args);
    }

}
