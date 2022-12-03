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
        String examle1 = tasks[0];
        String examle2 = tasks[1];
        String examle3 = tasks[2];
        String examle4 = tasks[3];
        String examle5 = tasks[4];
        Type type = Type.valueOf(tasks[1]);
        switch (type) {
            case TASK:
                return new Task(Integer.parseInt(examle1), Type.valueOf(examle2), examle3, Status.valueOf(examle4),
                        examle5);
            case EPICTASK:
                return new EpicTask(Integer.parseInt(examle1), Type.valueOf(examle2), examle3,
                        Status.valueOf(examle4), examle5);
            case SUBTASK:
                String examle6 = tasks[5];
                return new SubTask(Integer.parseInt(examle1), Type.valueOf(examle2), examle3,
                        Status.valueOf(examle4), examle5, Integer.parseInt(examle6));
        }
        return null;
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> historys = manager.getHistory();
        StringBuilder histor = new StringBuilder();
        int count = 1;
        for (Task key : historys) {
            if (historys.size() == count) {
                histor.append(key.getIdTask());
            } else {
                histor.append(key.getIdTask()).append(",");
            }
            count++;
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
