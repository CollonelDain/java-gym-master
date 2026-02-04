package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings {
    private Coach coach;
    private int trainings;

    public CounterOfTrainings(Coach coach) {
        this.coach = coach;
        this.trainings = 1;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainings() {
        return trainings;
    }

    public void incrementTrainings() {
        this.trainings++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach);
    }
}
