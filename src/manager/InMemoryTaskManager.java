package manager;

import task.*;
import util.Managers;

import java.time.ZonedDateTime;
import java.util.*;

import static util.СreationOfTime.defaultDuration;
import static util.СreationOfTime.defaultStartTime;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, EpicTask> epicTask = new HashMap<>();
    protected final Map<Integer, SubTask> subTask = new HashMap<>();
    protected final HistoryManager history = Managers.getDefaultHistory();
    protected final Set<Task> prioritizedTasks = new TreeSet<>((o1, o2) -> {

        if (o1.getStartTime().isAfter(o2.getStartTime())) {
            return 1;
        } else if (o1.getStartTime().isBefore(o2.getStartTime())) {
            return -1;
        } else {
            return 0;
        }
    }

    );

    public Map<Integer,Task> getTasks() {
        return tasks;
    }

    public Map<Integer, EpicTask> getEpicTask() {
        return epicTask;
    }

    public Map<Integer, SubTask> getSubTask() {
        return subTask;
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    @Override
    public String removeTask(Task task) {
        if (!tasks.containsKey(task.getIdTask())) {
            return "Задачи для удаления не существует!";
        }
        history.remove(task.getIdTask());
        tasks.remove(task.getIdTask());
        return "Задача успешно удалена!";
    }

    @Override
    public String removeEpicTask(EpicTask task) {
        if (!tasks.containsKey(task.getIdTask())) {
            return "Задачи для удаления не существует!";
        }
        List<Integer> subId = epicTask.get(task.getIdTask()).getListSubTaskIds();

        for (Integer id : subId) {
            history.remove(id);
            subTask.remove(id);

        }
        history.remove(task.getIdTask());
        epicTask.remove(task.getIdTask());
        return "Задача успешно удалена!";
    }

    @Override
    public String removeSubTask(SubTask task) {
        if (!tasks.containsKey(task.getIdTask())) {
            return "Задачи для удаления не существует!";
        }
        int epicId = subTask.get(task.getIdTask()).getEpicTaskId();
        EpicTask epictask = epicTask.get(epicId);

        epictask.getListSubTaskIds().remove((Integer) task.getIdTask());
        history.remove(task.getIdTask());
        subTask.remove(task.getIdTask());
        changeStatusEpicTask(epictask);
        setStartTimeEpic(epictask);
        setDurationEpic(epictask);
        return "Задача успешно удалена!";
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
        checkIntersections(task);
        task.setIdTask(id);
        tasks.put(task.getIdTask(), task);
        prioritizedTasks.add(task);
        id++;
    }

    @Override
    public void addEpicTask(EpicTask task) {
        task.setIdTask(id);
        changeStatusEpicTask(task);
        setStartTimeEpic(task);
        setDurationEpic(task);
        epicTask.put(task.getIdTask(), task);
        id++;
    }

    @Override
    public void addSubTask(SubTask task) {
        checkIntersections(task);
        int epicTaskId = task.getEpicTaskId();
        EpicTask taskEpic = epicTask.get(epicTaskId);
        if (taskEpic == null) {
            return;
        }

        task.setIdTask(id);
        subTask.put(task.getIdTask(), task);
        prioritizedTasks.add(task);
        taskEpic.addSubTaskId(id);
        changeStatusEpicTask(taskEpic);
        setStartTimeEpic(taskEpic);
        setDurationEpic(taskEpic);
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
        checkIntersections(task);
        tasks.put(task.getIdTask(), task);
    }

    @Override
    public void updateSubTasks(SubTask task) {
        int epicTaskId = task.getEpicTaskId();
        EpicTask taskEpic = epicTask.get(epicTaskId);
        if (taskEpic == null || !subTask.containsKey(task.getIdTask())) {
            return;
        }

        checkIntersections(task);
        subTask.put(task.getIdTask(), task);
        changeStatusEpicTask(epicTask.get(task.getEpicTaskId()));
    }

    public void changeStatusEpicTask(EpicTask task) {
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

    public void setDurationEpic(EpicTask task) {
        if (task.getListSubTaskIds().isEmpty()) {
            task.setDuration(defaultDuration);
            return;
        }
        long hours = task.getListSubTaskIds().stream().mapToLong(id -> subTask.get(id).getDuration().toHours()).sum();
        task.setDuration(hours);
    }

    public void setStartTimeEpic(EpicTask task) {
        if (task.getListSubTaskIds().isEmpty()) {
            task.setStartTime(defaultStartTime);
            return;
        }
        ZonedDateTime startTime = subTask.values().stream().filter(tasks -> tasks.getEpicTaskId() ==
                task.getIdTask()).min((o1, o2) -> {
                    if (o1.getStartTime().isBefore(o2.getStartTime())) {
                        return -1;
                    }
                    if (o1.getStartTime().isAfter(o2.getStartTime())) {
                        return 1;
                    }
                    return 0;
                }).get().getStartTime();
            task.setStartTime(startTime);
        }

        public void getEndTimeEpic (EpicTask task){
            if (task.getListSubTaskIds().isEmpty()) {
                return;
            }
            ZonedDateTime startTime = subTask.values().stream().filter(tasks -> tasks.getEpicTaskId() ==
                    task.getIdTask()).max((o1, o2) -> {
                if (o1.getStartTime().isBefore(o2.getStartTime())) {
                    return -1;
                }
                if (o1.getStartTime().isAfter(o2.getStartTime())) {
                    return 1;
                }
                return 0;
            }).get().getStartTime();
            task.setEndTime(startTime);
        }

        private void checkIntersections (Task task){
            if (task.getStartTime() == null) {
                return;
            }
            boolean intersections = prioritizedTasks.stream().anyMatch(tasks -> task.getStartTime().isBefore(tasks.getEndTime()) &&
                    task.getStartTime().isAfter(tasks.getStartTime()));
            if (intersections) {
                throw new RuntimeEnumerationException("Ошибка пересечения времени выполения задачи!Выберите новое время.");
            }
        }
    }