package com.sivalabs.demo;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.domain.io.UserInput;

@Agent(
    name = "url-data-fetcher",
    description = "Fetch content from internet for the given URL",
    version = "1.0.0")
public class UrlDataFetcherAgent {

    @AchievesGoal(description = "Fetch content from internet for the given URL")
    @Action
    public PageContent fetchContent(UserInput userInput, OperationContext context) {
        return context.ai()
                .withDefaultLlm()
                .withToolGroup(CoreToolGroups.WEB)
                .createObjectIfPossible(
                        """
                                Fetch the content of the URL from the following user request:
                                
                                %s
                                """.formatted(userInput.getContent()), PageContent.class
                );
    }

    public record PageContent(String content) {
    }
}
