package com.lab3.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка попадания в область")
class AreaHitCheckerTest {

    // Валидация

    @ParameterizedTest(name = "X = {0} - выход за границу [-6; 6]")
    @ValueSource(strings = {"6.1", "-6.01", "100", "-100"})
    void validateX_OutOfRange(String x) {
        String error = AreaHitChecker.validateParameters(
                new BigDecimal(x), BigDecimal.ZERO, BigDecimal.ONE
        );
        assertNotNull(error);
    }

    @ParameterizedTest(name = "X = {0} - в пределах нужного [-6; 6]")
    @ValueSource(strings = {"0", "-3", "5", "-6", "6"})
    void validateX_Valid(String x) {
        String error = AreaHitChecker.validateParameters(
                new BigDecimal(x), BigDecimal.ZERO, BigDecimal.ONE
        );
        assertNull(error);
    }

    @ParameterizedTest(name = "Y = {0} - выход за границы [-6; 6]")
    @ValueSource(strings = {"6.1", "-6.01", "100"})
    void validateY_OutOfRange(String y) {
        String error = AreaHitChecker.validateParameters(
                BigDecimal.ZERO, new BigDecimal(y), BigDecimal.ONE
        );
        assertNotNull(error);
    }

    @ParameterizedTest(name = "R = {0} - вне допустимого [1; 5]")
    @ValueSource(strings = {"0.9", "0", "-1", "5.1", "10"})
    void validateR_OutOfRange(String r) {
        String error = AreaHitChecker.validateParameters(
                BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(r)
        );
        assertNotNull(error);
    }

    @ParameterizedTest(name = "R = {0} - в пределах [1; 5]")
    @ValueSource(strings = {"1", "2", "3", "5"})
    void validateR_Valid(String r) {
        String error = AreaHitChecker.validateParameters(
                BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(r)
        );
        assertNull(error);
    }

    @Test
    @DisplayName("Локализованное сообщение об ошибке")
    void validateParameters_RussianCheck() {
        String error = AreaHitChecker.validateParameters(
                new BigDecimal("10"), BigDecimal.ZERO, BigDecimal.ONE,
                new Locale("ru")
        );
        assertNotNull(error);
        assertTrue(error.contains("не должно превышать"),
                "Сообщение должно быть на русском: " + error);
    }

    // Попадание / Промах

    @Test
    @DisplayName("(x>0, y>0) - всегда промах")
    void area1() {
        assertFalse(AreaHitChecker.checkHit(
                new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("4")
        ));
        assertFalse(AreaHitChecker.checkHit(
                new BigDecimal("0.1"), new BigDecimal("5"), new BigDecimal("4")
        ));
    }

    @Test
    @DisplayName("Точка внутри прямоугольника")
    void area2() {
        assertTrue(AreaHitChecker.checkHit(
                new BigDecimal("-1"), new BigDecimal("1"), new BigDecimal("4")
        ));
        assertTrue(AreaHitChecker.checkHit(
                new BigDecimal("-4"), new BigDecimal("2"), new BigDecimal("4")
        ));

        assertFalse(AreaHitChecker.checkHit(
                new BigDecimal("-1"), new BigDecimal("3"), new BigDecimal("4")
        ));
        assertFalse(AreaHitChecker.checkHit(
                new BigDecimal("-5"), new BigDecimal("1"), new BigDecimal("4")
        ));
    }


    @Test
    @DisplayName("Точка внутри/вне четверти круга")
    void area3() {
        assertTrue(AreaHitChecker.checkHit(
                new BigDecimal("-0.5"), new BigDecimal("-0.5"), new BigDecimal("2")
        ));
        assertTrue(AreaHitChecker.checkHit(
                new BigDecimal("-0.6"), new BigDecimal("-0.8"), new BigDecimal("2")
        ));

        assertFalse(AreaHitChecker.checkHit(
                new BigDecimal("-0.7"), new BigDecimal("-0.8"), new BigDecimal("2")
        ));
    }



    @Test
    @DisplayName("Точка внутри/вне треугольника")
    void area4() {
        assertTrue(AreaHitChecker.checkHit(
                new BigDecimal("1"), new BigDecimal("-1"), new BigDecimal("4")
        ));
        assertTrue(AreaHitChecker.checkHit(
                new BigDecimal("2"), new BigDecimal("0"), new BigDecimal("4")
        ));

        assertFalse(AreaHitChecker.checkHit(
                new BigDecimal("3"), new BigDecimal("-1"), new BigDecimal("4")
        ));
    }


    @Test
    @DisplayName("Начало координат (0,0)")
    void zeroZero() {
        assertTrue(AreaHitChecker.checkHit(
                BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("4")
        ));
    }

    @Test
    @DisplayName("Точка на положительной полуоси X")
    void positiveXAxis() {
        assertTrue(AreaHitChecker.checkHit(
                new BigDecimal("2"), BigDecimal.ZERO, new BigDecimal("4")
        ));
    }

    @Test
    @DisplayName("Точка на отрицательной полуоси X")
    void negativeXAxis() {
        assertTrue(AreaHitChecker.checkHit(
                new BigDecimal("-3"), BigDecimal.ZERO, new BigDecimal("4")
        ));
    }

}
