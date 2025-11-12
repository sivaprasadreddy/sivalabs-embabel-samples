package com.sivalabs.demo;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.core.CoreToolGroups;

@Agent(
    name = "github-agent",
    description = "Get information from GitHub",
    version = "1.0.0")
public class GitHubAgent {

    @AchievesGoal(description = "Fetch results from GitHub for the user request")
    @Action
    public SearchResult search(SearchRequest request, OperationContext context) {
        return context.ai()
                .withDefaultLlm()
                .withToolGroup(CoreToolGroups.WEB)
                .createObjectIfPossible(
                        """
                                Fetch the results from GitHub for the user request:
                                
                                %s
                                """.formatted(request.query()), SearchResult.class
                );
    }
}
