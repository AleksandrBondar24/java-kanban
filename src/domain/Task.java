package domain;

public class Task {
    private int idTask;
    private String nameTask;
    private String statusTask;
    private String descriptionTask;

    public Task(String nameTask, String statusTask, String descriptionTask) {
        this.nameTask = nameTask;
        this.statusTask = statusTask;
        this.descriptionTask = descriptionTask;
    }

    public Task(int idTask, String nameTask, String statusTask, String descriptionTask) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.statusTask = statusTask;
        this.descriptionTask = descriptionTask;
    }

    public Task(String nameTask, String descriptionTask) {
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.statusTask = "NEW";
    }

    public Task(int idTask, String nameTask, String descriptionTask) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.statusTask = "NEW";
    }

    public int getIdTask() {
        return idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getStatusTask() {
        return statusTask;
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

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }


    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", nameTask='" + nameTask + '\'' +
                ", statusTask='" + statusTask + '\'' +
                ", descriptionTask='" + descriptionTask + '\'' +
                '}';
    }
}