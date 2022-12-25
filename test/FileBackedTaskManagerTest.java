import manager.FileBackedTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static manager.FileBackedTaskManager.loadFromFile;

import static task.Type.EPICTASK;
import static util.Managers.getDefault;
import static util.CreationOfTime.*;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    private File file = new File("taskManager.csv");

    @BeforeEach
    public void setUp() {
        manager = new FileBackedTaskManager(file);
        createTask();
    }

    @Test
    public void shouldRestoreManagerFromFile() {
        manager.getTask(task.getIdTask());
        manager.getEpicTask(epicTask.getIdTask());
        manager.getSubTask(subTask.getIdTask());
        TaskManager loadFromFile = loadFromFile(file);

        Assertions.assertEquals(2, loadFromFile.getTasks().size());
        Assertions.assertEquals(2, loadFromFile.getEpicTasks().size());
        Assertions.assertEquals(2, loadFromFile.getSubTasks().size());
        Assertions.assertEquals(3, loadFromFile.getHistory().size());
        Assertions.assertEquals(4, loadFromFile.getPrioritizedTasks().size());
        Assertions.assertEquals(manager.getId(), loadFromFile.getId());
        Assertions.assertEquals(manager.getTasks().size(), loadFromFile.getTasks().size());
        Assertions.assertEquals(manager.getEpicTasks().size(), loadFromFile.getEpicTasks().size());
        Assertions.assertEquals(manager.getSubTasks().size(), loadFromFile.getSubTasks().size());
        Assertions.assertEquals(manager.getHistory().size(), loadFromFile.getHistory().size());
        Assertions.assertEquals(manager.getPrioritizedTasks().size(), loadFromFile.getPrioritizedTasks().size());
    }

    @Test
    public void shouldSaveAndReturnEpicIfSubtaskEmpty() {
        File file1 = new File("taskManager1.csv");
        TaskManager manager1 = getDefault(file1);
        epicTask = new EpicTask(-1, EPICTASK, "1Epic", Status.NEW,
                "okk", defaultStartTime, defaultDuration);
        manager1.addEpicTask(epicTask);
        TaskManager manager2 = loadFromFile(file1);

        Assertions.assertTrue(manager2.getEpicTasks().containsKey(epicTask.getIdTask()));
    }

    @Test
    public void shouldReturnEmptyHistoryTaskIfWasEmpty() {
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

