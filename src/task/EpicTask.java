package task;

import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {
    private final List<Integer> subTaskIds = new ArrayList<>();

    public EpicTask() {
    }

    public EpicTask(int idTask, Type type, String nameTask, Status status, String descriptionTask) {
        super(idTask, type, nameTask, status, descriptionTask);
    }

    public void addSubTaskId(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public List<Integer> getListSubTaskIds() {
        return subTaskIds;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "idTask=" + getIdTask() +
                ", nameTask='" + getNameTask() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", descriptionTask='" + getDescriptionTask() + '\'' +
                "subTaskIds=" + subTaskIds +
                '}';
    }
}
