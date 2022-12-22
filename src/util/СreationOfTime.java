package util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class СreationOfTime {
    public static final ZoneId zoneId = ZoneId.of("Europe/Moscow");
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd | HH:mm | ZZZZZ VV");
    public static final ZonedDateTime defaultStartTime = ZonedDateTime.of
            (LocalDateTime.of(3000, 01, 01, 01, 01), zoneId);
    public static final Duration defaultDuration = Duration.ZERO;
}
