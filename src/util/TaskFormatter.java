package util;

import manager.HistoryManager;
import task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static manager.InMemoryTaskManager.defaultDuration;
import static manager.InMemoryTaskManager.defaultStartTime;

public class TaskFormatter {
    public static String toString(Task task) {
        Optional<LocalDateTime> startTime = Optional.ofNullable(task.getStartTime());
        Optional<Duration> duration = Optional.ofNullable(task.getDuration());

        if (!task.getType().equals(Type.SUBTASK)) {
            return task.getIdTask() + "," + task.getType() + "," + task.getNameTask() + "," + task.getStatus() + ","
                    + task.getDescriptionTask() + "," + startTime.orElse(defaultStartTime) + "," + duration.orElse(Duration.ZERO);
        }
        return task.getIdTask() + "," + task.getType() + "," + task.getNameTask() + "," + task.getStatus() + ","
                + task.getDescriptionTask() + "," + startTime.orElse(defaultStartTime) + "," + duration.orElse(Duration.ZERO) + "," + task.getEpicTaskId();
    }

    public static String getHeader() {

        return "id,type,name,status,description,startTime,duration,epic";
    }

    public static Task fromString(String value) {
        String[] tasks = value.split(",");
        int idTask = Integer.parseInt(tasks[0]);
        String name = tasks[2];
        Status status = Status.valueOf(tasks[3]);
        String description = tasks[4];
        Type type = Type.valueOf(tasks[1]);
        LocalDateTime startTime = LocalDateTime.parse(tasks[5]);
        if (startTime.isEqual(defaultStartTime)) {
            startTime = null;
        }
        Duration duration = Duration.parse((tasks[6]));
        if (duration.equals(Duration.ZERO)) {
            duration = null;
        }

        switch (type) {
            case TASK:
                return new Task(idTask, type, name, status, description,startTime,duration);
            case EPICTASK:
                return new EpicTask(idTask, type, name, status, description,startTime,duration);
            case SUBTASK:
                int idEpic = Integer.parseInt(tasks[7]);
                return new SubTask(idTask, type, name, status, description,startTime,duration,idEpic);
        }
        return null;
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> historys = manager.getHistory();
        StringBuilder histor = new StringBuilder();
        for (int i = 0; i < historys.size(); i++) {
            if (i == historys.size() - 1) {
                histor.append(historys.get(i).getIdTask());
            } else {
                histor.append(historys.get(i).getIdTask()).append(",");
            }
        }
        return histor.toString();
    }

    public static List<Integer> historyFromString(String value) {
        String[] idTask = value.split(",");
        List<Integer> idTasks = new ArrayList<>();
        for (String s : idTask) {
            idTasks.add(Integer.parseInt(s));
        }
        return idTasks;
    }
}
