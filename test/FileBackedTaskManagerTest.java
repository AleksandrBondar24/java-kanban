import manager.FileBackedTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
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
    private File file = new File("taskManagerTest1.csv");

    @Test
    public void shouldSaveAndReturnEpicIfSubtaskEmpty() {
        TaskManager manager1 = getDefault(file);
        epicTask = new EpicTask(-1, EPICTASK, "1Epic", Status.NEW,
                "okk", defaultStartTime, defaultDuration);
        manager1.addEpicTask(epicTask);
        TaskManager manager2 = loadFromFile(file);

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

