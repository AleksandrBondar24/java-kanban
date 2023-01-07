package http;

import com.google.gson.Gson;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.RuntimeEnumerationException;
import manager.TaskManager;
import task.*;
import util.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class HttpTaskServer {
    private static final int PORT1 = 8080;
    private final Gson gson;
    private final HttpServer server;
    private final TaskManager manager;

    public HttpTaskServer() throws IOException, InterruptedException {
        this.server = HttpServer.create(new InetSocketAddress("localhost", PORT1), 0);
        this.gson = Managers.getGson();
        this.manager = Managers.getDefault("http://localhost:8078");
        server.createContext("/tasks", this::handler);
    }

    public void handler(HttpExchange exchange) throws IOException {
        System.out.println("\n/handler");
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath());
        switch (endpoint) {
            case GET_PRIORITIZEDTASKS -> handleGetPrioritizedTasks(exchange);

            case GET_HISTORY -> handleGetHistory(exchange);

            case GET_EPICSUBTASKS -> handleGetEpicSubtasks(exchange);

            case TASKS -> handleTasks(exchange);

            case EPICS -> handleEpics(exchange);

            case SUBTASKS -> handleSubtasks(exchange);

            default -> {
                System.out.println("Такого запроса не существует: " + exchange.getRequestURI());
                exchange.sendResponseHeaders(403, 0);
            }

        }
    }

    private void handleGetPrioritizedTasks(HttpExchange e) throws IOException {
        System.out.println("\n/handleGetPrioritizedTasks");
        if (!e.getRequestMethod().equals("GET")) {
            final String response = "Ожидался GET-запрос.Получен: " + e.getRequestMethod();
            writeText(e, response, 405);
            System.out.println("Ожидался GET-запрос.Получен: " + e.getRequestMethod());
            return;
        }
        final String response = gson.toJson(manager.getPrioritizedTasks());
        System.out.println("Список задач по приоритету получен!");
        writeText(e, response, 200);
    }

    private void handleGetHistory(HttpExchange e) throws IOException {
        System.out.println("\n/handleGetHistory");
        if (!e.getRequestMethod().equals("GET")) {
            final String response = "Ожидался GET-запрос.Получен: " + e.getRequestMethod();
            writeText(e, response, 405);
            System.out.println("Ожидался GET-запрос.Получен: " + e.getRequestMethod());
            return;
        }
        final String response = gson.toJson(manager.getHistory());
        System.out.println("Список задач в истории получен!");
        writeText(e, response, 200);
    }

    private void handleGetEpicSubtasks(HttpExchange e) throws IOException {
        System.out.println("\n/handleGetEpicSubtasks");
        if (!e.getRequestMethod().equals("GET")) {
            final String response = "Ожидался GET-запрос.Получен: " + e.getRequestMethod();
            writeText(e, response, 405);
            System.out.println("Ожидался GET-запрос.Получен: " + e.getRequestMethod());
            return;
        }
        Optional<Integer> taskId = getTaskId(e);
        if (taskId.isEmpty()) {
            System.out.println("Некорректный идентификатор задачи");
            final String response = "Некорректный идентификатор задачи";
            writeText(e, response, 400);
            return;
        }
        EpicTask epicTask = manager.getEpicTask(taskId.get());
        if (epicTask != null) {
            final String response = gson.toJson(epicTask.getListSubTaskIds());
            System.out.println("Список ID подзадач запрашиваемого эпика получен!");
            writeText(e, response, 200);
        } else {
            System.out.println("Эпик с таким id: " + taskId.get() + " не найден!");
            final String response = "Эпик с таким id: " + taskId.get() + " не найден!";
            writeText(e, response, 400);
        }
    }

    private void handleTasks(HttpExchange e) throws IOException {
        System.out.println("\n/handleTasks");
        String method = e.getRequestMethod();
        String param = e.getRequestURI().getQuery();
        switch (method) {
            case "GET" -> handleGetTasks(e, param);
            case "DELETE" -> handleDeleteTasks(e, param);
            case "POST" -> handlePostTasks(e);
            case "PUT" -> handlePutTasks(e);
            default -> {
                System.out.println("Ожидался метод запроса GET,POST,PUT или DELETE.Получен: " + method);
                final String response = "Ожидался метод запроса GET,POST,PUT или DELETE.Получен: " + method;
                writeText(e, response, 403);
            }
        }
    }

    private void handleGetTasks(HttpExchange e, String param) throws IOException {
        System.out.println("\n/handleGetTasks");
        if (param == null) {
            String response = gson.toJson(manager.getListTasks());
            System.out.println("Список всех задач получен!");
            writeText(e, response, 200);
        }
        Optional<Integer> taskId = getTaskId(e);
        if (taskId.isEmpty()) {
            System.out.println("Некорректный идентификатор задачи");
            String response = "Некорректный идентификатор задачи";
            writeText(e, response, 400);
            return;
        }
        int id = taskId.get();
        Task task = manager.getTask(id);
        if (task != null) {
            String taskGson = gson.toJson(task);
            System.out.println("Задача с идентификатором " + id + "получена!");
            writeText(e, taskGson, 200);
            return;
        }
        System.out.println("Задача с идентификатором " + id + " не найдена");
        String response = "Задача с идентификатором " + id + " не найдена";
        writeText(e, response, 404);
    }

    private void handleDeleteTasks(HttpExchange e, String param) throws IOException {
        System.out.println("\n/handleDeleteTasks");
        if (param == null) {
            manager.clearTasks();
            System.out.println("Список всех задач удален!");
            String response = gson.toJson(manager.getListTasks());
            writeText(e, response, 200);
            return;
        }
        Optional<Integer> taskId = getTaskId(e);
        if (taskId.isEmpty()) {
            System.out.println("Некорректный идентификатор задачи");
            String response = "Некорректный идентификатор задачи";
            writeText(e, response, 400);
            return;
        }
        int id = taskId.get();
        if (manager.getTasks().containsKey(id)) {
            manager.removeTask(id);
            System.out.println("Задача с идентификатором " + id + " успешно удалена!");
            String response = "Задача с идентификатором " + id + " успешно удалена!";
            writeText(e, response, 200);
            return;
        }
        System.out.println("Задача с идентификатором " + id + " не найдена");
        String response = "Задача с идентификатором " + id + " не найдена";
        writeText(e, response, 404);
    }

    private void handlePutTasks(HttpExchange e) throws IOException {
        System.out.println("\n/handlePutTasks");
        String body = readText(e);
        if (body.isEmpty()) {
            System.out.println("Тело запроса пустое!");
            String response = "Тело запроса пустое!";
            writeText(e, response, 400);
            return;
        }
        Task task = gson.fromJson(body, Task.class);
        try {
            manager.addTask(task);
        } catch (RuntimeEnumerationException exception) {
            String response = exception.getMessage();
            writeText(e, response, 404);
            return;
        }
        System.out.println("Задача успешно добавлена!");
        final String response = gson.toJson(task);
        writeText(e, response, 200);
    }

    private void handlePostTasks(HttpExchange e) throws IOException {
        System.out.println("\n/handlePostTasks");
        String body = readText(e);
        if (body.isEmpty()) {
            System.out.println("Тело запроса пустое!");
            String response = "Тело запроса пустое!";
            writeText(e, response, 400);
            return;
        }
        Task task = gson.fromJson(body, Task.class);
        if (manager.getTasks().containsKey(task.getIdTask())) {
            manager.updateTasks(task);
            System.out.println("Задача с идентификатором " + task.getIdTask() + "успешно обновлена!");
            final String response = gson.toJson(task);
            writeText(e, response, 200);
        }
        String response = "Задача с идентификатором: " + task.getIdTask() + "не найдена!";
        writeText(e, response, 400);
    }

    private void handleEpics(HttpExchange e) throws IOException {
        System.out.println("\n/handleEpics");
        String method = e.getRequestMethod();
        String param = e.getRequestURI().getQuery();
        switch (method) {
            case "GET" -> handleGetEpics(e, param);
            case "DELETE" -> handleDeleteEpics(e, param);
            case "POST" -> handlePostEpics(e);
            case "PUT" -> handlePutEpics(e);
            default -> {
                System.out.println("Ожидался метод запроса GET,POST,PUT или DELETE.Получен: " + method);
                final String response = "Ожидался метод запроса GET,POST,PUT или DELETE.Получен: " + method;
                writeText(e, response, 403);
            }
        }
    }

    private void handleGetEpics(HttpExchange e, String param) throws IOException {
        System.out.println("\n/handleGetEpics");
        if (param == null) {
            String listTasks = gson.toJson(manager.getListEpicTasks());
            System.out.println("Список всех задач получен!");
            writeText(e, listTasks, 200);
        }
        Optional<Integer> taskId = getTaskId(e);
        if (taskId.isEmpty()) {
            System.out.println("Некорректный идентификатор задачи");
            String response = "Некорректный идентификатор задачи";
            writeText(e, response, 400);
            return;
        }
        int id = taskId.get();
        EpicTask task = manager.getEpicTask(id);
        if (task != null) {
            String taskGson = gson.toJson(task);
            System.out.println("Задача с идентификатором " + id + "получена!");
            writeText(e, taskGson, 200);
            return;
        }
        System.out.println("Задача с идентификатором " + id + " не найдена");
        String response = "Задача с идентификатором " + id + " не найдена";
        writeText(e, response, 404);
    }

    private void handleDeleteEpics(HttpExchange e, String param) throws IOException {
        System.out.println("\n/handleDeleteEpics");
        if (param == null) {
            manager.clearEpicTasks();
            System.out.println("Список всех задач удален!");
            String response = gson.toJson(manager.getListEpicTasks());
            writeText(e, response, 200);
            return;
        }
        Optional<Integer> taskId = getTaskId(e);
        if (taskId.isEmpty()) {
            System.out.println("Некорректный идентификатор задачи");
            String response = "Некорректный идентификатор задачи";
            writeText(e, response, 400);
            return;
        }
        int id = taskId.get();
        if (manager.getEpicTasks().containsKey(id)) {
            manager.removeEpicTask(id);
            System.out.println("Задача с идентификатором " + id + " успешно удалена!");
            String response = "Задача с идентификатором " + id + " успешно удалена!";
            writeText(e, response, 200);
            return;
        }
        System.out.println("Задача с идентификатором " + id + " не найдена");
        String response = "Задача с идентификатором " + id + " не найдена";
        writeText(e, response, 404);
    }

    private void handlePutEpics(HttpExchange e) throws IOException {
        System.out.println("\n/handlePutEpics");
        String body = readText(e);
        if (body.isEmpty()) {
            System.out.println("Тело запроса пустое!");
            String response = "Тело запроса пустое!";
            writeText(e, response, 400);
            return;
        }
        EpicTask task = gson.fromJson(body, EpicTask.class);
        try {
            manager.addEpicTask(task);
        } catch (RuntimeEnumerationException exception) {
            String response = exception.getMessage();
            writeText(e, response, 404);
            return;
        }
        System.out.println("Задача успешно добавлена!");
        final String response = gson.toJson(task);
        writeText(e, response, 200);
    }

    private void handlePostEpics(HttpExchange e) throws IOException {
        System.out.println("\n/handlePostEpics");
        String body = readText(e);
        if (body.isEmpty()) {
            System.out.println("Тело запроса пустое!");
            String response = "Тело запроса пустое!";
            writeText(e, response, 400);
            return;
        }
        EpicTask task = gson.fromJson(body, EpicTask.class);
        if (manager.getEpicTasks().containsKey(task.getIdTask())) {
            manager.updateEpicTasks(task);
            System.out.println("Задача с идентификатором " + task.getIdTask() + "успешно обновлена!");
            final String response = gson.toJson(task);
            writeText(e, response, 200);
        }
        String response = "Задача с идентификатором: " + task.getIdTask() + "не найдена!";
        writeText(e, response, 400);
    }

    private void handleSubtasks(HttpExchange e) throws IOException {
        System.out.println("\n/handleSubtasks");
        String method = e.getRequestMethod();
        String param = e.getRequestURI().getQuery();
        switch (method) {
            case "GET" -> handleGetSubTasks(e, param);
            case "DELETE" -> handleDeleteSubTasks(e, param);
            case "POST" -> handlePostSubtasks(e);
            case "PUT" -> handlePutSubtasks(e);
            default -> {
                System.out.println("Ожидался метод запроса GET,POST,PUT или DELETE.Получен: " + method);
                final String response = "Ожидался метод запроса GET,POST,PUT или DELETE.Получен: " + method;
                writeText(e, response, 403);
            }
        }
    }

    private void handleGetSubTasks(HttpExchange e, String param) throws IOException {
        System.out.println("\n/handleGetSubTasks");
        if (param == null) {
            String listTasks = gson.toJson(manager.getListSubTasks());
            System.out.println("Список всех задач получен!");
            writeText(e, listTasks, 200);
        }
        Optional<Integer> taskId = getTaskId(e);
        if (taskId.isEmpty()) {
            System.out.println("Некорректный идентификатор задачи");
            String response = "Некорректный идентификатор задачи";
            writeText(e, response, 400);
            return;
        }
        int id = taskId.get();
        SubTask task = manager.getSubTask(id);
        if (task != null) {
            String taskGson = gson.toJson(task);
            System.out.println("Задача с идентификатором " + id + "получена!");
            writeText(e, taskGson, 200);
            return;
        }
        System.out.println("Задача с идентификатором " + id + " не найдена");
        String response = "Задача с идентификатором " + id + " не найдена";
        writeText(e, response, 404);
    }

    private void handleDeleteSubTasks(HttpExchange e, String param) throws IOException {
        System.out.println("\n/handleDeleteSubTasks");
        if (param == null) {
            manager.clearSubTasks();
            System.out.println("Список всех задач удален!");
            String response = gson.toJson(manager.getListSubTasks());
            writeText(e, response, 200);
            return;
        }
        Optional<Integer> taskId = getTaskId(e);
        if (taskId.isEmpty()) {
            System.out.println("Некорректный идентификатор задачи");
            String response = "Некорректный идентификатор задачи";
            writeText(e, response, 400);
            return;
        }
        int id = taskId.get();
        if (manager.getSubTasks().containsKey(id)) {
            manager.removeSubTask(id);
            System.out.println("Задача с идентификатором " + id + " успешно удалена!");
            String response = "Задача с идентификатором " + id + " успешно удалена!";
            writeText(e, response, 200);
            return;
        }
        System.out.println("Задача с идентификатором " + id + " не найдена");
        String response = "Задача с идентификатором " + id + " не найдена";
        writeText(e, response, 404);
    }

    private void handlePutSubtasks(HttpExchange e) throws IOException {
        System.out.println("\n/handlePutSubtasks");
        String body = readText(e);
        if (body.isEmpty()) {
            System.out.println("Тело запроса пустое!");
            String response = "Тело запроса пустое!";
            writeText(e, response, 400);
            return;
        }
        SubTask task = gson.fromJson(body, SubTask.class);
        try {
            manager.addSubTask(task);
        } catch (RuntimeEnumerationException exception) {
            String response = exception.getMessage();
            writeText(e, response, 404);
            return;
        }
        System.out.println("Задача успешно добавлена!");
        final String response = gson.toJson(task);
        writeText(e, response, 200);
    }

    private void handlePostSubtasks(HttpExchange e) throws IOException {
        System.out.println("\n/handlePostSubtasks");
        String body = readText(e);
        if (body.isEmpty()) {
            System.out.println("Тело запроса пустое!");
            String response = "Тело запроса пустое!";
            writeText(e, response, 400);
            return;
        }
        SubTask task = gson.fromJson(body, SubTask.class);
        if (manager.getSubTasks().containsKey(task.getIdTask())) {
            manager.updateSubTasks(task);
            System.out.println("Задача с идентификатором " + task.getIdTask() + "успешно обновлена!");
            final String response = gson.toJson(task);
            writeText(e, response, 200);
        }
        String response = "Задача с идентификатором: " + task.getIdTask() + "не найдена!";
        writeText(e, response, 400);
    }

    private Optional<Integer> getTaskId(HttpExchange exchange) {
        try {
            return Optional.of(parseInt(exchange.getRequestURI().getQuery().substring("id=".length())));
        } catch (NumberFormatException | NullPointerException e) {
            return Optional.empty();
        }
    }

    private void writeText(HttpExchange exchange, String responseString, int code) throws IOException {
        try (exchange) {
            byte[] response = responseString.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
            exchange.sendResponseHeaders(code, response.length);
            exchange.getResponseBody().write(response);
        }
    }

    private String readText(HttpExchange exchange) throws IOException {
        return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    private Endpoint getEndpoint(String requestPath) {
        String pathParts = requestPath.substring("/tasks/".length());

        if (pathParts.isEmpty()) {
            return Endpoint.GET_PRIORITIZEDTASKS;
        }
        if (pathParts.equals("subtask/epic")) {
            return Endpoint.GET_EPICSUBTASKS;
        }
        if (pathParts.equals("history")) {
            return Endpoint.GET_HISTORY;
        }
        if (pathParts.equals("task")) {
            return Endpoint.TASKS;
        }
        if (pathParts.equals("epic")) {
            return Endpoint.EPICS;
        }
        if (pathParts.equals("subtask")) {
            return Endpoint.SUBTASKS;
        }
        return Endpoint.UNKNOWN;
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT1);
        System.out.println("http://localhost:" + PORT1 + "/tasks");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT1);
    }

    enum Endpoint {
        UNKNOWN, GET_PRIORITIZEDTASKS, TASKS, EPICS, SUBTASKS, GET_EPICSUBTASKS, GET_HISTORY
    }
}

