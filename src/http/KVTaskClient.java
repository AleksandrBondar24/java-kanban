package http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static http.KVServer.PORT;

public class KVTaskClient {
    private final String url;
    public final String API_TOKEN;

    public KVTaskClient(String url) throws IOException, InterruptedException {
        this.url = "http://localhost:" + PORT;
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(this.url + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        this.API_TOKEN = httpResponse.body();
    }

    public void put(String key, String json) {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/save/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status >= 200 && status <= 299) {
                System.out.println("Сервер успешно обработал запрос. Код состояния: " + status);
                return;
            }
            if (status >= 400 && status <= 499) {
                System.out.println("Сервер сообщил о проблеме с запросом. Код состояния: " + status);
                return;
            }
            if (status >= 500 && status <= 599) {
                System.out.println("Сервер сообщил о внутренней проблеме и невозможности обработать запрос." +
                        " Код состояния: " + status);
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + status);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.Проверьте, пожалуйста," +
                    " URL-адрес и повторите попытку.");
        }
    }

    public String load(String key) {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status >= 200 && status <= 299) {
                System.out.println("Сервер успешно обработал запрос. Код состояния: " + status);
                return response.body();
            }
            if (status >= 400 && status <= 499) {
                System.out.println("Сервер сообщил о проблеме с запросом. Код состояния: " + status);
                return null;
            }
            if (status >= 500 && status <= 599) {
                System.out.println("Сервер сообщил о внутренней проблеме и невозможности обработать запрос." +
                        " Код состояния: " + status);
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + status);
            }
            return null;
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.Проверьте, пожалуйста," +
                    " URL-адрес и повторите попытку.");
            throw new RuntimeException(e);
        }
    }
}
