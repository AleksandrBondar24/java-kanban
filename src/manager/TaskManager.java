package manager;

import task.EpicTask;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    void removeTask(Task task);

    void removeEpicTask(EpicTask task);

    void removeSubTask(SubTask task);

    void clearTasks();

    void clearEpicTasks();

    void clearSubTasks();

    Task getTask(Task task);

    EpicTask getEpicTask(EpicTask task);

    SubTask getSubTask(SubTask task);

    List<Task> getListTasks();

    List<EpicTask> getListEpicTasks();

    List<SubTask> getListSubTasks();

    void addTask(Task task);

    void addEpicTask(EpicTask task);

    void addSubTask(SubTask task);

    void updateEpicTasks(EpicTask task);

    void updateTasks(Task task);

    void updateSubTasks(SubTask task);
}
