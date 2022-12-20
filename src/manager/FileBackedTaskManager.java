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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    public static void main(String[] args) {

        File file = new File("taskManager.csv");

        TaskManager manager = Managers.getDefault(file);

        Task task = new Task();
        task.setType(Type.TASK);
        task.setNameTask("TZ");
        task.setStatus(Status.NEW);
        task.setDescriptionTask("закончить ТЗ");
        task.setStartTime(2022,12,16,01,01);
        Duration duration = Duration.ofHours(56);
        task.setDuration(duration);
        manager.addTask(task);
        manager.getTask(task.getIdTask());

        Task task2 = new Task();
        task2.setType(Type.TASK);
        task2.setNameTask("TZ2");
        task2.setStatus(Status.NEW);
        task2.setDescriptionTask("закончить ТЗ4");
        task2.setStartTime(2022,11,16,01,01);
        Duration duration1 = Duration.ofHours(44);
        task2.setDuration(duration1);
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
        subTask.setStartTime(2022,10,16,01,01);
        Duration duration2 = Duration.ofHours(40);
        subTask.setDuration(duration2);
        manager.addSubTask(subTask);
        manager.getSubTask(subTask.getIdTask());
        manager.getEpicTask(epicTask.getIdTask());

        SubTask subTask1 = new SubTask();
        subTask1.setType(Type.SUBTASK);
        subTask1.setNameTask("TZ3");
        subTask1.setStatus(Status.NEW);
        subTask1.setDescriptionTask("закончить ТЗ278");
        subTask1.setEpicTaskId(epicTask.getIdTask());
        subTask1.setStartTime(2022,9,15,01,01);
        Duration duration3 = Duration.ofHours(38);
        subTask1.setDuration(duration3);
        manager.addSubTask(subTask1);
        manager.getSubTask(subTask1.getIdTask());
        manager.getEpicTask(epicTask.getIdTask());

        Task task3 = new Task();
        task3.setType(Type.TASK);
        task3.setNameTask("TZ6");
        task3.setStatus(Status.NEW);
        task3.setDescriptionTask("закончить ТЗ6");
        task3.setStartTime(2022,5,11,01,01);
        Duration duration4 = Duration.ofHours(49);
        task3.setDuration(duration4);
        manager.addTask(task3);
        manager.getTask(task3.getIdTask());
        manager.getTask(task.getIdTask());

        System.out.println(manager.getListTasks());
        System.out.println(manager.getListSubTasks());
        System.out.println(manager.getListEpicTasks());
        System.out.println(manager.getHistory());
        System.out.println(" ");
        System.out.println(manager.getPrioritizedTasks());

        TaskManager manager1 = loadFromFile(file);

        System.out.println(manager1.getListTasks());
        System.out.println(manager1.getListSubTasks());
        System.out.println(manager1.getListEpicTasks());
        System.out.println(manager1.getHistory());
        System.out.println(manager.getPrioritizedTasks());

    }

    private final File file;

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
                    case TASK:
                        fileManager.tasks.put(task.getIdTask(), task);
                        fileManager.prioritizedTasks.add(task);
                        break;
                    case EPICTASK:
                        fileManager.epicTask.put(task.getIdTask(), (EpicTask) task);
                        break;
                    case SUBTASK:
                        fileManager.subTask.put(task.getIdTask(), (SubTask) task);
                        fileManager.prioritizedTasks.add(task);
                        break;
                }

                if (task.getIdTask() > id) {
                    id = task.getIdTask();
                }
            }
            for (Integer idList : idHistory) {

                if (fileManager.tasks.containsKey(idList)) {
                    fileManager.history.add(fileManager.tasks.get(idList));
                } else if (fileManager.epicTask.containsKey(idList)) {
                    fileManager.history.add(fileManager.epicTask.get(idList));
                } else if (fileManager.subTask.containsKey(idList)) {
                    fileManager.history.add(fileManager.subTask.get(idList));
                }
            }

            for (SubTask taskas : fileManager.subTask.values()) {
                int idThisEpic = taskas.getEpicTaskId();
                EpicTask taskasa = fileManager.epicTask.get(idThisEpic);
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
            for (EpicTask epic : epicTask.values()) {
                String csvToString = TaskFormatter.toString(epic);
                writer.write(csvToString);
                writer.newLine();
            }
            for (SubTask subTask : subTask.values()) {
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
    public String removeTask(Task task) {
        super.removeTask(task);
        save();
        return "Задача успешно удалена!";
    }

    @Override
    public String removeEpicTask(EpicTask task) {
        super.removeEpicTask(task);
        save();
        return "Задача успешно удалена!";
    }

    @Override
    public String removeSubTask(SubTask task) {
        super.removeSubTask(task);
        save();
        return "Задача успешно удалена!";
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
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpicTask(EpicTask task) {
        super.addEpicTask(task);
        save();
    }

    @Override
    public void addSubTask(SubTask task) {
        super.addSubTask(task);
        save();
    }

    @Override
    public void updateEpicTasks(EpicTask task) {
        super.updateEpicTasks(task);
        save();
    }

    @Override
    public void updateTasks(Task task) {
        super.updateTasks(task);
        save();
    }

    @Override
    public void updateSubTasks(SubTask task) {
        super.updateSubTasks(task);
        save();
    }
}
