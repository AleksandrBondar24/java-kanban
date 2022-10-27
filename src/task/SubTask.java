package task;

public class SubTask extends Task {
    private int epicTaskId;

    public SubTask(String nameTask, Status status, String descriptionTask, int epicTaskId) {
        super(nameTask, status, descriptionTask);
        this.epicTaskId = epicTaskId;
    }

    public SubTask(int idTask, String nameTask, Status status, String descriptionTask, int epicTaskId) {
        super(idTask, nameTask, status, descriptionTask);
        this.epicTaskId = epicTaskId;
    }

    public void setEpicTaskId(int epicTaskId) {
        this.epicTaskId = epicTaskId;
    }

    public int getEpicTaskId() {
        return epicTaskId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicTaskId=" + epicTaskId +
                ", idTask=" + getIdTask() +
                ", nameTask='" + getNameTask() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", descriptionTask='" + getDescriptionTask() + '\'' +
                '}'+ "\n";
    }
}
