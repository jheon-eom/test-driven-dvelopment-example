package com.study.tdd;

import com.study.tdd.testdemo.PasswordMeter;
import com.study.tdd.testdemo.PasswordStrength;
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
        assertPasswordStrength("aaaD123", PasswordStrength.NORMAL);
        assertPasswordStrength("a123dDs", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("대문자 없음, 다른 조건 충족")
    void digitAndLength() {
        assertPasswordStrength("abcd1234", PasswordStrength.NORMAL);
        assertPasswordStrength("abcd12asd33", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("숫자 없음, 다른 조건 충족")
    void upperCaseAndLength() {
        assertPasswordStrength("ABCDabcdcs", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("길이만 충족")
    void length() {
        assertPasswordStrength("asdfsdkc", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("대문자만 충족")
    void upperCase() {
        assertPasswordStrength("ASDFVXC", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("숫자만 충족")
    void digit() {
        assertPasswordStrength("123sadf", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("아무것도 충족하지 않음")
    void nothing() {
        assertPasswordStrength("dd", PasswordStrength.WEAK);
    }

    void assertPasswordStrength(String password, PasswordStrength expected) {
        PasswordStrength result = passwordMeter.meter(password);
        assertThat(result).isEqualTo(expected);
    }
}
