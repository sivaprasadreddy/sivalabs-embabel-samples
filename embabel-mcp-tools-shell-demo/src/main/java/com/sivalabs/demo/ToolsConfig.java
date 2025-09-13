package com.sivalabs.demo;

import com.embabel.agent.core.ToolGroup;
import com.embabel.agent.core.ToolGroupDescription;
import com.embabel.agent.core.ToolGroupPermission;
import com.embabel.agent.tools.mcp.McpToolGroup;
import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

import static com.sivalabs.demo.Constants.TOOLS_GROUP_YOUTUBE;

@Configuration
public class ToolsConfig {


    private final List<McpSyncClient> mcpSyncClients;

    public ToolsConfig(List<McpSyncClient> mcpSyncClients) {
        this.mcpSyncClients = mcpSyncClients;
    }

    @Bean
    ToolGroup youtubeToolsGroup() {
        return new McpToolGroup(
                ToolGroupDescription.create(
                "YouTube information tools", TOOLS_GROUP_YOUTUBE),
                "npx",
                "youtube-mcp-tools",
                Set.of(ToolGroupPermission.INTERNET_ACCESS),
                mcpSyncClients,
                it -> {
                    String toolName = it.getToolDefinition().name().toLowerCase();
                    return toolName.contains("youtube");

                    // To selectively expose tools
                    /*
                    return toolName.contains("videos_searchvideos")
                            || toolName.contains("channels_listvideos");
                    */
                });
    }
}
