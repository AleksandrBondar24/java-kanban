package domain;

import java.util.ArrayList;

public class EpicTask extends Task {
    private final ArrayList<Integer> subTaskIds;

    public EpicTask(int idTask, String nameTask, String descriptionTask) {
        super(idTask, nameTask, descriptionTask);
        subTaskIds = new ArrayList<>();
    }

    public EpicTask(String nameTask, String descriptionTask) {
        super(nameTask, descriptionTask);
        subTaskIds = new ArrayList<>();
    }

    public void addSubTaskId(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public ArrayList<Integer> getListSubTaskIds() {
        return subTaskIds;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "idTask=" + getIdTask() +
                ", nameTask='" + getNameTask() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", descriptionTask='" + getDescriptionTask() + '\'' +
                '}';
    }
}
