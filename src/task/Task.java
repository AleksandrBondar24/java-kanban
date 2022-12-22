package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static util.СreationOfTime.*;

public class Task {

    private int idTask;
    private String nameTask;
    private Status status;
    private String descriptionTask;
    private Type type;
    private int epicTaskId;
    private Duration duration = defaultDuration;
    private ZonedDateTime startTime = defaultStartTime;


    public Task() {
    }

    public Task(int idTask, String nameTask, Status status, String descriptionTask, Type type) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.status = status;
        this.descriptionTask = descriptionTask;
        this.type = type;
    }

    public Task(Status status, Duration duration, ZonedDateTime startTime) {
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(int id, Status status, Duration duration, ZonedDateTime startTime) {
        this.idTask = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(Status status) {
        this.status = status;
    }

    public Task(int idTask, Type type, String nameTask, Status status, String descriptionTask, ZonedDateTime startTime, Duration duration) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.status = status;
        this.descriptionTask = descriptionTask;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(int year, int month, int day, int hours, int minutes) {
        this.startTime = ZonedDateTime.of(LocalDateTime.of(year, month, day, hours, minutes), zoneId);
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ZonedDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
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
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

}
