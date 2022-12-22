import manager.RuntimeEnumerationException;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.Managers.getDefault;
import static util.СreationOfTime.zoneId;

public class InMemoryTaskManagerTest {

    private TaskManager manager;
    private Task task;
    private Task task1;
    private EpicTask epicTask;
    private SubTask subTask;
    private SubTask subTask1;

    @BeforeEach
    public void createManagerTaskEpicSubtask() {
        manager = getDefault();
        Duration duration = Duration.ofHours(44);
        Duration duration1 = Duration.ofHours(34);
        Duration duration2 = Duration.ofHours(20);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 02, 12, 34), zoneId);
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 11, 12, 10, 40), zoneId);
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(LocalDateTime.of(2022, 10, 04, 7, 34), zoneId);
        task = new Task(-1, Type.TASK, "1task", Status.NEW, "ok", zonedDateTime2, duration2);
        task1 = new Task(Status.IN_PROGRESS, duration1, zonedDateTime1);
        epicTask = new EpicTask();
        subTask =  new SubTask(Status.IN_PROGRESS, duration1, zonedDateTime1, 2);
        subTask1 = new SubTask(Status.NEW, duration, zonedDateTime, 2);
        manager.addTask(task);
        manager.addEpicTask(epicTask);
        manager.addSubTask(subTask);
        manager.addSubTask(subTask1);
    }

    @Test
    public void shouldAssignStartField() {
        manager.setStartTimeEpic(epicTask);

        Assertions.assertEquals(subTask.getStartTime(),epicTask.getStartTime());
    }
    @Test
    public void shouldAssignDurationField() {
        manager.setDurationEpic(epicTask);
        long x = subTask.getDuration().toHours() + subTask1.getDuration().toHours();

        Assertions.assertEquals(x,epicTask.getDuration().toHours());
    }
    @Test
    public void shouldThrowExceptionOnIntersection() {

        final RuntimeEnumerationException exception = assertThrows(RuntimeEnumerationException.class,
                () -> manager.checkIntersections(task1));
        Assertions.assertEquals("Ошибка пересечения времени выполения задачи!Выберите новое время.",
                exception.getMessage());
    }

}
