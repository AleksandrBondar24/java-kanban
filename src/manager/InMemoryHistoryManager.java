package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> historys = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historys);
    }

    @Override
    public void add(Task task) {
        if (historys.size() == 10) {
            historys.remove(0);
        }
        historys.add(task);
    }
}
