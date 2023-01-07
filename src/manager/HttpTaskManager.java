package manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import http.KVTaskClient;
import task.EpicTask;
import task.SubTask;
import task.Task;
import util.Managers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTaskManager {
    private final KVTaskClient kvTaskClient;
    private final Gson gson;

    public HttpTaskManager(String url) throws IOException, InterruptedException {
        this.gson = Managers.getGson();
        this.kvTaskClient = new KVTaskClient(url);
    }

    public void load() {
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        String json = kvTaskClient.load("tasks");
        if (json != null) {
            List<Task> list = gson.fromJson(json, taskType);
            list.forEach(task -> tasks.put(task.getIdTask(), task));
        }
        Type taskType1 = new TypeToken<ArrayList<EpicTask>>() {
        }.getType();
        String json1 = kvTaskClient.load("epics");
        if (json1 != null) {
            List<EpicTask> list1 = gson.fromJson(json1, taskType1);
            list1.forEach(task -> epicTasks.put(task.getIdTask(), task));
        }
        Type taskType2 = new TypeToken<ArrayList<SubTask>>() {
        }.getType();
        String json2 = kvTaskClient.load("subtasks");
        if (json2 != null) {
            List<SubTask> list2 = gson.fromJson(json2, taskType2);
            list2.forEach(task -> subTasks.put(task.getIdTask(), task));
        }
        String json3 = kvTaskClient.load("id");
        if (json3 != null) {
            setId(JsonParser.parseString(json3).getAsInt());
        }
        String json4 = kvTaskClient.load("history");
        if (json4 != null) {
            JsonElement jsonHistory = JsonParser.parseString(json4);
            JsonArray jsonHistoryArray = jsonHistory.getAsJsonArray();
            for (JsonElement js : jsonHistoryArray) {
                int id = js.getAsInt();
                if (tasks.containsKey(id)) {
                    history.add(tasks.get(id));
                } else if (epicTasks.containsKey(id)) {
                    history.add(epicTasks.get(id));
                } else if (subTasks.containsKey(id)) {
                    history.add(subTasks.get(id));
                }
            }
        }

        String json5 = kvTaskClient.load("prioritized");
        if (json5 != null) {
            JsonElement jsonPrioritized = JsonParser.parseString(json5);
            JsonArray jsonPrioritizedArray = jsonPrioritized.getAsJsonArray();
            for (JsonElement js : jsonPrioritizedArray) {
                int id = js.getAsInt();
                if (tasks.containsKey(id)) {
                    Task task = tasks.get(id);
                    prioritizedTasks.put(task.getStartTime(), task);
                }
                if (subTasks.containsKey(id)) {
                    SubTask task1 = subTasks.get(id);
                    prioritizedTasks.put(task1.getStartTime(), task1);
                }
            }
        }
    }

    @Override
    public void save() {
        String tasksJson = gson.toJson(tasks.values());
        kvTaskClient.put("tasks", tasksJson);
        String epicsJson = gson.toJson(epicTasks.values());
        kvTaskClient.put("epics", epicsJson);
        String subTasksJson = gson.toJson(subTasks.values());
        kvTaskClient.put("subtasks", subTasksJson);
        String historyJson = gson.toJson(getHistory().stream().map(Task::getIdTask).collect(Collectors.toList()));
        kvTaskClient.put("history", historyJson);
        String prioritizedJson = gson.toJson(getPrioritizedTasks().stream().map(Task::getIdTask).collect(Collectors.toList()));
        kvTaskClient.put("prioritized", prioritizedJson);
        String idJson = gson.toJson(getId());
        kvTaskClient.put("id", idJson);
    }
}
