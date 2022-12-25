package manager;

import task.*;
import util.Managers;

import java.time.ZonedDateTime;
import java.util.*;

import static util.CreationOfTime.defaultDuration;
import static util.CreationOfTime.defaultStartTime;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, EpicTask> epicTasks = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final HistoryManager history = Managers.getDefaultHistory();
    protected final Map<ZonedDateTime, Task> prioritizedTasks = new TreeMap<>((o1, o2) -> {

        if (o1.isAfter(o2)) {
            return 1;
        } else if (o1.isBefore(o2)) {
            return -1;
        } else {
            return 0;
        }
    }

    );

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, EpicTask> getEpicTasks() {
        return epicTasks;
    }

    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks.values());
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    @Override
    public String removeTask(Task task) {
        if (tasks.isEmpty()) {
            return "Задачи для удаления не существует!";
        }
        if (!tasks.containsKey(task.getIdTask())) {
            return "Неверный идентификатор задачи";
        }
        history.remove(task.getIdTask());
        tasks.remove(task.getIdTask());
        return "Задача успешно удалена!";
    }

    @Override
    public String removeEpicTask(EpicTask task) {
        if (epicTasks.isEmpty()) {
            return "Задачи для удаления не существует!";
        }
        if (!epicTasks.containsKey(task.getIdTask())) {
            return "Неверный идентификатор задачи";
        }
        List<Integer> subId = epicTasks.get(task.getIdTask()).getListSubTaskIds();

        for (Integer id : subId) {
            history.remove(id);
            subTasks.remove(id);

        }
        history.remove(task.getIdTask());
        epicTasks.remove(task.getIdTask());
        return "Задача успешно удалена!";
    }

    @Override
    public String removeSubTask(SubTask task) {
        if (subTasks.isEmpty()) {
            return "Задачи для удаления не существует!";
        }
        if (!subTasks.containsKey(task.getIdTask())) {
            return "Неверный идентификатор задачи";
        }
        int epicId = subTasks.get(task.getIdTask()).getEpicTaskId();
        EpicTask epictask = epicTasks.get(epicId);

        epictask.getListSubTaskIds().remove((Integer) task.getIdTask());
        history.remove(task.getIdTask());
        subTasks.remove(task.getIdTask());
        changeStatusEpicTask(epictask);
        setStartTimeEpic(epictask);
        setDurationEpic(epictask);
        return "Задача успешно удалена!";
    }

    @Override
    public void clearTasks() {
        for (Task task : tasks.values()) {
            prioritizedTasks.remove(task.getStartTime());
        }
        tasks.clear();
    }

    @Override
    public void clearEpicTasks() {
        clearSubTasks();
        epicTasks.clear();
    }

    @Override
    public void clearSubTasks() {
        for (SubTask subTask : subTasks.values()) {
            prioritizedTasks.remove(subTask.getStartTime());
        }
        subTasks.clear();
        for (Integer id : epicTasks.keySet()) {
            epicTasks.get(id).getListSubTaskIds().clear();
            changeStatusEpicTask(epicTasks.get(id));
        }
    }

    @Override
    public Task getTask(int id) {
        if (tasks.isEmpty()) {
            return null;
        }
        if (!tasks.containsKey(id)) {
            return null;
        }
        history.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public EpicTask getEpicTask(int id) {
        if (epicTasks.isEmpty()) {
            return null;
        }
        if (!epicTasks.containsKey(id)) {
            return null;
        }
        history.add(epicTasks.get(id));
        return epicTasks.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        if (subTasks.isEmpty()) {
            return null;
        }
        if (!subTasks.containsKey(id)) {
            return null;
        }
        history.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public List<Task> getListTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<EpicTask> getListEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    @Override
    public List<SubTask> getListSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public String addTask(Task task) {
        checkIntersections(task);
        task.setIdTask(id);
        tasks.put(task.getIdTask(), task);
        prioritizedTasks.put(task.getStartTime(), task);
        id++;
        return "Задача успешно добавлена!";
    }

    @Override
    public String addEpicTask(EpicTask task) {
        task.setIdTask(id);
        changeStatusEpicTask(task);
        setStartTimeEpic(task);
        setDurationEpic(task);
        epicTasks.put(task.getIdTask(), task);
        id++;
        return "Эпикзадача успешно добавлена!";
    }

    @Override
    public String addSubTask(SubTask task) {
        checkIntersections(task);
        int epicTaskId = task.getEpicTaskId();
        EpicTask taskEpic = epicTasks.get(epicTaskId);
        if (taskEpic == null) {
            return "Отсутствует эпик для этой подзадачи!";
        }

        task.setIdTask(id);
        subTasks.put(task.getIdTask(), task);
        prioritizedTasks.put(task.getStartTime(), task);
        taskEpic.addSubTaskId(id);
        changeStatusEpicTask(taskEpic);
        setStartTimeEpic(taskEpic);
        setDurationEpic(taskEpic);
        id++;
        return "Подзадача успешно добавлена!";
    }

    @Override
    public String updateEpicTasks(EpicTask task) {
        if (epicTasks.isEmpty()) {
            return "Задачи для обновления не существует!";
        }
        if (!epicTasks.containsKey(task.getIdTask())) {
            return "Неверный идентификатор задачи";
        }

        epicTasks.put(task.getIdTask(), task);
        return "Задача успешно обновлена!";
    }

    @Override
    public String updateTasks(Task task) {
        if (tasks.isEmpty()) {
            return "Задачи для обновления не существует!";
        }
        if (!tasks.containsKey(task.getIdTask())) {
            return "Неверный идентификатор задачи";
        }
        tasks.put(task.getIdTask(), task);
        return "Задача успешно обновлена!";
    }

    @Override
    public String updateSubTasks(SubTask task) {
        if (subTasks.isEmpty()) {
            return "Задачи для обновления не существует!";
        }
        if (!subTasks.containsKey(task.getIdTask())) {
            return "Неверный идентификатор задачи";
        }

        subTasks.put(task.getIdTask(), task);
        changeStatusEpicTask(epicTasks.get(task.getEpicTaskId()));
        return "Задача успешно обновлена!";
    }

    public void changeStatusEpicTask(EpicTask task) {
        if (task.getListSubTaskIds().isEmpty()) {
            task.setStatus(Status.NEW);
            return;
        }

        Status status = null;

        for (Integer ids : task.getListSubTaskIds()) {
            if (status == null) {
                status = subTasks.get(ids).getStatus();
                continue;
            }

            if (!subTasks.get(ids).getStatus().equals(status)) {
                task.setStatus(Status.IN_PROGRESS);
                return;
            }
        }
        task.setStatus(status);
    }

    public void setDurationEpic(EpicTask task) {
        if (task.getListSubTaskIds().isEmpty()) {
            task.setDuration(defaultDuration.toHours());
            return;
        }
        long hours = task.getListSubTaskIds().stream().mapToLong(id -> subTasks.get(id).getDuration().toHours()).sum();
        task.setDuration(hours);
    }

    public void setStartTimeEpic(EpicTask task) {
        if (task.getListSubTaskIds().isEmpty()) {
            task.setStartTime(defaultStartTime);
            return;
        }
        ZonedDateTime startTime = subTasks.values().stream().filter(tasks -> tasks.getEpicTaskId() ==
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

        /*public void getEndTimeEpic (EpicTask task){
            if (task.getListSubTaskIds().isEmpty()) {
                return;
            }
            ZonedDateTime startTime = subTasks.values().stream().filter(tasks -> tasks.getEpicTaskId() ==
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
        }*/

    public void checkIntersections(Task task) {
        if (task.getStartTime() == null) {
            return;
        }
        for (Task task1 : prioritizedTasks.values()) {
            if (task.getStartTime().isBefore(task1.getEndTime()) &&
                    task.getStartTime().isAfter(task1.getStartTime()) || task.getStartTime().equals(task1.getStartTime())) {
                throw new RuntimeEnumerationException("Ошибка пересечения времени выполения задачи!Выберите новое время.");
            }
        }
    }
}