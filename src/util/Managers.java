package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;

public class Managers {

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        return gsonBuilder.create();
    }

    public static TaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public static TaskManager getDefault(File file) {

        return new FileBackedTaskManager(file);
    }

    public static TaskManager getDefault(String url) throws IOException, InterruptedException {
        return new HttpTaskManager("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
