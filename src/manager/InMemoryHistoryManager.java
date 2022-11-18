package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> historyTask = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;
    private int size = 0;

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task) {
        remove(task.getIdTask());
        linkLast(task);
        historyTask.put(task.getIdTask(), last);
    }

    @Override
    public void remove(int id) {
        Node<Task> node = historyTask.get(id);
        if (node == null) {
            return;
        }
        final Node<Task> next = node.next;
        final Node<Task> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.task = null;
        size--;
    }

    private void linkLast(Task task) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, task, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    private List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        Node<Task> node = first;
        for (int i = 0; i < size; i++) {
            if (node.task == null) {
                throw new NoSuchElementException();
            } else {
                list.add(node.task);
            }
            node = node.next;
        }
        return list;
    }

    static class Node<Task> {
        public Node<Task> next;
        public Node<Task> prev;
        public Task task;

        public Node(Node<Task> prev, Task task, Node<Task> next) {
            this.next = next;
            this.prev = prev;
            this.task = task;
        }
    }
}
