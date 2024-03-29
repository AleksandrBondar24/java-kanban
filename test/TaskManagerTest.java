
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

import static task.Type.*;
import static util.CreationOfTime.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected TaskManager manager;
    protected EpicTask epicTask;
    protected EpicTask epicTask1;
    protected Task task;
    protected Task task1;
    protected SubTask subTask;
    protected SubTask subTask1;

    @BeforeEach
    public void createManager() {
        manager = Managers.getDefault();
    }

    @BeforeEach
    public void createTask() {
        Duration duration = Duration.ofHours(44);
        Duration duration1 = Duration.ofHours(34);
        Duration duration2 = Duration.ofHours(44);
        Duration duration3 = Duration.ofHours(34);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 22, 12, 34), zoneId);
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 10, 12, 10, 40), zoneId);
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(LocalDateTime.of(2022, 9, 10, 12, 34), zoneId);
        ZonedDateTime zonedDateTime3 = ZonedDateTime.of(LocalDateTime.of(2022, 8, 11, 10, 40), zoneId);
        task = new Task(-1, TASK, "Tz1", Status.NEW, "okk", zonedDateTime, duration);
        task1 = new Task(-1, TASK, "Tz2", Status.IN_PROGRESS, "okk", zonedDateTime1, duration1);
        epicTask = new EpicTask(-1, EPICTASK, "1Epic", Status.NEW, "okk", defaultStartTime, defaultDuration);
        epicTask1 = new EpicTask(-1, EPICTASK, "2Epic", Status.NEW, "okk", defaultStartTime, defaultDuration);
        subTask = new SubTask(-1, SUBTASK, "ep", Status.IN_PROGRESS, "okk", zonedDateTime2, duration2, 3);
        subTask1 = new SubTask(-1, SUBTASK, "ep", Status.NEW, "okk", zonedDateTime3, duration3, 3);
    }

    @Test
    public void shouldDeleteTheTaskTrue() {
        manager.removeTask(1);
        Map<Integer, Task> tasks1 = manager.getTasks();

        Assertions.assertFalse(tasks1.containsKey(task.getIdTask()));
    }

    @Test
    public void shouldReturnMessageIfTheListTaskIsEmpty() {
        manager.clearTasks();
        String message = manager.removeTask(1);

        Assertions.assertEquals("Задачи для удаления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidTaskId() {
        Task task1 = new Task();
        task1.setIdTask(999);
        String message = manager.removeTask(task1.getIdTask());

        Assertions.assertEquals("Неверный идентификатор задачи", message);
    }

    @Test
    public void shouldDeleteTheEpicTrue() {
        manager.removeEpicTask(3);
        Map<Integer, EpicTask> tasks1 = manager.getEpicTasks();

        Assertions.assertFalse(tasks1.containsKey(epicTask.getIdTask()));
    }

    @Test
    public void shouldReturnMessageIfTheListEpicIsEmpty() {
        manager.clearEpicTasks();
        String message = manager.removeEpicTask(3);

        Assertions.assertEquals("Задачи для удаления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidEpicId() {
        EpicTask task1 = new EpicTask();
        task1.setIdTask(999);
        String message = manager.removeEpicTask(task1.getIdTask());

        Assertions.assertEquals("Неверный идентификатор задачи", message);
    }

    @Test
    public void shouldDeleteTheSubTaskTrue() {
        manager.removeSubTask(subTask.getIdTask());
        Map<Integer, SubTask> tasks1 = manager.getSubTasks();

        Assertions.assertFalse(tasks1.containsKey(subTask.getIdTask()));
    }

    @Test
    public void shouldReturnMessageIfTheListSubTaskIsEmpty() {
        manager.clearSubTasks();
        String message = manager.removeSubTask(5);

        Assertions.assertEquals("Задачи для удаления не существует!", message);
    }

    @Test
    public void shouldReturnMessageIfInvalidSubTaskId() {
        SubTask task1 = new SubTask();
        task1.setIdTask(999);
        String message = manager.removeSubTask(task1.getIdTask());

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

        Assertions.assertEquals(2, manager.getListTasks().size());
    }

    @Test
    public void shouldReturnAListOfEpic() {

        Assertions.assertEquals(2, manager.getListEpicTasks().size());
    }

    @Test
    public void shouldReturnAListOfSubTask() {

        Assertions.assertEquals(2, manager.getListSubTasks().size());
    }

    @Test
    public void shouldReturnEmptyAListOfTasks() {
        manager.clearTasks();

        Assertions.assertEquals(0, manager.getListTasks().size());
    }

    @Test
    public void shouldReturnEmptyAListOfEpic() {
        manager.clearEpicTasks();

        Assertions.assertEquals(0, manager.getListEpicTasks().size());
    }

    @Test
    public void shouldReturnEmptyAListOfSubTask() {
        manager.clearSubTasks();

        Assertions.assertEquals(0, manager.getListSubTasks().size());
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
