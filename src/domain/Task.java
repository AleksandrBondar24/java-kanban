package domain;

public class Task {
    private int idTask;
    private String nameTask;
    private Status status;
    private String descriptionTask;

    public Task(String nameTask, Status status, String descriptionTask) {
        this.nameTask = nameTask;
        this.status = status;
        this.descriptionTask = descriptionTask;
    }

    public Task(int idTask, String nameTask, Status status, String descriptionTask) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.status = status;
        this.descriptionTask = descriptionTask;
    }

    public Task(String nameTask, String descriptionTask) {
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.status = Status.NEW;
    }

    public Task(int idTask, String nameTask, String descriptionTask) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.status = Status.NEW;
    }

    public int getIdTask() {
        return idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public Status getStatus() {
        return status;
    }
    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", nameTask='" + nameTask + '\'' +
                ", status='" + status + '\'' +
                ", descriptionTask='" + descriptionTask + '\'' +
                '}';
    }
}
