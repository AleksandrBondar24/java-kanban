import manager.HistoryManager;
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

import static util.CreationOfTime.zoneId;
import static util.Managers.getDefaultHistory;

public class InMemoryHistoryManagerTest {
    private Task task;
    private EpicTask epicTask;
    private SubTask subTask;
    private HistoryManager manager;

    @BeforeEach
    public void createTaskAndHistoryManager() {
        manager = getDefaultHistory();
        Duration duration = Duration.ofHours(44);
        Duration duration1 = Duration.ofHours(34);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 2, 12, 34), zoneId);
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 11, 12, 10, 40), zoneId);
        task = new Task(1, Status.NEW, duration, zonedDateTime);
        epicTask = new EpicTask(2, Status.NEW, duration, zonedDateTime);
        subTask = new SubTask(3, Status.IN_PROGRESS, duration1, zonedDateTime1, 2);
        manager.add(task);
        manager.add(epicTask);
        manager.add(subTask);
    }

    @Test
    public void shouldReturnEmptyHistoryListIfHistoryEmpty() {
        manager.remove(task.getIdTask());
        manager.remove(epicTask.getIdTask());
        manager.remove(subTask.getIdTask());

        Assertions.assertEquals(0, manager.getHistory().size());
    }

    @Test
    public void shouldReturnHistoryListWithoutDuplicate() {
        manager.add(task);

        Assertions.assertEquals(3, manager.getHistory().size());
    }

    @Test
    public void shouldReturnHistoryListWithoutFirstTask() {
        manager.remove(task.getIdTask());

        Assertions.assertEquals(2, manager.getHistory().size());
    }

    @Test
    public void shouldReturnHistoryListWithoutMiddleTask() {
        manager.remove(epicTask.getIdTask());

        Assertions.assertEquals(2, manager.getHistory().size());
    }

    @Test
    public void shouldReturnHistoryListWithoutLastTask() {
        manager.remove(task.getIdTask());

        Assertions.assertEquals(2, manager.getHistory().size());
    }
}
