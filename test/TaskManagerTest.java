
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.EpicTask;
import task.Status;
import task.SubTask;
import task.Task;
import util.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

import static util.СreationOfTime.zoneId;

public abstract class TaskManagerTest<T extends TaskManager> {

    private TaskManager manager;
    private EpicTask epicTask;
    private Task task;
    private SubTask subTask;
    private Duration duration;
    private Duration duration1;
    private ZonedDateTime zonedDateTime;
    private ZonedDateTime zonedDateTime1;

    @BeforeEach
    public void createTaskAndManager() {
        manager = Managers.getDefault();
        epicTask = new EpicTask();
        duration = Duration.ofHours(44);
        zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 02, 12, 34), zoneId);
        task = new Task(Status.NEW, duration, zonedDateTime);
        duration1 = Duration.ofHours(40);
        zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 11, 01, 11, 37), zoneId);
        subTask = new SubTask(Status.IN_PROGRESS, duration1, zonedDateTime1, epicTask.getIdTask());
        //manager.addTask(task);
        //manager.addEpicTask(epicTask);
        //manager.addSubTask(subTask);
    }

    @Test
    public void shouldDeleteTheTaskTrue() {
        manager.addTask(task);
        manager.removeTask(task);
        Map<Integer, Task> tasks1 = manager.getTasks();

        Assertions.assertFalse(tasks1.containsKey(task.getIdTask()));

    }

    @Test
    public void shouldDeleteTheTaskTr() {
        String message = manager.removeTask(task);
        Map<Integer, Task> tasks1 = manager.getTasks();

        Assertions.assertFalse(tasks1.containsKey(task.getIdTask()));
    }
}
