package com.sivalabs.demo.models;

import java.time.LocalDate;
import java.util.List;

public record Movie(String name, LocalDate releaseDate, String director, List<String> actors) {
    public Movie(MovieBasicInfo info, MovieDirector director, MovieActors actors) {
        this(info.name(), info.releaseDate(), director.name(), actors.actors());
    }
}