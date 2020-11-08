package ysaak.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMathUtils {
    @Test
    public void testNullSafeRound_nullInput() {
        // Given

        // When
        Double result = MathUtils.nullSafeRound(null, 0);

        // Then
        assertThat(result).isNull();
    }

    @Test
    public void testNullSafeRound_notNullInput() {
        // Given
        Double input = 10.3433333;
        Double expectedValue = 10.34;

        // When
        Double result = MathUtils.nullSafeRound(input, 2);

        // Then
        assertThat(result).isNotNull()
                .isEqualTo(expectedValue);
    }
}
