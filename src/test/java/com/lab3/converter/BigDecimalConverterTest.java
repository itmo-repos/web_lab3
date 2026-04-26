package com.lab3.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalConverterTest {

    BigDecimalConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BigDecimalConverter();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("null и пустая строка => null")
    void getAsObject_NullEmpty(String value) {
        assertNull(converter.getAsObject(null, null, value));
    }

    @Test
    @DisplayName("Прямое преобразование")
    void getAsObject_ValidString() {
        assertEquals(new BigDecimal("3.14"), converter.getAsObject(null, null, "3.14"));
        assertEquals(new BigDecimal("-2.5"), converter.getAsObject(null, null, "-2.5"));
        assertEquals(new BigDecimal("100"), converter.getAsObject(null, null, "100"));
        assertEquals(new BigDecimal("3.14"), converter.getAsObject(null, null, "3,14"));
        assertEquals(BigDecimal.ZERO, converter.getAsObject(null, null, "0"));
    }

    @Test
    @DisplayName("Что-то непонятное => null")
    void getAsObject_InvalidInput() {
        assertNull(converter.getAsObject(null, null, "abc"));
        assertNull(converter.getAsObject(null, null, "1.2.3"));
    }

    // getAsString

    @Test
    @DisplayName("null => пустая строка")
    void getAsString_Null() {
        assertEquals("", converter.getAsString(null, null, null));
    }

    @Test
    @DisplayName("Обратное преобразование")
    void getAsString_ValidDecimal() {
        assertEquals("3.14", converter.getAsString(null, null, new BigDecimal("3.14")));
        assertEquals("-2.5", converter.getAsString(null, null, new BigDecimal("-2.5")));
        assertEquals("0", converter.getAsString(null, null, BigDecimal.ZERO));
    }
}
