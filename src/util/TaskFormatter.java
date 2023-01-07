package util;

import manager.HistoryManager;
import task.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static util.CreationOfTime.formatter;


public class TaskFormatter {
    public static String toString(Task task) {

        if (!task.getType().equals(Type.SUBTASK)) {
            return task.getIdTask() + "," + task.getType() + "," + task.getNameTask() + "," + task.getStatus() + ","
                    + task.getDescriptionTask() + "," + task.getStartTime().format(formatter) + "," + task.getDuration();
        }
        SubTask task1 = (SubTask) task;
        return toStringSubtask(task1);
    }

    public static String toStringSubtask(SubTask task) {
        return task.getIdTask() + "," + task.getType() + "," + task.getNameTask() + "," + task.getStatus() + ","
                + task.getDescriptionTask() + "," + task.getStartTime().format(formatter) + "," + task.getDuration() + "," + task.getEpicTaskId();
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
        ZonedDateTime startTime = ZonedDateTime.parse(tasks[5], formatter);
        Duration duration = Duration.parse((tasks[6]));

        switch (type) {
            case TASK -> {
                return new Task(idTask, type, name, status, description, startTime, duration);
            }
            case EPICTASK -> {
                return new EpicTask(idTask, type, name, status, description, startTime, duration);
            }
            case SUBTASK -> {
                int idEpic = Integer.parseInt(tasks[7]);
                return new SubTask(idTask, type, name, status, description, startTime, duration, idEpic);
            }
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
