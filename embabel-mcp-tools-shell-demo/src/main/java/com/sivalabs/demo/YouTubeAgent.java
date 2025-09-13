package com.sivalabs.demo;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;

import java.util.List;

import static com.sivalabs.demo.Constants.TOOLS_GROUP_YOUTUBE;

@Agent(name = "YouTubeAgent",
        description = "Find information from YouTube",
        version = "1.0.0")
public class YouTubeAgent {

    @AchievesGoal(description = "Find information from YouTube according to the user's request.")
    @Action
    public Videos searchVideos(UserInput userInput, OperationContext context) {
        return context.ai()
                .withDefaultLlm()
                .withToolGroup(TOOLS_GROUP_YOUTUBE)
                .createObjectIfPossible("""
                        Search YouTube for videos related to the following user input:
                        
                        %s
                        
                        Return Videos by extracting their details.
                        """.formatted(userInput.getContent()), Videos.class);
    }

    public record Videos(List<Video> videos){}
    public record Video(String title, String url) {
    }
}
