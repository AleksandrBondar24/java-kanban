import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static manager.FileBackedTaskManager.loadFromFile;
import static util.Managers.getDefault;
import static util.СreationOfTime.*;

public class FileBackedTaskManagerTest {
    File file = new File("taskManagerTest1.csv");
    private TaskManager manager;
    private Task task;
    private Task task1;
    private EpicTask epicTask;

    @BeforeEach
    public void createTasksManager() {

        manager = getDefault(file);
        Duration duration = Duration.ofHours(44);
        Duration duration1 = Duration.ofHours(34);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 02, 12, 34), zoneId);
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 11, 12, 10, 40), zoneId);
        task = new Task(-1, Type.TASK, "1task", Status.NEW, "ok", zonedDateTime, duration);
        task1 = new Task(-1, Type.TASK, "2task", Status.NEW, "okk", zonedDateTime1, duration1);
        epicTask = new EpicTask(3, Type.EPICTASK, "1Epic", Status.NEW, "okk", defaultStartTime, defaultDuration);
    }

    @Test
    public void shouldSaveAndReturnEpicIfSubtaskEmpty() {
        manager.addTask(task);
        manager.addTask(task1);
        manager.addEpicTask(epicTask);
        TaskManager manager1 = loadFromFile(file);

        Assertions.assertTrue(manager1.getEpicTasks().containsKey(epicTask.getIdTask()));
    }

    @Test
    public void shouldReturnEmptyHistoryTaskIfWasEmpty() {
        manager.addTask(task);
        manager.addTask(task1);
        manager.addEpicTask(epicTask);
        boolean x = manager.getHistory().isEmpty();

        Assertions.assertTrue(x);

        TaskManager manager1 = loadFromFile(file);
        boolean y = manager1.getHistory().isEmpty();

        Assertions.assertTrue(y);
    }

    @Test
    public void shouldReturnEmptyIfManagerEmpty() throws IOException {
        Files.createFile(Path.of("taskManagerTest.csv"));
        TaskManager manager1 = loadFromFile(new File("taskManagerTest.csv"));

        Assertions.assertTrue(manager1.getTasks().values().isEmpty());
        Files.delete(Path.of("taskManagerTest.csv"));
    }
}

