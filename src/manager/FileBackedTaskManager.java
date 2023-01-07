package manager;

import task.*;
import util.Managers;
import util.TaskFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static util.CreationOfTime.zoneId;

public class FileBackedTaskManager extends InMemoryTaskManager {
    public FileBackedTaskManager() {
    }

    public static void main(String[] args) {

        File file = new File("taskManager.csv");

        TaskManager manager = Managers.getDefault(file);

        Task task = new Task();
        task.setType(Type.TASK);
        task.setNameTask("TZ");
        task.setStatus(Status.NEW);
        task.setDescriptionTask("закончить ТЗ");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 16, 1, 1), zoneId);
        task.setStartTime(zonedDateTime);
        task.setDuration(56);
        manager.addTask(task);
        manager.getTask(task.getIdTask());

        Task task2 = new Task();
        task2.setType(Type.TASK);
        task2.setNameTask("TZ2");
        task2.setStatus(Status.NEW);
        task2.setDescriptionTask("закончить ТЗ4");
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 11, 16, 1, 1), zoneId);
        task2.setStartTime(zonedDateTime1);
        task2.setDuration(44);
        manager.addTask(task2);
        manager.getTask(task2.getIdTask());

        EpicTask epicTask = new EpicTask();
        epicTask.setType(Type.EPICTASK);
        epicTask.setNameTask("TZ2");
        epicTask.setDescriptionTask("закончить ТЗ5");
        manager.addEpicTask(epicTask);
        manager.getEpicTask(epicTask.getIdTask());

        SubTask subTask = new SubTask();
        subTask.setType(Type.SUBTASK);
        subTask.setNameTask("TZ3");
        subTask.setStatus(Status.DONE);
        subTask.setDescriptionTask("закончить ТЗ27");
        subTask.setEpicTaskId(epicTask.getIdTask());
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(LocalDateTime.of(2022, 10, 16, 1, 1), zoneId);
        subTask.setStartTime(zonedDateTime2);
        subTask.setDuration(40);
        manager.addSubTask(subTask);
        manager.getSubTask(subTask.getIdTask());
        manager.getEpicTask(epicTask.getIdTask());

        SubTask subTask1 = new SubTask();
        subTask1.setType(Type.SUBTASK);
        subTask1.setNameTask("TZ3");
        subTask1.setStatus(Status.NEW);
        subTask1.setDescriptionTask("закончить ТЗ278");
        subTask1.setEpicTaskId(epicTask.getIdTask());
        ZonedDateTime zonedDateTime3 = ZonedDateTime.of(LocalDateTime.of(2022, 9, 15, 1, 1), zoneId);
        subTask1.setStartTime(zonedDateTime3);
        subTask1.setDuration(38);
        manager.addSubTask(subTask1);
        manager.getSubTask(subTask1.getIdTask());
        manager.getEpicTask(epicTask.getIdTask());

        Task task3 = new Task();
        task3.setType(Type.TASK);
        task3.setNameTask("TZ6");
        task3.setStatus(Status.NEW);
        task3.setDescriptionTask("закончить ТЗ6");
        ZonedDateTime zonedDateTime4 = ZonedDateTime.of(LocalDateTime.of(2022, 5, 11, 1, 1), zoneId);
        task3.setStartTime(zonedDateTime4);
        task3.setDuration(49);
        manager.addTask(task3);
        manager.getTask(task3.getIdTask());
        manager.getTask(task.getIdTask());

        System.out.println(manager.getListTasks());
        System.out.println(manager.getListSubTasks());
        System.out.println(manager.getListEpicTasks());
        System.out.println(manager.getHistory());
        System.out.println(" ");
        System.out.println(manager.getPrioritizedTasks());
        System.out.println(" ");


        TaskManager manager1 = loadFromFile(file);

        System.out.println(manager1.getListTasks());
        System.out.println(manager1.getListSubTasks());
        System.out.println(manager1.getListEpicTasks());
        System.out.println(manager1.getHistory());
        System.out.println(manager.getPrioritizedTasks());

    }

    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }


    public static FileBackedTaskManager loadFromFile(File file) {

        try {

            FileBackedTaskManager fileManager = new FileBackedTaskManager(file);
            String csvTasFile = Files.readString(Path.of(String.valueOf(file)));
            String[] strings = csvTasFile.split("\r?\n");
            int id = 0;
            System.out.println(csvTasFile);
            System.out.println(" ");
            List<Integer> idHistory = new ArrayList<>();
            for (int i = 1; i < strings.length; i++) {
                if (strings[i].isBlank()) {

                    idHistory = TaskFormatter.historyFromString(strings[++i]);
                    break;
                }
                Task task = TaskFormatter.fromString(strings[i]);
                assert task != null;
                Type type = task.getType();
                switch (type) {
                    case TASK -> {
                        fileManager.tasks.put(task.getIdTask(), task);
                        fileManager.prioritizedTasks.put(task.getStartTime(), task);
                    }
                    case EPICTASK -> fileManager.epicTasks.put(task.getIdTask(), (EpicTask) task);
                    case SUBTASK -> {
                        fileManager.subTasks.put(task.getIdTask(), (SubTask) task);
                        fileManager.prioritizedTasks.put(task.getStartTime(), task);
                    }
                }

                if (task.getIdTask() > id) {
                    id = task.getIdTask();
                }
            }
            for (Integer idList : idHistory) {

                if (fileManager.tasks.containsKey(idList)) {
                    fileManager.history.add(fileManager.tasks.get(idList));
                } else if (fileManager.epicTasks.containsKey(idList)) {
                    fileManager.history.add(fileManager.epicTasks.get(idList));
                } else if (fileManager.subTasks.containsKey(idList)) {
                    fileManager.history.add(fileManager.subTasks.get(idList));
                }
            }

            for (SubTask taskas : fileManager.subTasks.values()) {
                int idThisEpic = taskas.getEpicTaskId();
                EpicTask taskasa = fileManager.epicTasks.get(idThisEpic);
                List<Integer> list = taskasa.getListSubTaskIds();
                list.add(taskas.getIdTask());
            }
            fileManager.setId(++id);

            return fileManager;
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла", e);
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(TaskFormatter.getHeader());
            writer.newLine();

            for (Task task : tasks.values()) {
                String csvToString = TaskFormatter.toString(task);
                writer.write(csvToString);
                writer.newLine();
            }
            for (EpicTask epic : epicTasks.values()) {
                String csvToString = TaskFormatter.toString(epic);
                writer.write(csvToString);
                writer.newLine();
            }
            for (SubTask subTask : subTasks.values()) {
                String csvToString = TaskFormatter.toString(subTask);
                writer.write(csvToString);
                writer.newLine();
            }
            writer.newLine();

            String csvToString = TaskFormatter.historyToString(history);
            writer.write(csvToString);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи файла", e);
        }
    }

    @Override
    public Task getTask(int id) {
        Task task1 = super.getTask(id);
        save();
        return task1;
    }

    @Override
    public EpicTask getEpicTask(int id) {
        EpicTask task1 = super.getEpicTask(id);
        save();
        return task1;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask task1 = super.getSubTask(id);
        save();
        return task1;
    }

    @Override
    public String removeTask(int id) {
        String message = super.removeTask(id);
        if (message.equals("Задача успешно удалена!")) {
            save();
        }
        return message;
    }

    @Override
    public String removeEpicTask(int id) {
        String message = super.removeEpicTask(id);
        if (message.equals("Задача успешно удалена!")) {
            save();
        }
        return message;
    }

    @Override
    public String removeSubTask(int id) {
        String message = super.removeSubTask(id);
        if (message.equals("Задача успешно удалена!")) {
            save();
        }
        return message;
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpicTasks() {
        super.clearEpicTasks();
        save();
    }

    @Override
    public void clearSubTasks() {
        super.clearSubTasks();
        save();
    }

    @Override
    public String addTask(Task task) {
        String message = super.addTask(task);
        if (message.equals("Задача успешно добавлена!")) {
            save();
        }
        return message;
    }

    @Override
    public String addEpicTask(EpicTask task) {
        String message = super.addEpicTask(task);
        if (message.equals("Эпикзадача успешно добавлена!")) {
            save();
        }
        return message;
    }

    @Override
    public String addSubTask(SubTask task) {
        String message = super.addSubTask(task);
        if (message.equals("Подзадача успешно добавлена!")) {
            save();
        }
        return message;
    }

    @Override
    public String updateEpicTasks(EpicTask task) {
        String message = super.updateEpicTasks(task);
        if (message.equals("Задача успешно обновлена!")) {
            save();
        }
        return message;
    }

    @Override
    public String updateTasks(Task task) {
        String message = super.updateTasks(task);
        if (message.equals("Задача успешно обновлена!")) {
            save();
        }
        return message;
    }

    @Override
    public String updateSubTasks(SubTask task) {
        String message = super.updateSubTasks(task);
        if (message.equals("Задача успешно обновлена!")) {
            save();
        }
        return message;
    }
}
