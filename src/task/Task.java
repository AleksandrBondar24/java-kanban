package task;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    private int idTask;
    private String nameTask;
    private Status status;
    private String descriptionTask;
    private Type type;
    private int epicTaskId;
    private Duration duration;
    private LocalDateTime startTime ;


    public void setDuration(long hours) {
        this.duration = Duration.ofHours(hours);
    }

    public void setStartTime(int year, int month, int day, int hours, int minutes) {
        this.startTime = LocalDateTime.of(year, month, day, hours, minutes);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public Task() {
    }

    public Task(int idTask, Type type, String nameTask, Status status, String descriptionTask, LocalDateTime startTime, Duration duration) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.status = status;
        this.descriptionTask = descriptionTask;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getEpicTaskId() {
        return epicTaskId;
    }

    public void setEpicTaskId(int epictaskId) {
        this.epicTaskId = epicTaskId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", nameTask='" + nameTask + '\'' +
                ", status=" + status +
                ", descriptionTask='" + descriptionTask + '\'' +
                ", type=" + type +
                ", epicTaskId=" + epicTaskId +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
