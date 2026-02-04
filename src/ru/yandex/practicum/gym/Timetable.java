package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, SortedMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    //TODO: вернуться к реализации из коммита 078b511, идея с флагами прикольная
    private Map<Coach, Integer> coaches = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        coaches.put(trainingSession.getCoach(), coaches.getOrDefault(trainingSession.getCoach(), 0) + 1);

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
        List<CounterOfTrainings> topCoaches = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coaches.entrySet()) {
            topCoaches.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        topCoaches.sort(new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings o1, CounterOfTrainings o2) {
                return o1.getTrainings() - o2.getTrainings();
            }
        }.reversed());

        return topCoaches;
    }
}
