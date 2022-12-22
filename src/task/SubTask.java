package task;

import java.time.Duration;
import java.time.ZonedDateTime;

public class SubTask extends Task {
    private int epicTaskId;

    public SubTask() {
    }

    public SubTask(Status status, Duration duration, ZonedDateTime startTime, int epicTaskId) {
        super(status, duration, startTime);
        this.epicTaskId = epicTaskId;
    }

    public SubTask(int id, Status status, Duration duration, ZonedDateTime startTime, int epicTaskId) {
        super(id, status, duration, startTime);
        this.epicTaskId = epicTaskId;
    }

    public SubTask(int idTask, Type type, String nameTask, Status status, String descriptionTask, ZonedDateTime startTime, Duration duration, int epicTaskId) {
        super(idTask, type, nameTask, status, descriptionTask, startTime, duration);
        this.epicTaskId = epicTaskId;
    }

    public int getEpicTaskId() {
        return epicTaskId;
    }

    public void setEpicTaskId(int epicTaskId) {
        this.epicTaskId = epicTaskId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicTaskId=" + getEpicTaskId() +
                ", idTask=" + getIdTask() +
                ", nameTask='" + getNameTask() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", descriptionTask='" + getDescriptionTask() + '\'' +
                ", duration=" + getDuration() +
                ", startTime=" + getStartTime() +
                '}';
    }
}
