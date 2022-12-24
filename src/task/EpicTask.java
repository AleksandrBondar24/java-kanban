package task;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EpicTask extends Task {
    private final List<Integer> subTaskIds = new ArrayList<>();
    private ZonedDateTime endTime;

    public EpicTask() {
    }

    public EpicTask(Status status, Duration duration, ZonedDateTime startTime) {
        super(status, duration, startTime);
    }

    public EpicTask(int idTask, Type type, String nameTask, Status status, String descriptionTask, ZonedDateTime startTime, Duration duration) {
        super(idTask, type, nameTask, status, descriptionTask, startTime, duration);

    }

    public EpicTask(int id, Status status, Duration duration, ZonedDateTime startTime) {
        super(id, status, duration, startTime);
    }

    public void setEndTime(ZonedDateTime zonedDateTime) {
        this.endTime = zonedDateTime;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EpicTask epicTask = (EpicTask) o;
        return Objects.equals(subTaskIds, epicTask.subTaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIds);
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
