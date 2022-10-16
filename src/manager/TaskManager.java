package manager;

import domain.EpicTask;
import domain.SubTask;
import domain.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;
    private final HashMap<Integer, Task> task = new HashMap<>();
    private final HashMap<Integer, EpicTask> epicTask = new HashMap<>();
    private final HashMap<Integer, SubTask> subTask = new HashMap<>();

    public void removeTask(int idTask) {
        task.remove(idTask);
    }

    public void removeEpicTask(int idEpic) {
        ArrayList<Integer> subId = epicTask.get(idEpic).getListSubTaskIds();

        for (Integer id : subId) {
            subTask.remove(id);
        }
        epicTask.remove(idEpic);
    }

    public void removeSubTask(int idSub) {
        int epicId = subTask.get(idSub).getEpicTaskId();
        EpicTask epictask = epicTask.get(epicId);

        epictask.getListSubTaskIds().remove((Integer) idSub);
        subTask.remove(idSub);
        changeStatusEpicTask(epictask);
    }

    public void clearTasks() {
        task.clear();
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
        return this.task.get(task.getIdTask());
    }

    public EpicTask getEpicTask(EpicTask task) {
        return epicTask.get(task.getIdTask());
    }

    public SubTask getSubTask(SubTask task) {
        return subTask.get(task.getIdTask());
    }

    public ArrayList<Task> getListTasks() {
        return new ArrayList<>(task.values());
    }

    public ArrayList<EpicTask> getListEpicTasks() {
        return new ArrayList<>(epicTask.values());
    }

    public ArrayList<SubTask> getListSubTasks() {
        return new ArrayList<>(subTask.values());
    }

    public void addTask(Task task) {
        task.setIdTask(id);
        this.task.put(task.getIdTask(), task);
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
        if (!this.task.containsKey(task.getIdTask())) {
            return;
        }

        this.task.put(task.getIdTask(), task);
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
            task.setStatusTask("NEW");
            return;
        }

        String status = null;

        for (Integer ids : task.getListSubTaskIds()) {
            if (status == null) {
                status = subTask.get(ids).getStatusTask();
                continue;
            }

            if (!subTask.get(ids).getStatusTask().equals(status)) {
                task.setStatusTask("IN_PROGRESS");
                return;
            }
        }
        task.setStatusTask(status);
    }
}
