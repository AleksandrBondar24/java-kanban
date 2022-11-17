import manager.TaskManager;
import task.EpicTask;
import task.Status;
import task.SubTask;
import task.Task;
import util.Managers;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Уборка", Status.NEW, "Помыть полы и пропылесосить");
        Task task1 = new Task("Покупка", Status.NEW, "Купить хлеб и молоко");
        EpicTask task2 = new EpicTask("Построить дом", "Кирпичный дом с 4 комнатами");
        SubTask task3 = new SubTask("Залить фундамент", Status.NEW,
                "Глубина 2 метра", 3);
        SubTask task4 = new SubTask("Выложить коробку", Status.NEW,
                "Использовать белый кирпич", 3);
        SubTask task5 = new SubTask("Построить забор", Status.NEW,
                "Зелёный цвет", 3);
        EpicTask task6 = new EpicTask("Сьездить в отпуск", "Посетить одно из чудес света");

        manager.addTask(task);
        manager.addTask(task1);
        manager.addEpicTask(task2);
        manager.addSubTask(task3);
        manager.addSubTask(task4);
        manager.addSubTask(task5);
        manager.addEpicTask(task6);

        manager.getEpicTask(task2);
        System.out.println(manager.getHistory());
        manager.getTask(task);
        System.out.println(manager.getHistory());
        manager.getEpicTask(task2);
        System.out.println(manager.getHistory());
        manager.getEpicTask(task6);
        System.out.println(manager.getHistory());
        manager.getSubTask(task3);
        System.out.println(manager.getHistory());
        manager.getSubTask(task3);
        System.out.println(manager.getHistory());
        manager.getSubTask(task4);
        System.out.println(manager.getHistory());
        manager.getSubTask(task3);
        System.out.println(manager.getHistory());
        manager.removeTask(task);
        System.out.println(manager.getHistory());
        manager.removeEpicTask(task2);
        System.out.println(manager.getHistory());
    }
}
