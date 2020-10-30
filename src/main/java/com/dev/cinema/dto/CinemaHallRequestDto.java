package com.dev.cinema.dto;

import javax.validation.constraints.NotNull;

public class CinemaHallRequestDto {
    @NotNull(message = "Capacity should not be null")
    private int capacity;
    private String description;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CinemaHall{" + "capacity=" + capacity
                + ", description='" + description + '\'' + '}';
    }
}
