import manager.InMemoryTaskManager;
import manager.RuntimeEnumerationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void setUp() {
        manager = new InMemoryTaskManager();
        createTask();
    }

    @Test
    public void shouldReturnNotNullListTask() {

        Assertions.assertNotNull(manager.getTasks());
    }

    @Test
    public void shouldReturnListTask() {

        Assertions.assertEquals(2, manager.getTasks().size());
    }

    @Test
    public void shouldAssignStartField() {
        manager.setStartTimeEpic(epicTask);

        Assertions.assertEquals(subTask1.getStartTime(), epicTask.getStartTime());
    }

    @Test
    public void shouldAssignDurationField() {
        manager.setDurationEpic(epicTask);
        long x = subTask.getDuration().toHours() + subTask1.getDuration().toHours();

        Assertions.assertEquals(x, epicTask.getDuration().toHours());
    }

    @Test
    public void shouldThrowExceptionOnIntersection() {

        final RuntimeEnumerationException exception = assertThrows(RuntimeEnumerationException.class,
                () -> manager.checkIntersections(task1));
        Assertions.assertEquals("Ошибка пересечения времени выполения задачи!Выберите новое время.",
                exception.getMessage());
    }
}
