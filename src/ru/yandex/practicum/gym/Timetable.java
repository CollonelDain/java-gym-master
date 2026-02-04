package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, SortedMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
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
}
