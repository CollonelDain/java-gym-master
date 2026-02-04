package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что в понедельник вернулось именно то занятие
        Assertions.assertEquals(
                singleTrainingSession,
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).get(new TimeOfDay(13, 0)).getFirst()
        );
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(Collections.emptySortedMap(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession2 = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(18, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession2);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        var trainingSessionForThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(
                3,
                trainingSessionForThursday.size(),
                "Неверное количество занятий в четверг"
        );
        Assertions.assertEquals(
                "[13:00, 18:00, 20:00]",
                trainingSessionForThursday.keySet().toString(),
                "Неверный порядок следования элементов"
        );
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(Collections.emptySortedMap(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(
                1,
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13,0)).size()
        );
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(
                Collections.emptyList(),
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14,0))
        );
    }

    @Test
    void testGetCountByCoachesForEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> coaches = timetable.getCountByCoaches();

        //Проверить, что список тренеров пуст
        Assertions.assertTrue(coaches.isEmpty());
    }

    @Test
    void testGetCountByCoachesForOneCoach() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Василий", "Васильевич");
        Group group1 = new Group("Акробатика для взрослых", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.SUNDAY, new TimeOfDay(10, 0))
        );
        List<CounterOfTrainings> coaches = timetable.getCountByCoaches();

        //Проверить, что в списке только один тренер
        Assertions.assertEquals(1, coaches.size());
        //Проверить, что у тренера две тренировки
        Assertions.assertEquals(2, coaches.getFirst().getTrainings());
    }

    @Test
    void testGetCountByCoachesForTreeCoachesOnSort() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Василий", "Васильевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach3 = new Coach("Никитин", "Никита", "Никитич");

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.FRIDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.FRIDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.FRIDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group2, coach3, DayOfWeek.MONDAY, new TimeOfDay(12, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group2, coach3, DayOfWeek.THURSDAY, new TimeOfDay(13, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group2, coach3, DayOfWeek.SATURDAY, new TimeOfDay(12, 0))
        );

        List<CounterOfTrainings> coaches = timetable.getCountByCoaches();

        //Проверить, что список тренеров отсортирован по убыванию
        for (int i=1; i<coaches.size(); i++) {
            Assertions.assertTrue(coaches.get(i-1).getTrainings() >= coaches.get(i).getTrainings());
        }
    }

    @Test
    void testTwoEqualsCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Василий", "Васильевич");
        Coach coach2 = new Coach("Васильев", "Василий", "Васильевич");
        Group group1 = new Group("Акробатика для взрослых", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach2, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(new TrainingSession(
                group1, coach2, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );

        List<CounterOfTrainings> coaches = timetable.getCountByCoaches();

        //Проверить, что в списке только один тренер
        Assertions.assertEquals(1, coaches.size());
        //Проверить, что у учителя четыре тренировки
        Assertions.assertEquals(4, coaches.getFirst().getTrainings());
    }
}
