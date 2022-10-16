package manager;

import domain.EpicTask;
import domain.SimpleTask;
import domain.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;
    private final HashMap<Integer, SimpleTask> SIMPLETASK = new HashMap<>();
    private final HashMap<Integer, EpicTask> EPICTASK = new HashMap<>();
    private final HashMap<Integer, SubTask> SUBTASK = new HashMap<>();

    public void removeSimpleTask(int idSimple) {
        SIMPLETASK.remove(idSimple);
    }

    public void removeEpicTask(int idEpic) {
        ArrayList<Integer> subId = EPICTASK.get(idEpic).getListSubTaskIds();

        for (Integer id : subId) {
            SUBTASK.remove(id);
        }
        EPICTASK.remove(idEpic);
    }

    public void removeSubTask(int idSub) {
        int epicId = SUBTASK.get(idSub).getEpicTaskId();
        EpicTask epictask = EPICTASK.get(epicId);

        epictask.getListSubTaskIds().remove((Integer) idSub);
        SUBTASK.remove(idSub);
        changeStatusEpicTask(epictask);
    }

    public void clearSimpleTasks() {
        SIMPLETASK.clear();
    }

    public void clearEpicTasks() {
        clearSubTasks();
        EPICTASK.clear();
    }

    public void clearSubTasks() {
        SUBTASK.clear();
        for (Integer id : EPICTASK.keySet()) {
            EPICTASK.get(id).getListSubTaskIds().clear();
            changeStatusEpicTask(EPICTASK.get(id));
        }
    }

    public SimpleTask getSimpleTask(SimpleTask task) {
        return SIMPLETASK.get(task.getIdTask());
    }

    public EpicTask getEpicTask(EpicTask task) {
        return EPICTASK.get(task.getIdTask());
    }

    public SubTask getSubTask(SubTask task) {
        return SUBTASK.get(task.getIdTask());
    }

    public ArrayList<SimpleTask> getListSimpleTasks() {
        return new ArrayList<>(SIMPLETASK.values());
    }

    public ArrayList<EpicTask> getListEpicTasks() {
        return new ArrayList<>(EPICTASK.values());
    }

    public ArrayList<SubTask> getListSubTasks() {
        return new ArrayList<>(SUBTASK.values());
    }

    public void addSimpleTask(SimpleTask task) {
        task.setIdTask(id);
        SIMPLETASK.put(task.getIdTask(), task);
        id++;
    }

    public void addEpicTask(EpicTask task) {
        task.setIdTask(id);
        EPICTASK.put(task.getIdTask(), task);
        id++;
    }

    public void addSubTask(SubTask task) {
        int epicTaskId = task.getEpicTaskId();
        EpicTask taskEpic = EPICTASK.get(epicTaskId);
        if (taskEpic == null) {
            return;
        }

        task.setIdTask(id);
        SUBTASK.put(task.getIdTask(), task);
        taskEpic.addSubTaskId(id);
        changeStatusEpicTask(taskEpic);
        id++;
    }

    public void updateEpicTasks(EpicTask task) {
        if (!EPICTASK.containsKey(task.getIdTask())) {
            return;
        }

        ArrayList<Integer> subId = (EPICTASK.get(task.getIdTask()).getListSubTaskIds());

        for (Integer id : subId) {
            task.addSubTaskId(id);
        }

        EPICTASK.put(task.getIdTask(), task);
    }

    public void updateSimpleTasks(SimpleTask task) {
        if (!SIMPLETASK.containsKey(task.getIdTask())) {
            return;
        }

        SIMPLETASK.put(task.getIdTask(), task);
    }

    public void updateSubTasks(SubTask task) {
        int epicTaskId = task.getEpicTaskId();
        EpicTask taskEpic = EPICTASK.get(epicTaskId);
        if (taskEpic == null || !SUBTASK.containsKey(task.getIdTask())) {
            return;
        }

        SUBTASK.put(task.getIdTask(), task);
        changeStatusEpicTask(EPICTASK.get(task.getEpicTaskId()));
    }

    private void changeStatusEpicTask(EpicTask task) {
        if (task.getListSubTaskIds().isEmpty()) {
            task.setStatusTask("NEW");
            return;
        }

        String status = null;

        for (Integer ids : task.getListSubTaskIds()) {
            if (status == null) {
                status = SUBTASK.get(ids).getStatusTask();
                continue;
            }

            if (!SUBTASK.get(ids).getStatusTask().equals(status)) {
                task.setStatusTask("IN_PROGRESS");
                return;
            }
        }
        task.setStatusTask(status);
    }
}
