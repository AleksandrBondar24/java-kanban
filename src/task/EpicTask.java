package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {
    private final List<Integer> subTaskIds = new ArrayList<>();

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Duration duration;

    public EpicTask() {
    }
    public EpicTask(int idTask, Type type, String nameTask, Status status, String descriptionTask, ZonedDateTime startTime, Duration duration) {
        super(idTask, type, nameTask, status, descriptionTask, startTime, duration);

    }

    public void setStartTime(ZonedDateTime zonedDateTime) {
        this.startTime = zonedDateTime;
    }

    public void setEndTime(ZonedDateTime zonedDateTime) {
        this.endTime = zonedDateTime;
    }
    public void setDuration(long hours) {
        this.duration = Duration.ofHours(hours);
    }


    @Override
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    @Override
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void addSubTaskId(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public List<Integer> getListSubTaskIds() {
        return subTaskIds;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "idTask=" + getIdTask() +
                ", nameTask='" + getNameTask() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", descriptionTask='" + getDescriptionTask() + '\'' +
                ", duration=" + getDuration() +
                ", startTime=" + getStartTime() +
                "subTaskIds=" + subTaskIds +
                '}';
    }

}
