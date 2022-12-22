package manager;

import task.EpicTask;
import task.SubTask;
import task.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    List<Task> getHistory();

    String removeTask(Task task);

    String removeEpicTask(EpicTask task);

    String removeSubTask(SubTask task);

    void clearTasks();

    void clearEpicTasks();

    void clearSubTasks();

    Task getTask(int id);

    EpicTask getEpicTask(int id);

    SubTask getSubTask(int id);

    List<Task> getListTasks();

    List<EpicTask> getListEpicTasks();

    List<SubTask> getListSubTasks();

    String addTask(Task task);

    String addEpicTask(EpicTask task);

    String addSubTask(SubTask task);

    String updateEpicTasks(EpicTask task);

    String updateTasks(Task task);

    String updateSubTasks(SubTask task);

    List<Task> getPrioritizedTasks();

    void changeStatusEpicTask(EpicTask task);

    Map<Integer, Task> getTasks();

    Map<Integer, EpicTask> getEpicTasks();

    Map<Integer, SubTask> getSubTasks();

    void setStartTimeEpic(EpicTask epicTask);

    void setDurationEpic(EpicTask epicTask);

    void checkIntersections(Task task1);
}


