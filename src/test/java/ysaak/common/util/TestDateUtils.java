package ysaak.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDateUtils {
    @Test
    public void testToTimestamp_success() {
        // Given
        LocalDateTime timeToConvert = LocalDateTime.of(2011, 10, 16, 16, 17, 56);
        long expectedTimestamp = 1318781876L;

        // When
        long timestamp = DateUtils.toLocalTimestamp(timeToConvert);

        // Then
        assertThat(timestamp).isEqualTo(expectedTimestamp);
    }
}
