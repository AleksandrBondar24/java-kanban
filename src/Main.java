import manager.TaskManager;
import task.*;
import util.Managers;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file = new File("taskManagerMain.csv");
        TaskManager manager = Managers.getDefault(file);
        Task task = new Task(-1, Type.TASK, "Уборка", Status.NEW, "Помыть полы и пропылесосить");
        Task task1 = new Task(-1, Type.TASK, "Покупка", Status.NEW, "Купить хлеб и молоко");
        EpicTask task2 = new EpicTask(-1, Type.EPICTASK, "Построить дом", Status.NEW,
                "Кирпичный дом с 4 комнатами");
        SubTask task3 = new SubTask(-1, Type.SUBTASK, "Залить фундамент", Status.NEW,
                "Глубина 2 метра", 3);
        SubTask task4 = new SubTask(-1, Type.SUBTASK, "Выложить коробку", Status.NEW,
                "Использовать белый кирпич", 3);
        SubTask task5 = new SubTask(-1, Type.SUBTASK, "Построить забор", Status.NEW,
                "Зелёный цвет", 3);
        EpicTask task6 = new EpicTask(-1, Type.EPICTASK, "Сьездить в отпуск", Status.NEW,
                "Посетить одно из чудес света");

        manager.addTask(task);
        manager.addTask(task1);
        manager.addEpicTask(task2);
        manager.addSubTask(task3);
        manager.addSubTask(task4);
        manager.addSubTask(task5);
        manager.addEpicTask(task6);

        manager.getEpicTask(task2.getIdTask());
        System.out.println(manager.getHistory());
        manager.getTask(task.getIdTask());
        System.out.println(manager.getHistory());
        manager.getEpicTask(task2.getIdTask());
        System.out.println(manager.getHistory());
        manager.getEpicTask(task6.getIdTask());
        System.out.println(manager.getHistory());
        manager.getSubTask(task3.getIdTask());
        System.out.println(manager.getHistory());
        manager.getSubTask(task3.getIdTask());
        System.out.println(manager.getHistory());
        manager.getSubTask(task4.getIdTask());
        System.out.println(manager.getHistory());
        manager.getSubTask(task3.getIdTask());
        System.out.println(manager.getHistory());
        manager.removeTask(task);
        System.out.println(manager.getHistory());
        manager.removeEpicTask(task2);
        System.out.println(manager.getHistory());
    }
}
