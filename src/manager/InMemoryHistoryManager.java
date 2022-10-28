package manager;

import task.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> historys = new LinkedList<>();

    @Override
    public List<Task> getHistory() {
        return new LinkedList<>(historys);
    }

    @Override
    public void add(Task task) {
        if (historys.size() == 10) {
            historys.removeFirst();
        }
        historys.add(task);
    }
}
