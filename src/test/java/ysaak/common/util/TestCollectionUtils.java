package ysaak.common.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class TestCollectionUtils {

    @Test
    public void testIsCollectionOfType_emptyList() {
        // Given
        final List<Object> emptyList = Collections.emptyList();
        final boolean expectedResult = false;

        // When
        final boolean result = CollectionUtils.isCollectionOfType(emptyList, String.class);

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testIsCollectionOfType_validType() {
        // Given
        final List<String> list = new ArrayList<>();
        list.add("String2");

        final boolean expectedResult = true;

        // When
        final boolean result = CollectionUtils.isCollectionOfType(list, String.class);

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testIsCollectionOfType_validSubType() {
        // Given
        final List<Integer> list = new ArrayList<>();
        list.add(1);

        final boolean expectedResult = true;

        // When
        final boolean result = CollectionUtils.isCollectionOfType(list, Number.class);

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testIsCollectionOfType_invalidType() {
        // Given
        final List<String> list = new ArrayList<>();
        list.add("String2");

        final boolean expectedResult = false;

        // When
        final boolean result = CollectionUtils.isCollectionOfType(list, Integer.class);

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }
}
