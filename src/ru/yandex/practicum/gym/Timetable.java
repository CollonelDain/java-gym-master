package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, SortedMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private List<CounterOfTrainings> coaches = new ArrayList<>();
    private boolean isSortedCoaches = true;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        isSortedCoaches = false;
        boolean flag = false;
        for (CounterOfTrainings coach : coaches) {
            if (coach.getCoach().equals(trainingSession.getCoach())) {
                coach.incrementTrainings();
                flag = true;
            }
        }
        if (!flag) {
            coaches.add(new CounterOfTrainings(trainingSession.getCoach()));
        }

        SortedMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.computeIfAbsent(
                trainingSession.getDayOfWeek(),
                k -> new TreeMap<>()
        );

        dayMap.computeIfAbsent(
                trainingSession.getTimeOfDay(),
                k -> new ArrayList<>()
        ).add(trainingSession);
    }


    //TODO: возможно, не было смысла возвращать emptyCollection и стоит вернуться к returnForDefault new Collection<>()
    public SortedMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, Collections.emptySortedMap());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return getTrainingSessionsForDay(dayOfWeek).getOrDefault(timeOfDay, Collections.emptyList());
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        if (!isSortedCoaches) {
            coaches.sort(Comparator.comparingInt(CounterOfTrainings::getTrainings).reversed());
            isSortedCoaches = true;
        }

        return coaches;
    }
}
