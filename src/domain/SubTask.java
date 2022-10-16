package domain;

public class SubTask extends Task {
    private int epicTaskId;

    public SubTask(String nameTask, String statusTask, String descriptionTask, int epicTaskId) {
        super(nameTask, statusTask, descriptionTask);
        this.epicTaskId = epicTaskId;
    }

    public SubTask(int idTask, String nameTask, String statusTask, String descriptionTask, int epicTaskId) {
        super(idTask, nameTask, statusTask, descriptionTask);
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
                ", statusTask='" + getStatusTask() + '\'' +
                ", descriptionTask='" + getDescriptionTask() + '\'' +
                '}';
    }
}
