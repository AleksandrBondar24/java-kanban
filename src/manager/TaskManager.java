package manager;

import domain.EpicTask;
import domain.Status;
import domain.SubTask;
import domain.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, EpicTask> epicTask = new HashMap<>();
    private final HashMap<Integer, SubTask> subTask = new HashMap<>();

    public void removeTask(Task task) {
        tasks.remove(task.getIdTask());
    }

    public void removeEpicTask(EpicTask task) {
        ArrayList<Integer> subId = epicTask.get(task.getIdTask()).getListSubTaskIds();

        for (Integer id : subId) {
            subTask.remove(id);
        }
        epicTask.remove(task.getIdTask());
    }

    public void removeSubTask(SubTask task) {
        int epicId = subTask.get(task.getIdTask()).getEpicTaskId();
        EpicTask epictask = epicTask.get(epicId);

        epictask.getListSubTaskIds().remove((Integer) task.getIdTask());
        subTask.remove(task.getIdTask());
        changeStatusEpicTask(epictask);
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void clearEpicTasks() {
        clearSubTasks();
        epicTask.clear();
    }

    public void clearSubTasks() {
        subTask.clear();
        for (Integer id : epicTask.keySet()) {
            epicTask.get(id).getListSubTaskIds().clear();
            changeStatusEpicTask(epicTask.get(id));
        }
    }

    public Task getTask(Task task) {
        return tasks.get(task.getIdTask());
    }

    public EpicTask getEpicTask(EpicTask task) {
        return epicTask.get(task.getIdTask());
    }

    public SubTask getSubTask(SubTask task) {
        return subTask.get(task.getIdTask());
    }

    public ArrayList<Task> getListTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<EpicTask> getListEpicTasks() {
        return new ArrayList<>(epicTask.values());
    }

    public ArrayList<SubTask> getListSubTasks() {
        return new ArrayList<>(subTask.values());
    }

    public void addTask(Task task) {
        task.setIdTask(id);
        tasks.put(task.getIdTask(), task);
        id++;
    }

    public void addEpicTask(EpicTask task) {
        task.setIdTask(id);
        epicTask.put(task.getIdTask(), task);
        id++;
    }

    public void addSubTask(SubTask task) {
        int epicTaskId = task.getEpicTaskId();
        EpicTask taskEpic = epicTask.get(epicTaskId);
        if (taskEpic == null) {
            return;
        }

        task.setIdTask(id);
        subTask.put(task.getIdTask(), task);
        taskEpic.addSubTaskId(id);
        changeStatusEpicTask(taskEpic);
        id++;
    }

    public void updateEpicTasks(EpicTask task) {
        if (!epicTask.containsKey(task.getIdTask())) {
            return;
        }

        ArrayList<Integer> subId = (epicTask.get(task.getIdTask()).getListSubTaskIds());

        for (Integer id : subId) {
            task.addSubTaskId(id);
        }

        epicTask.put(task.getIdTask(), task);
    }

    public void updateTasks(Task task) {
        if (!tasks.containsKey(task.getIdTask())) {
            return;
        }

        tasks.put(task.getIdTask(), task);
    }

    public void updateSubTasks(SubTask task) {
        int epicTaskId = task.getEpicTaskId();
        EpicTask taskEpic = epicTask.get(epicTaskId);
        if (taskEpic == null || !subTask.containsKey(task.getIdTask())) {
            return;
        }

        subTask.put(task.getIdTask(), task);
        changeStatusEpicTask(epicTask.get(task.getEpicTaskId()));
    }

    private void changeStatusEpicTask(EpicTask task) {
        if (task.getListSubTaskIds().isEmpty()) {
            task.setStatus(Status.NEW);
            return;
        }

        Status status = null;

        for (Integer ids : task.getListSubTaskIds()) {
            if (status == null) {
                status = subTask.get(ids).getStatus();
                continue;
            }

            if (!subTask.get(ids).getStatus().equals(status)) {
                task.setStatus(Status.IN_PROGRESS);
                return;
            }
        }
        task.setStatus(status);
    }
}
