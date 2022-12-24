
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;
import util.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

import static task.Type.EPICTASK;

import static util.CreationOfTime.*;

public abstract class TaskManagerTest {
    protected TaskManager manager;
    protected EpicTask epicTask;
    protected EpicTask epicTask1;
    protected Task task;
    protected Task task1;
    protected SubTask subTask;
    protected SubTask subTask1;

    @BeforeEach
    public void createTaskAndManager() {
        manager = Managers.getDefault();
        Duration duration = Duration.ofHours(44);
        Duration duration1 = Duration.ofHours(34);
        Duration duration2 = Duration.ofHours(44);
        Duration duration3 = Duration.ofHours(34);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 22, 12, 34), zoneId);
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 10, 12, 10, 40), zoneId);
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(LocalDateTime.of(2022, 9, 10, 12, 34), zoneId);
        ZonedDateTime zonedDateTime3 = ZonedDateTime.of(LocalDateTime.of(2022, 8, 11, 10, 40), zoneId);
        task = new Task(Status.NEW, duration, zonedDateTime);
        task1 = new Task(Status.IN_PROGRESS, duration1, zonedDateTime1);
        epicTask = new EpicTask(-1, EPICTASK, "1Epic", Status.NEW, "okk", defaultStartTime, defaultDuration);
        epicTask1 = new EpicTask(Status.NEW, defaultDuration, defaultStartTime);
        subTask = new SubTask(Status.IN_PROGRESS, duration2, zonedDateTime2, 3);
        subTask1 = new SubTask(Status.NEW, duration3, zonedDateTime3, 3);
        manager.addTask(task);
        manager.addTask(task1);
        manager.addEpicTask(epicTask);
        manager.addEpicTask(epicTask1);
        manager.addSubTask(subTask);
        manager.addSubTask(subTask1);
    }

    @Test
    public void shouldDeleteTheTaskTrue() {
        manager.removeTask(task);
        Map<Integer, Task> tasks1 = manager.getTasks();

        Assertions.assertFalse(tasks1.containsKey(task.getIdTask()));
    }

    @Test
    public void shouldReturnMessageIfTheListTaskIsEmpty() {
        manager.clearTasks();
        String message = manager.removeTask(task);

        Assertions.assertEquals("Задачи для удаления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidTaskId() {
        Task task1 = new Task();
        task1.setIdTask(999);
        String message = manager.removeTask(task1);

        Assertions.assertEquals("Неверный идентификатор задачи", message);
    }

    @Test
    public void shouldDeleteTheEpicTrue() {
        manager.removeEpicTask(epicTask);
        Map<Integer, EpicTask> tasks1 = manager.getEpicTasks();

        Assertions.assertFalse(tasks1.containsKey(epicTask.getIdTask()));
    }

    @Test
    public void shouldReturnMessageIfTheListEpicIsEmpty() {
        manager.clearEpicTasks();
        String message = manager.removeEpicTask(epicTask);

        Assertions.assertEquals("Задачи для удаления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidEpicId() {
        EpicTask task1 = new EpicTask();
        task1.setIdTask(999);
        String message = manager.removeEpicTask(task1);

        Assertions.assertEquals("Неверный идентификатор задачи", message);
    }

    @Test
    public void shouldDeleteTheSubTaskTrue() {
        manager.removeSubTask(subTask);
        Map<Integer, SubTask> tasks1 = manager.getSubTasks();

        Assertions.assertFalse(tasks1.containsKey(subTask.getIdTask()));
    }

    @Test
    public void shouldReturnMessageIfTheListSubTaskIsEmpty() {
        manager.clearSubTasks();
        String message = manager.removeSubTask(subTask);

        Assertions.assertEquals("Задачи для удаления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidSubTaskId() {
        SubTask task1 = new SubTask();
        task1.setIdTask(999);
        String message = manager.removeSubTask(task1);

        Assertions.assertEquals("Неверный идентификатор задачи", message);
    }

    @Test
    public void shouldMakeTheListTasksEmpty() {
        manager.clearTasks();

        Assertions.assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    public void shouldMakeTheListEpicsEmpty() {
        manager.clearEpicTasks();

        Assertions.assertTrue(manager.getEpicTasks().isEmpty());
    }

    @Test
    public void shouldMakeTheListSubTasksEmpty() {
        manager.clearSubTasks();

        Assertions.assertTrue(manager.getSubTasks().isEmpty());
    }

    @Test
    public void shouldMakeEmptyTheListTasksEmpty() {
        manager.clearTasks();
        manager.clearTasks();

        Assertions.assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    public void shouldMakeEmptyTheListEpicsEmpty() {
        manager.clearEpicTasks();
        manager.clearEpicTasks();

        Assertions.assertTrue(manager.getEpicTasks().isEmpty());
    }

    @Test
    public void shouldMakeEmptyTheListSubTasksEmpty() {
        manager.clearSubTasks();
        manager.clearSubTasks();

        Assertions.assertTrue(manager.getSubTasks().isEmpty());
    }

    @Test
    public void shouldReturnATask() {
        int id = task.getIdTask();

        Assertions.assertEquals(task, manager.getTask(id));
    }

    @Test
    public void shouldReturnNullIfListTasksEmpty() {
        manager.clearTasks();
        int id = task.getIdTask();

        Assertions.assertNull(manager.getTask(id));
    }

    @Test
    public void shouldReturnNullIfIdTaskIncorrect() {
        int id = 999;

        Assertions.assertNull(manager.getTask(id));
    }

    @Test
    public void shouldReturnAEpicTask() {
        int id = epicTask.getIdTask();

        Assertions.assertEquals(epicTask, manager.getEpicTask(id));
    }

    @Test
    public void shouldReturnNullIfListEpicsEmpty() {
        manager.clearEpicTasks();
        int id = epicTask.getIdTask();

        Assertions.assertNull(manager.getEpicTask(id));
    }

    @Test
    public void shouldReturnNullIfIdEpicIncorrect() {
        int id = 999;

        Assertions.assertNull(manager.getEpicTask(id));
    }

    @Test
    public void shouldReturnASubTask() {
        int id = subTask.getIdTask();

        Assertions.assertEquals(subTask, manager.getSubTask(id));
    }

    @Test
    public void shouldReturnNullIfListSubTaskEmpty() {
        manager.clearSubTasks();
        int id = subTask.getIdTask();

        Assertions.assertNull(manager.getSubTask(id));
    }

    @Test
    public void shouldReturnNullIfIdSubTaskIncorrect() {
        int id = 999;

        Assertions.assertNull(manager.getSubTask(id));
    }

    @Test
    public void shouldReturnAListOfTasks() {
        Task[] listTask = {task, task1};

        Assertions.assertArrayEquals(listTask, manager.getListTasks().toArray());
    }

    @Test
    public void shouldReturnAListOfEpic() {
        EpicTask[] listTask = {epicTask, epicTask1};

        Assertions.assertArrayEquals(listTask, manager.getListEpicTasks().toArray());
    }

    @Test
    public void shouldReturnAListOfSubTask() {
        SubTask[] listTask = {subTask, subTask1};

        Assertions.assertArrayEquals(listTask, manager.getListSubTasks().toArray());
    }

    @Test
    public void shouldReturnEmptyAListOfTasks() {
        Task[] listTask = {};
        manager.clearTasks();
        Assertions.assertArrayEquals(listTask, manager.getListTasks().toArray());
    }

    @Test
    public void shouldReturnEmptyAListOfEpic() {
        EpicTask[] listTask = {};
        manager.clearEpicTasks();
        Assertions.assertArrayEquals(listTask, manager.getListEpicTasks().toArray());
    }

    @Test
    public void shouldReturnEmptyAListOfSubTask() {
        SubTask[] listTask = {};
        manager.clearSubTasks();
        Assertions.assertArrayEquals(listTask, manager.getListSubTasks().toArray());
    }

    @Test
    public void shouldReturnMessageIfTaskUpdate() {
        String message = manager.updateTasks(task);

        Assertions.assertEquals("Задача успешно обновлена!", message);
    }

    @Test
    public void shouldReturnMessageIfListTaskEmpty() {
        manager.clearTasks();
        String message = manager.updateTasks(task);

        Assertions.assertEquals("Задачи для обновления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidTaskIds() {
        Task task999 = new Task();
        task999.setIdTask(999);
        String message = manager.updateTasks(task999);

        Assertions.assertEquals("Неверный идентификатор задачи", message);
    }

    @Test
    public void shouldReturnMessageIfEpicUpdate() {
        String message = manager.updateEpicTasks(epicTask);

        Assertions.assertEquals("Задача успешно обновлена!", message);
        Assertions.assertEquals(epicTask, manager.getEpicTask(epicTask.getIdTask()));
    }

    @Test
    public void shouldReturnMessageIfListEpicEmpty() {
        manager.clearEpicTasks();
        String message = manager.updateEpicTasks(epicTask);

        Assertions.assertEquals("Задачи для обновления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidEpicIds() {
        EpicTask task999 = new EpicTask();
        task999.setIdTask(999);
        String message = manager.updateEpicTasks(task999);

        Assertions.assertEquals("Неверный идентификатор задачи", message);
    }

    @Test
    public void shouldReturnMessageIfSubTaskUpdate() {
        String message = manager.updateSubTasks(subTask);

        Assertions.assertEquals("Задача успешно обновлена!", message);
    }

    @Test
    public void shouldReturnMessageIfListSubtaskEmpty() {
        manager.clearSubTasks();
        String message = manager.updateSubTasks(subTask);

        Assertions.assertEquals("Задачи для обновления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidSubtaskIds() {
        SubTask task999 = new SubTask();
        task999.setIdTask(999);
        String message = manager.updateSubTasks(task999);

        Assertions.assertEquals("Неверный идентификатор задачи", message);
    }

    @Test
    public void shouldAddTaskToManager() {
        manager.clearTasks();
        manager.addTask(task);

        Assertions.assertEquals(task, manager.getTask(task.getIdTask()));
    }

    @Test
    public void shouldAddEpicToManager() {
        manager.clearEpicTasks();
        manager.addEpicTask(epicTask);

        Assertions.assertEquals(epicTask, manager.getEpicTask(epicTask.getIdTask()));
    }

    @Test
    public void shouldAddSubTaskToManager() {
        manager.clearSubTasks();
        manager.addSubTask(subTask);

        Assertions.assertEquals(subTask, manager.getSubTask(subTask.getIdTask()));
    }

    @Test
    public void shouldReturnMessageIfEpicAbsent() {
        manager.clearSubTasks();
        manager.clearEpicTasks();
        String message = manager.addSubTask(subTask);

        Assertions.assertEquals("Отсутствует эпик для этой подзадачи!", message);
    }
}
