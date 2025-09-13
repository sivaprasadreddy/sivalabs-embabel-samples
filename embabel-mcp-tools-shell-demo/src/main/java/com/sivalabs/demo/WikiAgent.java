package com.sivalabs.demo;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.domain.io.UserInput;

@Agent(description = "Find information from wikipedia")
public class WikiAgent {
    private final int wordCount = 200;

    @Action
    @AchievesGoal(description = "Find information from wikipedia according to the user's request.")
    Report getInfoFromWikipedia(UserInput userInput, OperationContext context) {
        return context.ai()
                .withDefaultLlm()
                .withToolGroup(CoreToolGroups.WEB)
                .createObject("""
                                Get the information on the following subject from Wikipedia in %d words:
                                Subject: %s
                                """.formatted(wordCount, userInput.getContent()),
                        Report.class);
    }

    record Report(String content) { }
}
