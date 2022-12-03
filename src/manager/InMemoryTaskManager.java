package manager;

import task.*;
import util.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, EpicTask> epicTask = new HashMap<>();
    protected final Map<Integer, SubTask> subTask = new HashMap<>();
    protected final HistoryManager history = Managers.getDefaultHistory();


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    @Override
    public void removeTask(Task task) {
        history.remove(task.getIdTask());
        tasks.remove(task.getIdTask());

    }

    @Override
    public void removeEpicTask(EpicTask task) {
        List<Integer> subId = epicTask.get(task.getIdTask()).getListSubTaskIds();

        for (Integer id : subId) {
            history.remove(id);
            subTask.remove(id);

        }
        history.remove(task.getIdTask());
        epicTask.remove(task.getIdTask());

    }

    @Override
    public void removeSubTask(SubTask task) {
        int epicId = subTask.get(task.getIdTask()).getEpicTaskId();
        EpicTask epictask = epicTask.get(epicId);

        epictask.getListSubTaskIds().remove((Integer) task.getIdTask());
        history.remove(task.getIdTask());
        subTask.remove(task.getIdTask());
        changeStatusEpicTask(epictask);

    }

    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public void clearEpicTasks() {
        clearSubTasks();
        epicTask.clear();
    }

    @Override
    public void clearSubTasks() {
        subTask.clear();
        for (Integer id : epicTask.keySet()) {
            epicTask.get(id).getListSubTaskIds().clear();
            changeStatusEpicTask(epicTask.get(id));
        }
    }

    @Override
    public Task getTask(int id) {
        history.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public EpicTask getEpicTask(int id) {
        history.add(epicTask.get(id));
        return epicTask.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        history.add(subTask.get(id));
        return subTask.get(id);
    }

    @Override
    public List<Task> getListTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<EpicTask> getListEpicTasks() {
        return new ArrayList<>(epicTask.values());
    }

    @Override
    public List<SubTask> getListSubTasks() {
        return new ArrayList<>(subTask.values());
    }

    @Override
    public void addTask(Task task) {
        task.setIdTask(id);
        tasks.put(task.getIdTask(), task);
        id++;
    }

    @Override
    public void addEpicTask(EpicTask task) {
        task.setIdTask(id);
        changeStatusEpicTask(task);
        epicTask.put(task.getIdTask(), task);
        id++;
    }

    @Override
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

    @Override
    public void updateEpicTasks(EpicTask task) {
        if (!epicTask.containsKey(task.getIdTask())) {
            return;
        }

        List<Integer> subId = (epicTask.get(task.getIdTask()).getListSubTaskIds());

        for (Integer id : subId) {
            task.addSubTaskId(id);
        }

        epicTask.put(task.getIdTask(), task);
    }

    @Override
    public void updateTasks(Task task) {
        if (!tasks.containsKey(task.getIdTask())) {
            return;
        }

        tasks.put(task.getIdTask(), task);
    }

    @Override
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