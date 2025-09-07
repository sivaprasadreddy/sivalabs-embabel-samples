package com.sivalabs.demo;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Agent(name = "movie-info-provider",
        description = "Provides movie information",
        version = "1.0.0",
        beanName = "movieInfoProviderAgent")
public class MovieInfoProviderAgent {
    private static final Logger log = LoggerFactory.getLogger(MovieInfoProviderAgent.class);

    @Action
    public MovieBasicInfo getMovieBasicInfo(UserInput userInput, OperationContext context) {
        return context.ai()
                .withDefaultLlm()
                //.withLlm(OpenAiModels.GPT_41)
                //.withLlm("qwen3:8b")
                //.withFirstAvailableLlmOf("qwen3:8b", OpenAiModels.GPT_41)
                //.withLlmByRole("faster")
                .createObjectIfPossible(
                        """
                        Create a MovieBasicInfo from this user input, extracting their details:
                        %s""".formatted(userInput.getContent()),
                        MovieBasicInfo.class
                );
    }

    @Action
    public MovieActors getMovieActors(UserInput userInput, OperationContext context) {
        return context.ai()
                .withDefaultLlm()
                .createObjectIfPossible(
                        """
                                Extract the movie name from this user input: %s
                                Then get the actors for that movie.
                                Create a MovieActors from the actors info.
                                """.formatted(userInput.getContent()),
                        MovieActors.class
                );
    }

    @AchievesGoal(description = "Provides movie information for the given movie name")
    @Action
    public Movie getMovieInfo(MovieBasicInfo basicInfo, MovieActors actors, OperationContext context) {
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
        return new Movie(basicInfo, director, actors);
    }

    /*
    @AchievesGoal(description = "Provides movie information for the given movie name")
    @Action
    public Movie getMovie(UserInput userInput,  OperationContext context) {
        Movie movie = context.ai()
                .withDefaultLlm()
                .createObjectIfPossible(
                        """
                        Create a Movie from this user input, extracting their details: %s
                        """.formatted(userInput.getContent()),
                        Movie.class
                );
        log.info("Movie info: {}", movie);
        return movie;
    }*/

    public record MovieBasicInfo(String name, LocalDate releaseDate) {
    }

    public record MovieActors(List<String> actors) {
    }

    public record MovieDirector(String name) {
    }

    public record Movie(String name, LocalDate releaseDate, String director, List<String> actors) {
        Movie(MovieBasicInfo info, MovieDirector director, MovieActors actors) {
            this(info.name(), info.releaseDate(), director.name(), actors.actors());
        }
    }
}
