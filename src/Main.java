import domain.EpicTask;
import domain.SimpleTask;
import domain.SubTask;
import manager.TaskManager;

public class Main {

    public static void main(String[] args) {
        // Тесты:
        TaskManager manager = new TaskManager();
        SimpleTask task = new SimpleTask("Уборка", "NEW", "Помыть полы и пропылесосить");
        SimpleTask task1 = new SimpleTask("Покупка", "NEW", "Купить хлеб и молоко");
        EpicTask task2 = new EpicTask("Построить дом", "Кирпичный дом с 4 комнатами");
        SubTask task3 = new SubTask("Залить фундамент", "NEW",
                "Глубина 2 метра", 3);
        SubTask task4 = new SubTask("Выложить коробку", "NEW",
                "Использовать белый кирпич", 3);
        EpicTask task5 = new EpicTask("Сьездить в отпуск", "Посетить одно из чудес света");
        SubTask task6 = new SubTask("Купить путевки", "NEW",
                "Успеть купить горящие путевки", 6);
        manager.addSimpleTask(task);
        manager.addSimpleTask(task1);
        manager.addEpicTask(task2);
        manager.addSubTask(task3);
        manager.addSubTask(task4);
        manager.addEpicTask(task5);
        manager.addSubTask(task6);
        System.out.println(manager.getListSimpleTasks());
        System.out.println(manager.getListEpicTasks());
        System.out.println(manager.getListSubTasks());
        SimpleTask task7 = new SimpleTask(1, "Уборка", "IN_PROGRESS",
                "Помыть полы и пропылесосить");
        SimpleTask task8 = new SimpleTask(2, "Покупка", "IN_PROGRESS",
                "Купить хлеб и молоко");
        EpicTask task9 = new EpicTask(3, "Построить дом", "Кирпичный дом с 3 комнатами");
        SubTask task10 = new SubTask(4, "Залить фундамент", "IN_PROGRESS",
                "Глубина 2 метра", 3);
        SubTask task11 = new SubTask(5, "Выложить коробку", "DONE",
                "Использовать белый кирпич", 3);
        EpicTask task12 = new EpicTask(6, "Сьездить в отпуск", "Посетить все чудеса света");
        SubTask task13 = new SubTask(7, "Купить путевки", "IN_PROGRESS",
                "Успеть купить горящие путевки", 6);
        manager.updateSimpleTasks(task7);
        manager.updateSimpleTasks(task8);
        manager.updateEpicTasks(task9);
        manager.updateSubTasks(task10);
        manager.updateSubTasks(task11);
        manager.updateEpicTasks(task12);
        manager.updateSubTasks(task13);
        System.out.println(manager.getListSimpleTasks());
        System.out.println(manager.getListEpicTasks());
        System.out.println(manager.getListSubTasks());
        manager.removeSimpleTask(2);
        manager.removeEpicTask(6);
        manager.removeSubTask(4);
        System.out.println(manager.getListSimpleTasks());
        System.out.println(manager.getListEpicTasks());
        System.out.println(manager.getListSubTasks());
    }
}
