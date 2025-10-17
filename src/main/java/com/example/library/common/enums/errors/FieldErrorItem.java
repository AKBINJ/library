package com.example.library.common.enums.errors;

public record FieldErrorItem(
        String field,       // 필드명
        String rejected,    // 거부값(문자열화)
        String message      // 오류 사유
) {
}
