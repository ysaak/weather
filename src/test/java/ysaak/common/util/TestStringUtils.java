package ysaak.common.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class TestStringUtils {
    @Test
    public void testIsBlank_blankValues() {
        // Given
        List<String> blankStringList = Arrays.asList(
                null,
                "",
                "     ",
                "\t",
                "\t     ",
                "\n"
        );

        // When
        for (int i = 0; i < blankStringList.size(); i++) {

            String stringToTest = blankStringList.get(i);

            assertThat(StringUtils.isBlank(stringToTest))
                    .withFailMessage(String.format("String '%s' (index %d) is not considered as blank", stringToTest, i))
                    .isTrue();
        }
    }

    @Test
    public void testIsBlank_notBlankValues() {
        // Given
        List<String> notBlankStringList = Arrays.asList(
                "text",
                " text "
        );

        // When
        for (int i = 0; i < notBlankStringList.size(); i++) {

            String stringToTest = notBlankStringList.get(i);

            assertThat(StringUtils.isBlank(stringToTest))
                    .withFailMessage(String.format("String '%s' (index %d) is considered as blank", stringToTest, i))
                    .isFalse();
        }
    }
    @Test
    public void testIsNotBlank_blankValues() {
        // Given
        List<String> blankStringList = Arrays.asList(
                null,
                "",
                "     ",
                "\t",
                "\t     ",
                "\n"
        );

        // When
        for (int i = 0; i < blankStringList.size(); i++) {

            String stringToTest = blankStringList.get(i);
            assertThat(StringUtils.isNotBlank(stringToTest))
                    .withFailMessage(String.format("String '%s' (index %d) is not considered as blank", stringToTest, i))
                    .isFalse();
        }
    }

    @Test
    public void testIsNotBlank_notBlankValues() {
        // Given
        List<String> notBlankStringList = Arrays.asList(
                "text",
                " text "
        );

        // When
        for (int i = 0; i < notBlankStringList.size(); i++) {
            String stringToTest = notBlankStringList.get(i);

            assertThat(StringUtils.isNotBlank(stringToTest))
                    .withFailMessage(String.format("String '%s' (index %d) is considered as blank", stringToTest, i))
                    .isTrue();
        }
    }

    @Test
    public void testGetNotNull_nullValue() {
        // Given
        String nullString = null;
        String expectedResult = StringUtils.EMPTY;

        // When
        String result = StringUtils.getNotNull(nullString);

        // Then
        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedResult);
    }

    @Test
    public void testGetNotNull_notNullValue() {
        // Given
        String notNullString = "text";

        // When
        String result = StringUtils.getNotNull(notNullString);

        // Then
        assertThat(result)
                .isNotNull()
                .isSameAs(notNullString);
    }

    @Test
    public void testExtractDigits_withoutDigits() {
        // Given
        String testString = "no_digit";
        int expectedResult = 0;

        // When
        int result = StringUtils.extractDigits(testString);

        // Assert
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testExtractDigits_withDigits() {
        // Given
        String testString = "S123LKJ456H";
        int expectedResult = 123456;

        // When
        int result = StringUtils.extractDigits(testString);

        // Assert
        assertThat(result).isEqualTo(expectedResult);
    }
}
