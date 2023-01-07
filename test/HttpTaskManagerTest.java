import http.KVServer;
import manager.HttpTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.EpicTask;
import task.Status;

import java.io.IOException;

import static task.Type.EPICTASK;
import static util.CreationOfTime.defaultDuration;
import static util.CreationOfTime.defaultStartTime;
import static util.Managers.getDefault;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    private KVServer server;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        server = new KVServer();
        server.start();
        manager = new HttpTaskManager("http://localhost:8078");
        createTask();
        manager.addTask(task);
        manager.addTask(task1);
        manager.addEpicTask(epicTask);
        manager.addEpicTask(epicTask1);
        manager.addSubTask(subTask);
        manager.addSubTask(subTask1);
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

    @Test
    public void shouldRestoreManagerFromServer() throws IOException, InterruptedException {
        manager.getTask(task.getIdTask());
        manager.getEpicTask(epicTask.getIdTask());
        manager.getSubTask(subTask.getIdTask());

        HttpTaskManager managerLoad = new HttpTaskManager("http://localhost:8078");
        managerLoad.load();

        Assertions.assertEquals(2, managerLoad.getTasks().size());
        Assertions.assertEquals(2, managerLoad.getEpicTasks().size());
        Assertions.assertEquals(2, managerLoad.getSubTasks().size());
        Assertions.assertEquals(3, managerLoad.getHistory().size());
        Assertions.assertEquals(4, managerLoad.getPrioritizedTasks().size());
        Assertions.assertEquals(manager.getId(), managerLoad.getId());
        Assertions.assertEquals(manager.getTasks().size(), managerLoad.getTasks().size());
        Assertions.assertEquals(manager.getEpicTasks().size(), managerLoad.getEpicTasks().size());
        Assertions.assertEquals(manager.getSubTasks().size(), managerLoad.getSubTasks().size());
        Assertions.assertEquals(manager.getHistory().size(), managerLoad.getHistory().size());
        Assertions.assertEquals(manager.getPrioritizedTasks().size(), managerLoad.getPrioritizedTasks().size());
    }

    @Test
    public void shouldSaveAndReturnEpicIfSubtaskEmpty() throws IOException, InterruptedException {
        TaskManager manager1 = getDefault("http://localhost:8078");
        epicTask = new EpicTask(-1, EPICTASK, "1Epic", Status.NEW,
                "okk", defaultStartTime, defaultDuration);
        manager1.addEpicTask(epicTask);
        HttpTaskManager manager2 = new HttpTaskManager("http://localhost:8078");
        manager2.load();

        Assertions.assertTrue(manager2.getEpicTasks().containsKey(epicTask.getIdTask()));
    }

    @Test
    public void shouldReturnEmptyHistoryTaskIfWasEmpty() throws IOException, InterruptedException {
        boolean x = manager.getHistory().isEmpty();

        Assertions.assertTrue(x);

        HttpTaskManager manager1 = new HttpTaskManager("http://localhost:8078");
        boolean y = manager1.getHistory().isEmpty();

        Assertions.assertTrue(y);
    }

    @Test
    public void shouldReturnEmptyIfManagerEmpty() throws IOException, InterruptedException {
        server.stop();
        server = new KVServer();
        server.start();
        HttpTaskManager manager1 = new HttpTaskManager("http://localhost:8078");
        manager1.load();

        Assertions.assertTrue(manager1.getTasks().values().isEmpty());
        server.stop();
    }
}
