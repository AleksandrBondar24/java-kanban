package task;

import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {
    private final List<Integer> subTaskIds;

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
                '}' + "\n";
    }
}
