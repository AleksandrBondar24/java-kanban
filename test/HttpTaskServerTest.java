import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import http.HttpTaskServer;
import http.KVServer;
import org.junit.jupiter.api.*;
import task.EpicTask;
import task.Status;
import task.SubTask;
import task.Task;
import util.Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static task.Type.*;
import static util.CreationOfTime.*;

public class HttpTaskServerTest {
    private static KVServer kvServer;
    private static HttpTaskServer server;
    private static Gson gson;
    private EpicTask epicTask;
    private EpicTask epicTask1;
    private Task task;
    private Task task1;
    private SubTask subTask;
    private SubTask subTask1;

    @BeforeEach
    public void startServer() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        server = new HttpTaskServer();
        server.start();
        gson = Managers.getGson();
    }

    @AfterEach
    public void stopServer() {
        kvServer.stop();
        server.stop();
    }

    @BeforeEach
    public void createTask() {
        Duration duration = Duration.ofHours(44);
        Duration duration1 = Duration.ofHours(34);
        Duration duration2 = Duration.ofHours(44);
        Duration duration3 = Duration.ofHours(34);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 22, 12, 34), zoneId);
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.of(2022, 10, 12, 10, 40), zoneId);
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(LocalDateTime.of(2022, 9, 10, 12, 34), zoneId);
        ZonedDateTime zonedDateTime3 = ZonedDateTime.of(LocalDateTime.of(2022, 8, 11, 10, 40), zoneId);
        task = new Task(-1, TASK, "Tz1", Status.NEW, "okk", zonedDateTime, duration);
        task1 = new Task(1, TASK, "Tz2", Status.IN_PROGRESS, "okk", zonedDateTime1, duration1);
        epicTask = new EpicTask(-1, EPICTASK, "1Epic", Status.NEW, "okk", defaultStartTime, defaultDuration);
        epicTask1 = new EpicTask(1, EPICTASK, "2Epic", Status.NEW, "okk", defaultStartTime, defaultDuration);
        subTask = new SubTask(-1, SUBTASK, "ep", Status.IN_PROGRESS, "okk", zonedDateTime2, duration2, 1);
        subTask1 = new SubTask(2, SUBTASK, "ep", Status.NEW, "okk", zonedDateTime3, duration3, 1);
    }

    @Test
    public void shouldHandleAllTaskRequests() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task1)))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(task1, gson.fromJson(response1.body(), Task.class));

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        JsonArray arrayTasks = JsonParser.parseString(response2.body()).getAsJsonArray();
        Assertions.assertEquals(1, arrayTasks.size());

        URI url1 = URI.create("http://localhost:8080/tasks/task?id=1");
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(task1, gson.fromJson(response3.body(), Task.class));

        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(url1)
                .DELETE()
                .build();
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response4.statusCode());

        HttpRequest request5 = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response5.statusCode());
    }

    @Test
    public void shouldHandleAllEpicRequests() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTask)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTask1)))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(epicTask1, gson.fromJson(response1.body(), EpicTask.class));

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        JsonArray arrayEpics = JsonParser.parseString(response2.body()).getAsJsonArray();
        Assertions.assertEquals(1, arrayEpics.size());

        URI url1 = URI.create("http://localhost:8080/tasks/epic?id=1");
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(epicTask1, gson.fromJson(response3.body(), EpicTask.class));

        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(url1)
                .DELETE()
                .build();
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response4.statusCode());


        HttpRequest request5 = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response5.statusCode());
    }

    @Test
    public void shouldHandleAllSubtaskRequests() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI urlEpic = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest requestEpic = HttpRequest.newBuilder()
                .uri(urlEpic)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTask)))
                .build();
        client.send(requestEpic, HttpResponse.BodyHandlers.ofString());

        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(subTask)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subTask1)))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(subTask1, gson.fromJson(response1.body(), SubTask.class));

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        JsonArray arraySubtask = JsonParser.parseString(response2.body()).getAsJsonArray();
        Assertions.assertEquals(1, arraySubtask.size());

        URI url1 = URI.create("http://localhost:8080/tasks/subtask?id=2");
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(subTask1, gson.fromJson(response3.body(), SubTask.class));

        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(url1)
                .DELETE()
                .build();
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response4.statusCode());

        HttpRequest request5 = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response5.statusCode());
    }

    @Test
    public void shouldHandleHistoryRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        URI url1 = URI.create("http://localhost:8080/tasks/task?id=1");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .GET()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        JsonElement jsonElement = JsonParser.parseString(response2.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        Assertions.assertEquals(1, jsonArray.size());
    }

    @Test
    public void shouldHandlePrioritizedTasksRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI urlEpic = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest requestEpic = HttpRequest.newBuilder()
                .uri(urlEpic)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTask)))
                .build();
        client.send(requestEpic, HttpResponse.BodyHandlers.ofString());

        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(subTask)))
                .build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/tasks/");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .GET()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        JsonElement jsonElement = JsonParser.parseString(response2.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        Assertions.assertEquals(2, jsonArray.size());
    }

    @Test
    public void shouldHandleEpicSubTasksRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest requestEpic = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTask)))
                .build();
        client.send(requestEpic, HttpResponse.BodyHandlers.ofString());

        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(subTask)))
                .build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/tasks/subtask/epic?id=1");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .GET()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        JsonElement jsonElement = JsonParser.parseString(response2.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        Assertions.assertEquals(1, jsonArray.size());
    }
}
