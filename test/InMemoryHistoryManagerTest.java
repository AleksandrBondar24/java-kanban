import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.EpicTask;
import task.Status;
import task.SubTask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static util.Managers.getDefault;
import static util.CreationOfTime.zoneId;

public class InMemoryHistoryManagerTest {
    private Task task;
    private EpicTask epicTask;
    private SubTask subTask;
    private TaskManager manager1;

    @BeforeEach
    public void createTaskAndHistoryManager() {
        manager1 = getDefault();
        Duration duration = Duration.ofHours(44);
        Duration duration1 = Duration.ofHours(34);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 02, 12, 34), zoneId);
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 11, 12, 10, 40), zoneId);
        task = new Task(Status.NEW, duration, zonedDateTime);
        epicTask = new EpicTask(Status.NEW, duration, zonedDateTime);
        subTask = new SubTask(Status.IN_PROGRESS, duration1, zonedDateTime1, 2);
        manager1.addTask(task);
        manager1.addEpicTask(epicTask);
        manager1.addSubTask(subTask);
        manager1.getTask(task.getIdTask());
        manager1.getEpicTask(epicTask.getIdTask());
        manager1.getSubTask(subTask.getIdTask());

    }

    @Test
    public void shouldReturnEmptyHistoryListIfHistoryEmpty() {
        Task[] listTask = {};
        manager1.removeTask(task);
        manager1.removeEpicTask(epicTask);
        manager1.removeSubTask(subTask);

        Assertions.assertArrayEquals(listTask, manager1.getHistory().toArray());
    }

    @Test
    public void shouldReturnHistoryListWithoutDouble() {
        manager1.getTask(task.getIdTask());
        Task[] listTask = {epicTask, subTask, task};

        Assertions.assertArrayEquals(listTask, manager1.getHistory().toArray());
    }

    @Test
    public void shouldReturnHistoryListWithoutFirstTask() {
        Task[] listTask = {epicTask, subTask};
        manager1.removeTask(task);

        Assertions.assertArrayEquals(listTask, manager1.getHistory().toArray());
    }

    @Test
    public void shouldReturnHistoryListWithoutMiddleTask() {
        Task[] listTask = {task};
        manager1.removeEpicTask(epicTask);

        Assertions.assertArrayEquals(listTask, manager1.getHistory().toArray());
    }

    @Test
    public void shouldReturnHistoryListWithoutLastTask() {
        Task[] listTask = {epicTask, subTask};
        manager1.removeTask(task);

        Assertions.assertArrayEquals(listTask, manager1.getHistory().toArray());
    }
}
