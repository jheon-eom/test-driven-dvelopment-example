package com.study.tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordMeterTest {
    private PasswordMeter passwordMeter = new PasswordMeter();

    @Test
    @DisplayName("null 입력이면 예외 발생")
    void nullInput() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> passwordMeter.meter(null));
    }

    @Test
    @DisplayName("빈 값 입력이면 예외 발생")
    void emptyInput() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> passwordMeter.meter(""));
    }

    @Test
    @DisplayName("모든 조건을 충족하면 강함")
    void meetAllRules() {
        assertPasswordStrength("abcABC123", PasswordStrength.STRONG);
        assertPasswordStrength("123abcABC", PasswordStrength.STRONG);
    }

    @Test
    @DisplayName("길이가 8 미만, 그 외 조건은 충족")
    void digitAndUppercase() {
        assertPasswordStrength("abcC123", PasswordStrength.NORMAL);
        assertPasswordStrength("1233Casc", PasswordStrength.NORMAL);
    }

    void assertPasswordStrength(String password, PasswordStrength expected) {
        PasswordStrength result = passwordMeter.meter(password);
        assertThat(result).isEqualTo(expected);
    }
}