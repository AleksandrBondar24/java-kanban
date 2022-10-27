import manager.TaskManager;
import task.EpicTask;
import task.Status;
import task.SubTask;
import task.Task;
import utils.Managers;

public class Main {

    public static void main(String[] args) {
        // Тесты:
        TaskManager manager = Managers.getDefault();

        Task task = new Task("Уборка", Status.NEW, "Помыть полы и пропылесосить");
        Task task1 = new Task("Покупка", Status.NEW, "Купить хлеб и молоко");
        EpicTask task2 = new EpicTask("Построить дом", "Кирпичный дом с 4 комнатами");
        SubTask task3 = new SubTask("Залить фундамент", Status.NEW,
                "Глубина 2 метра", 3);
        SubTask task4 = new SubTask("Выложить коробку", Status.NEW,
                "Использовать белый кирпич", 3);
        EpicTask task5 = new EpicTask("Сьездить в отпуск", "Посетить одно из чудес света");
        SubTask task6 = new SubTask("Купить путевки", Status.NEW,
                "Успеть купить горящие путевки", 6);
        SubTask task7 = new SubTask("Построить крышу", Status.NEW, "Цвет будет красным", 3);
        SubTask task8 = new SubTask("Установить двери", Status.NEW,
                "Металлические девери", 3);
        Task task9 = new Task("Получить 10 лвл", Status.NEW, "В течении дня");
        Task task10 = new Task("Заточить оружие", Status.NEW, "За 3 попытки");
        Task task11 = new Task("Пройти данж", Status.NEW, "Без смертей");
        manager.addTask(task);
        manager.addTask(task1);
        manager.addEpicTask(task2);
        manager.addSubTask(task3);
        manager.addSubTask(task4);
        manager.addEpicTask(task5);
        manager.addSubTask(task6);
        manager.addSubTask(task7);
        manager.addSubTask(task8);
        manager.addTask(task9);
        manager.addTask(task10);
        manager.addTask(task11);
        System.out.println(manager.getListTasks());
        System.out.println(manager.getListEpicTasks());
        System.out.println(manager.getListSubTasks());
        Task task13 = new Task(1, "Уборка", Status.IN_PROGRESS,
                "Помыть полы и пропылесосить");
        Task task14 = new Task(2, "Покупка", Status.IN_PROGRESS,
                "Купить хлеб и молоко");
        EpicTask task15 = new EpicTask(3, "Построить дом", "Кирпичный дом с 3 комнатами");
        SubTask task16 = new SubTask(4, "Залить фундамент", Status.IN_PROGRESS,
                "Глубина 2 метра", 3);
        SubTask task17 = new SubTask(5, "Выложить коробку", Status.DONE,
                "Использовать белый кирпич", 3);
        EpicTask task18 = new EpicTask(6, "Сьездить в отпуск", "Посетить все чудеса света");
        SubTask task19 = new SubTask(7, "Купить путевки", Status.IN_PROGRESS,
                "Успеть купить горящие путевки", 6);
        manager.updateTasks(task19);
        manager.updateTasks(task14);
        manager.updateEpicTasks(task15);
        manager.updateSubTasks(task16);
        manager.updateSubTasks(task17);
        manager.updateEpicTasks(task18);
        manager.updateSubTasks(task19);
        System.out.println(manager.getListTasks());
        System.out.println(manager.getListEpicTasks());
        System.out.println(manager.getListSubTasks() + "\n");

        // Новые тесты:

        manager.getTask(task13);
        manager.getTask(task14);
        manager.getTask(task9);
        manager.getTask(task10);
        manager.getTask(task11);
        manager.getEpicTask(task15);
        manager.getEpicTask(task18);
        manager.getSubTask(task7);
        manager.getSubTask(task8);
        manager.getSubTask(task16);
        manager.getSubTask(task17);
        System.out.println(manager.getHistory());
        manager.getSubTask(task17);
        System.out.println(manager.getHistory());
    }
}