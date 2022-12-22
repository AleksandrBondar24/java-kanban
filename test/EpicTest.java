
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.EpicTask;
import task.SubTask;
import util.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static task.Status.*;
import static util.СreationOfTime.zoneId;


class EpicTest {
    private TaskManager manager;
    private EpicTask task;
    Duration duration;
    Duration duration1;
    Duration duration2;
    ZonedDateTime zonedDateTime;
    ZonedDateTime zonedDateTime1;
    ZonedDateTime zonedDateTime2;

    @BeforeEach
    public void createEpicAndManager() {
        duration = Duration.ofHours(44);
        duration1 = Duration.ofHours(34);
        duration2 = Duration.ofHours(20);
         zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 02, 12, 34), zoneId);
         zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 11, 12, 10, 40), zoneId);
         zonedDateTime2 = ZonedDateTime.of(LocalDateTime.of(2022, 10, 04, 7, 34), zoneId);
        manager = Managers.getDefault();
        task = new EpicTask();
        manager.addEpicTask(task);
    }

    @Test
    public void epicStatusShouldBeNewWithAnEmptyListOfSubtasks() {
        List<Integer> subTaskIds = task.getListSubTaskIds();
        subTaskIds.clear();
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(NEW, task.getStatus());
    }

    @Test
    public void epicStatusShouldBeNewIfAllSubtasksAreNew() {
        List<SubTask> list = List.of(new SubTask(NEW, duration, zonedDateTime, task.getIdTask()),
                new SubTask(NEW, duration1, zonedDateTime1, task.getIdTask()),
                new SubTask(NEW, duration2, zonedDateTime2, task.getIdTask()));
        list.forEach(manager::addSubTask);
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(NEW, task.getStatus());

    }

    @Test
    public void epicStatusShouldBeDoneIfAllSubtasksAreDone() {
        List<SubTask> list = List.of(new SubTask(DONE,duration,zonedDateTime, task.getIdTask()),
                new SubTask(DONE,duration1,zonedDateTime1, task.getIdTask()),
                new SubTask(DONE,duration2,zonedDateTime2, task.getIdTask()));
        list.forEach(manager::addSubTask);
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(DONE, task.getStatus());
    }

    @Test
    public void epicStatusShouldBeInProgressIfAllSubtasksDoneNew() {
        List<SubTask> list = List.of(new SubTask(DONE,duration,zonedDateTime, task.getIdTask()),
                new SubTask(NEW,duration1,zonedDateTime1, task.getIdTask()),
                new SubTask(DONE,duration2,zonedDateTime2, task.getIdTask()));
        list.forEach(manager::addSubTask);
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(IN_PROGRESS, task.getStatus());
    }

    @Test
    public void epicStatusShouldBeInProgressIfAllSubtasksAreInProgress() {
        List<SubTask> list = List.of(new SubTask(IN_PROGRESS,duration,zonedDateTime, task.getIdTask()),
                new SubTask(IN_PROGRESS,duration1,zonedDateTime1, task.getIdTask()),
                new SubTask(IN_PROGRESS,duration2,zonedDateTime2, task.getIdTask()));
        list.forEach(manager::addSubTask);
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(IN_PROGRESS, task.getStatus());
    }
}