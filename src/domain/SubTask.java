package domain;

public class SubTask extends Task {
    private int epicTaskId;

    public SubTask(int idTask, String nameTask, String statusTask, String descriptionTask, int epicTaskId) {
        super(idTask, nameTask, descriptionTask);
        this.epicTaskId = epicTaskId;
        this.statusTask = statusTask;
    }

    public SubTask(String nameTask, String statusTask, String descriptionTask, int epicTaskId) {
        super(nameTask, descriptionTask);
        this.epicTaskId = epicTaskId;
        this.statusTask = statusTask;
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
                ", idTask=" + idTask +
                ", nameTask='" + nameTask + '\'' +
                ", statusTask='" + statusTask + '\'' +
                ", descriptionTask='" + descriptionTask + '\'' +
                '}';
    }
}
