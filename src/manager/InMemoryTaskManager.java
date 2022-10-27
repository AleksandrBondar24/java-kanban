package manager;

import task.EpicTask;
import task.Status;
import task.SubTask;
import task.Task;
import utils.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager manager = Managers.getDefaultHistory();
    private int id = 1;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, EpicTask> epicTask = new HashMap<>();
    private final Map<Integer, SubTask> subTask = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(manager.getHistory());
    }

    @Override
    public void removeTask(Task task) {
        tasks.remove(task.getIdTask());
    }

    @Override
    public void removeEpicTask(EpicTask task) {
        List<Integer> subId = epicTask.get(task.getIdTask()).getListSubTaskIds();

        for (Integer id : subId) {
            subTask.remove(id);
        }
        epicTask.remove(task.getIdTask());
    }

    @Override
    public void removeSubTask(SubTask task) {
        int epicId = subTask.get(task.getIdTask()).getEpicTaskId();
        EpicTask epictask = epicTask.get(epicId);

        epictask.getListSubTaskIds().remove((Integer) task.getIdTask());
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
    public Task getTask(Task task) {
        manager.add(task);
        return tasks.get(task.getIdTask());
    }

    @Override
    public EpicTask getEpicTask(EpicTask task) {
        manager.add(task);
        return epicTask.get(task.getIdTask());
    }

    @Override
    public SubTask getSubTask(SubTask task) {
        manager.add(task);
        return subTask.get(task.getIdTask());
    }

    @Override
    public ArrayList<Task> getListTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<EpicTask> getListEpicTasks() {
        return new ArrayList<>(epicTask.values());
    }

    @Override
    public ArrayList<SubTask> getListSubTasks() {
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
