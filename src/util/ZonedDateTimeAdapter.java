package util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.ZonedDateTime;

import static util.CreationOfTime.defaultStartTime;
import static util.CreationOfTime.formatter;

public class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {

    @Override
    public void write(JsonWriter jsonWriter, ZonedDateTime zonedDateTime) throws IOException {
        if (zonedDateTime == null) {
            zonedDateTime = defaultStartTime;
            jsonWriter.value(zonedDateTime.format(formatter));
            return;
        }
        jsonWriter.value(zonedDateTime.format(formatter));
    }

    @Override
    public ZonedDateTime read(JsonReader jsonReader) throws IOException {
        return ZonedDateTime.parse(jsonReader.nextString(), formatter);
    }
}
