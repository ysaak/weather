package ysaak.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateUtils {
    public static final ZoneId LOCAL_ZONE = ZoneId.systemDefault();

    public static LocalDateTime getDateTimeUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static long toLocalTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.of("Z")).withZoneSameInstant(ZoneId.systemDefault()).toEpochSecond();
    }
}
