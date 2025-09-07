package com.sivalabs.demo;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.sivalabs.demo.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

@Agent(name = "movie-info-provider",
        description = "Provides movie information",
        version = "1.0.0",
        beanName = "movieInfoProviderAgent")
public class MovieInfoProviderAgent {
    private static final Logger log = LoggerFactory.getLogger(MovieInfoProviderAgent.class);

    @Action
    public MovieName getMovieName(MovieEnquiryRequest enquiryRequest, OperationContext context) {
        log.info("-------------getMovieName()----------------");
        return context.ai()
                .withDefaultLlm()
                .createObjectIfPossible(
                        """
                        Create a MovieName from the movie enquiry request by extracting their details: %s.
                        """.formatted(enquiryRequest.request()),
                        MovieName.class
                );
    }

    @Action
    public MovieBasicInfo getMovieBasicInfo(MovieName movieName, OperationContext context) {
        log.info("-------------getMovieBasicInfo()----------------");
        return context.ai()
                .withDefaultLlm()
                .createObjectIfPossible(
                        """
                        Get the movie details for the movie %s.
                        Create a MovieBasicInfo from the movie details by extracting their details.
                        """.formatted(movieName.name()),
                        MovieBasicInfo.class
                );
    }

    @Action
    public MovieActors getMovieActors(MovieName movieName, OperationContext context) {
        log.info("-------------getMovieActors()----------------");
        return context.ai()
                .withDefaultLlm()
                .createObjectIfPossible(
                        """
                        Get the actors for the movie %s.
                        Create a MovieActors from the actors info.
                        """.formatted(movieName.name()),
                        MovieActors.class
                );
    }

    @AchievesGoal(description = "Provides movie information for the given movie name")
    @Action
    public Movie getMovieInfo(MovieBasicInfo basicInfo, MovieActors actors, OperationContext context) {
        log.info("-------------getMovieInfo()----------------");
        MovieDirector director = context.ai()
                .withDefaultLlm()
                .createObjectIfPossible(
                        """
                                Get the director of the movie: %s.
                                Create a MovieDirector from that info.
                                """.formatted(basicInfo.name()),
                        MovieDirector.class
                );
        Assert.notNull(director, "Director cannot be null");
        Movie movie = new Movie(basicInfo, director, actors);
        log.info("Final movie result: {}", movie);
        return movie;
    }
}








