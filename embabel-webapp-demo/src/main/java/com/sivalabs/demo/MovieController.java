package com.sivalabs.demo;

import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.*;
import com.sivalabs.demo.models.Movie;
import com.sivalabs.demo.models.MovieEnquiryRequest;
import com.sivalabs.demo.models.MovieName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    private static final Logger log = LoggerFactory.getLogger(MovieController.class);
    private final AgentPlatform agentPlatform;
    private final Ai ai;

    public MovieController(AgentPlatform agentPlatform, Ai ai) {
        this.agentPlatform = agentPlatform;
        this.ai = ai;
    }

    @PostMapping("/api/chat-1")
    Movie getMovieInfo1(@RequestBody MovieEnquiryRequest enquiryRequest) {
        var movieAgentInvocation = AgentInvocation.create(agentPlatform, Movie.class);
        Movie movie = movieAgentInvocation.invoke(enquiryRequest);
        log.info("chat-1:: Result movie: {}", movie);
        return movie;
    }

    @PostMapping("/api/chat-2")
    Movie getMovieInfo2(@RequestBody MovieEnquiryRequest enquiryRequest) {
        MovieName movieName = ai.withDefaultLlm()
                .createObjectIfPossible(
                        """
                                Create a MovieName from this user input: %s"""
                                .formatted(enquiryRequest.request()), MovieName.class);
        Assert.notNull(movieName, "Movie name cannot be null");
        var movieAgentInvocation = AgentInvocation.create(agentPlatform, Movie.class);
        Movie movie = movieAgentInvocation.invoke(movieName);
        log.info("chat-2:: Result movie: {}", movie);
        return movie;
    }

    @PostMapping("/api/chat-3")
    Movie getMovieInfo3(@RequestBody MovieEnquiryRequest enquiryRequest) {
        var invocation = AgentInvocation
                .builder(agentPlatform)
                .options(ProcessOptions.builder().verbosity(v -> v.showPrompts(true)).build())
                .build(Movie.class);
        Movie movie = invocation.invoke(enquiryRequest);
        log.info("chat-3:: Result movie: {}", movie);
        return movie;
    }

    @PostMapping("/api/chat-4")
    Movie getMovieInfo4(@RequestBody MovieEnquiryRequest enquiryRequest) {
        Agent movieAgent = agentPlatform.agents().stream()
                .filter(agent -> agent.getName().equalsIgnoreCase("movie-info-provider"))
                .findFirst().orElseThrow();
        ProcessOptions processOptions =
                ProcessOptions.builder()
                        .verbosity(v -> v.showPrompts(true))
                        .build();

        AgentProcess agentProcess = agentPlatform.createAgentProcessFrom(movieAgent, processOptions, enquiryRequest);

        try {
            AgentProcess result = agentPlatform.start(agentProcess).get();
            AgentProcessStatusCode status = result.getStatus();
            if(status == AgentProcessStatusCode.COMPLETED) {
                Movie movie = (Movie) result.lastResult();
                log.info("chat-4:: Result movie: {}", movie);
                return movie;
            }
        } catch (Exception e) {
            log.error("Error while processing chat-4", e);
            return new Movie("", null, "", List.of());
        }
        log.error("Failure while processing chat-4");
        return new Movie("", null, "", List.of());
    }
}
