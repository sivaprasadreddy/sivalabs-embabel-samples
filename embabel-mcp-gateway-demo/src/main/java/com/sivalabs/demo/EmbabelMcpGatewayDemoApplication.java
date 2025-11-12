package com.sivalabs.demo;

import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.config.annotation.LocalModels;
import com.embabel.agent.config.annotation.LoggingThemes;
import com.embabel.agent.config.annotation.McpServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAgents(
        loggingTheme = LoggingThemes.STAR_WARS,
        localModels = {LocalModels.OLLAMA, LocalModels.DOCKER},
        mcpServers = {McpServers.DOCKER, McpServers.DOCKER_DESKTOP}
)
public class EmbabelMcpGatewayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbabelMcpGatewayDemoApplication.class, args);
    }

}
