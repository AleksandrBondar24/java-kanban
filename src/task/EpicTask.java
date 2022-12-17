package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {
    private final List<Integer> subTaskIds = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public EpicTask() {
    }

    public EpicTask(int idTask, Type type, String nameTask, Status status, String descriptionTask, LocalDateTime startTime, Duration duration) {
        super(idTask, type, nameTask, status, descriptionTask, startTime, duration);

    }

    public void setStartTime(LocalDateTime localDateTime) {
        this.startTime = localDateTime;
    }

    public void setEndTime(LocalDateTime localDateTime) {
        this.endTime = localDateTime;
    }


    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public LocalDateTime getEndTime() {
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
