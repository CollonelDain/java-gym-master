package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

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

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> result = timetable.get(dayOfWeek);
        return (result != null) ? result : Collections.emptyMap();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return getTrainingSessionsForDay(dayOfWeek).getOrDefault(timeOfDay, Collections.emptyList());
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCounter = getCoachIntegerMap();

        List<CounterOfTrainings> topCoaches = new ArrayList<>();
        for (Map.Entry<Coach, Integer> coach : coachCounter.entrySet()) {
            topCoaches.add(new CounterOfTrainings(coach.getKey(), coach.getValue()));
        }
        topCoaches.sort(Comparator.comparingInt(CounterOfTrainings::getTrainings).reversed());

        return topCoaches;
    }

    private Map<Coach, Integer> getCoachIntegerMap() {
        Map<Coach, Integer> coachCounter = new HashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> day : timetable.entrySet()) {
            for (Map.Entry<TimeOfDay, List<TrainingSession>> timeOfDay : day.getValue().entrySet()) {
                for (TrainingSession workout : timeOfDay.getValue()) {
                    Coach coach = workout.getCoach();
                    coachCounter.put(coach, coachCounter.getOrDefault(coach, 0) + 1);
                }
            }
        }
        return coachCounter;
    }
}
