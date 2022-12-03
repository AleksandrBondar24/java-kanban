package util;

import manager.HistoryManager;
import task.*;

import java.util.ArrayList;
import java.util.List;

public class TaskFormatter {
    public static String toString(Task task) {
        if (!task.getType().equals(Type.SUBTASK)) {
            return task.getIdTask() + "," + task.getType() + "," + task.getNameTask() + "," + task.getStatus() + ","
                    + task.getDescriptionTask();
        }
        return task.getIdTask() + "," + task.getType() + "," + task.getNameTask() + "," + task.getStatus() + ","
                + task.getDescriptionTask() + "," + task.getEpicTaskId();
    }

    public static String getHeader() {

        return "id,type,name,status,description,epic";
    }

    public static Task fromString(String value) {
        String[] tasks = value.split(",");
        int idTask = Integer.parseInt(tasks[0]);
        String name = tasks[2];
        Status status = Status.valueOf(tasks[3]);
        String description = tasks[4];
        Type type = Type.valueOf(tasks[1]);
        switch (type) {
            case TASK:
                return new Task(idTask, type, name, status, description);
            case EPICTASK:
                return new EpicTask(idTask, type, name, status, description);
            case SUBTASK:
                int idEpic = Integer.parseInt(tasks[5]);
                return new SubTask(idTask, type, name, status, description, idEpic);
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
