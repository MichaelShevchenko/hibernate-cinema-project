package com.dev.cinema.dto;

import javax.validation.constraints.NotNull;

public class MovieRequestDto {
    @NotNull
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Movie{" + "title='" + title + '\''
                + ", description='" + description + '\'' + '}';
    }
}
