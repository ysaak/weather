package ysaak.common.util;

import org.junit.jupiter.api.Test;
import ysaak.common.exception.ErrorCode;
import ysaak.common.exception.FunctionalException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.fail;

public class TestValidate {
    @Test
    public void testNotNull_nullValue_genericException() {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        try {
            Validate.notNull(null, errorCode, "arg");

            fail("Exception should have been thrown");
        }
        catch (FunctionalException e) {
            // Check if thrown exception is the correct one
            assertThat(e.getError()).isEqualTo(errorCode);
        }
    }

    @Test
    public void testNotNull_nullValue_customException() {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        try {
            Validate.notNull(null, errorCode, "arg");

            fail("Exception should have been thrown");
        }
        catch (FunctionalException e) {
            // Check if thrown exception is the correct one
            assertThat(e.getError()).isEqualTo(errorCode);
        }
    }

    @Test
    public void testNotNull_notNullValue() throws FunctionalException {
        // Given
        Object testObject = new Object();
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.notNull(testObject, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testNotBlank_blankValue() {
        // Given
        String testValue = " ";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.notBlank(testValue, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testNotBlank_notBlankValue() throws FunctionalException {
        // Given
        String testValue = " text ";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.notBlank(testValue, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testLength_nullValue() {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.length(null, 5, 10, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testLength_lessThanMinLength() {
        // Given
        String testValue = "12";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.length(testValue, 5, 10, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testLength_moreThanMaxLength() {
        // Given
        String testValue = "123456789012";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.length(testValue, 5, 10, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testLength_minLengthValue() throws FunctionalException {
        // Given
        String testValue = "12345";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.length(testValue, 5, 10, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testLength_maxLengthValue() throws FunctionalException {
        // Given
        String testValue = "1234567890";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.length(testValue, 5, 10, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testLength_betweenLengthValue() throws FunctionalException {
        // Given
        String testValue = "1234567";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.length(testValue, 5, 10, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testMinLength_lessThanMinLength() {
        // Given
        String testValue = "123";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.minLength(testValue, 5, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testMinLength_MoreThanMinLength() throws FunctionalException {
        // Given
        String testValue = "123456";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.minLength(testValue, 5, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testMaxLength_MoreThanMaxLength() {
        // Given
        String testValue = "123456";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.maxLength(testValue, 5, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testMaxLength_LessThanMaxLength() throws FunctionalException {
        // Given
        String testValue = "123";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.maxLength(testValue, 5, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testMinLengthCollection_nullValue() {
        // Given
        List<String> testValue = null;
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.minLength(testValue, 5, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testMinLengthCollection_lessThanMinLength() {
        // Given
        List<String> testValue = Arrays.asList("1", "2", "3");
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.minLength(testValue, 5, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testMinLengthCollection_MoreThanMinLength() throws FunctionalException {
        // Given
        List<String> testValue = Arrays.asList("1", "2", "3", "4", "5", "6");
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.minLength(testValue, 5, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testValidUrl_invalid() {
        // Given
        String testValue = "blob";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.validUrl(testValue, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testValidUrl_valid() throws FunctionalException {
        // Given
        String testValue = "https://www.qwant.com/?q=%s";
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.validUrl(testValue, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testIsTrue_invalid() {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.isTrue(false, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testIsTrue_valid() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.isTrue(true, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testIsFalse_invalid() {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.isFalse(true, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testIsFalse_valid() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.isFalse(false, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testBetween_validIntegerInclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.between(5, 5, 10, true, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testBetween_lowerIntegerInclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.between(4, 5, 10, true, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testBetween_upperIntegerInclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.between(4, 5, 10, true, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testBetween_lowerIntegerExclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.between(5, 5, 10, false, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testBetween_upperIntegerExclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.between(10, 5, 10, false, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testBetween_validDoubleInclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Validate.between(5.0, 5., 10., true, errorCode, "arg");

        // Then
        // Success
    }

    @Test
    public void testBetween_lowerDoubleInclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.between(4., 5., 10., true, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testBetween_upperDoubleInclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.between(4., 5., 10., true, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testBetween_lowerDoubleExclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.between(5., 5., 10., false, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    @Test
    public void testBetween_upperDoubleExclusive() throws FunctionalException {
        // Given
        ErrorCode errorCode = TestErrorCode.TEST;

        // When
        Throwable thrown = catchThrowable(() -> Validate.between(10., 5., 10., false, errorCode, "arg"));

        // Then
        assertThat(thrown)
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", errorCode);
    }

    private enum TestErrorCode implements ErrorCode {
        TEST("CODE", "MESSAGE %s")
        ;

        private final String code;
        private final String message;

        TestErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
