
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.EpicTask;
import task.SubTask;
import util.Managers;

import java.util.List;

import static task.Status.*;


class EpicTest {
    private TaskManager manager;
    private EpicTask task;

    @BeforeEach
    public void createEpicAndManager() {
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
        List<SubTask> list = List.of(new SubTask(NEW, task.getIdTask()), new SubTask(NEW, task.getIdTask()),
                new SubTask(NEW, task.getIdTask()));
        list.forEach(manager::addSubTask);
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(NEW, task.getStatus());

    }

    @Test
    public void epicStatusShouldBeDoneIfAllSubtasksAreDone() {
        List<SubTask> list = List.of(new SubTask(DONE, task.getIdTask()), new SubTask(DONE, task.getIdTask()),
                new SubTask(DONE, task.getIdTask()));
        list.forEach(manager::addSubTask);
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(DONE, task.getStatus());
    }

    @Test
    public void epicStatusShouldBeInProgressIfAllSubtasksDoneNew() {
        List<SubTask> list = List.of(new SubTask(DONE, task.getIdTask()), new SubTask(NEW, task.getIdTask()),
                new SubTask(DONE, task.getIdTask()));
        list.forEach(manager::addSubTask);
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(IN_PROGRESS, task.getStatus());
    }

    @Test
    public void epicStatusShouldBeInProgressIfAllSubtasksAreInProgress() {
        List<SubTask> list = List.of(new SubTask(IN_PROGRESS, task.getIdTask()),
                new SubTask(IN_PROGRESS, task.getIdTask()), new SubTask(IN_PROGRESS, task.getIdTask()));
        list.forEach(manager::addSubTask);
        manager.changeStatusEpicTask(task);

        Assertions.assertEquals(IN_PROGRESS, task.getStatus());
    }
}