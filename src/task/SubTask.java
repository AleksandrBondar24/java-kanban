package task;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private int epicTaskId;

    public SubTask() {
    }

    public SubTask(int idTask, Type type, String nameTask, Status status, String descriptionTask, LocalDateTime startTime, Duration duration, int epicTaskId) {
        super(idTask, type, nameTask, status, descriptionTask,startTime,duration);
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
