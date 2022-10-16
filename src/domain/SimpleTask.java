package domain;

public class SimpleTask extends Task {

    public SimpleTask(int idTask, String nameTask, String statusTask, String descriptionTask) {
        super(idTask, nameTask, descriptionTask);
        this.statusTask = statusTask;

    }

    public SimpleTask(String nameTask, String statusTask, String descriptionTask) {
        super(nameTask, descriptionTask);
        this.statusTask = statusTask;
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "idTask=" + idTask +
                ", nameTask='" + nameTask + '\'' +
                ", statusTask='" + statusTask + '\'' +
                ", descriptionTask='" + descriptionTask + '\'' +
                '}';
    }
}
