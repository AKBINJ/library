package com.example.library.handler;

import com.example.library.common.enums.ErrorCode;
import com.example.library.common.enums.errors.ErrorResponse;
import com.example.library.common.enums.errors.FieldErrorItem;
import com.example.library.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    public ResponseEntity<ResponseDto<Object>> fail(
            ErrorCode code, String reason, List<FieldErrorItem> errors
            // : 실패 응답을 한곳에서 처리
    ) {
        // reason 값 설정: 비워질 경우 ErrorCode의 기본 메시지 값 사용
        String finalReason = (reason != null && !reason.isBlank()) ? reason : code.defaultMessage;
        // 클라이언트가 해석할 수 있는 표준 에러 응답 본문 조립
        ErrorResponse body = ErrorResponse.of(code.code, finalReason, errors);
        return ResponseEntity.status(code.status)
                .body(ResponseDto.setFailed(finalReason, body));
    }

    // @Valid(@RequestBody) 검증 실패 항목을 표준 형식으로 변환
    private List<FieldErrorItem> toFieldErrors(MethodArgumentNotValidException e){
        List<FieldErrorItem> list = new ArrayList<>();
        // 필드 단위 검증 실패 항목 순회
        e.getBindingResult().getFieldErrors().forEach(err -> {
            list.add(new FieldErrorItem(
                    err.getField(), // 실패한 필드명
                    Objects.toString(err.getRejectedValue(), "null"), // 거부도니 값(null 이면 null)
                    err.getDefaultMessage() // 제약 메시지
            ));
        });

        // GlobalErrorCheck
        e.getBindingResult().getGlobalErrors().forEach(err -> {
            //                              필드명 대신 오브젝트명
            list.add(new FieldErrorItem(err.getObjectName(), "", err.getDefaultMessage()));
        });
        return list;
    }
    // == 400 : BAD_REQUEST - 잘못된 인자/상테 ==
    @ExceptionHandler({IllegalIdentifierException.class, IllegalStateException.class})
    public ResponseEntity<ResponseDto<Object>> handleBadRequest(Exception e){
        log.warn("Bad Request: {}", e.getMessage());
        return fail(ErrorCode.BAD_REQUEST, null, null);
    }
    // == 400 : VALIDATION ERROR - @Valid @RequestBody 검증 실패 ==
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Object>> handleValidation(MethodArgumentNotValidException e){
        log.warn("Validation Error: {}", e.getMessage());
        return fail(ErrorCode.VALIDATION_ERROR, null, toFieldErrors(e));
    }
    // == 401 : Unauthorized - 권한 없음(잘못된 자젹 증명) ==
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto<Object>> handleAuth(AuthenticationException e){
        log.warn("AUTH_INVALID: {}", e.getMessage());
        return fail(ErrorCode.AUTH_INVALID, null, null);
    }
    // == 403 : FORBIDDEN ERROR - 접근 거부==
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto<Object>> handleAccessDenied(AccessDeniedException e){
        log.warn("AUTH_FORBIDDEN: {}", e.getMessage());
        return fail(ErrorCode.AUTH_FORBIDDEN, null, null);
    }

    // == 404 : NOT_FOUND - 엔티티 조회 실패
    // 조건문 추가해서 아래 Exception들 추가해야함
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> EntityNotFound(EntityNotFoundException e){
        log.warn("ENTITY_NOT_FOUND: {}", e.getMessage());
        return fail(ErrorCode.ENTITY_NOT_FOUND, null, null);
    }

//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<ResponseDto<Object>> handleBusiness(BusinessException e){
//        ErrorCode code = e.getErrorCode();
//        String reason = (e.getReason() != null && !e.getReason().isBlank()) ? e.getReason() : null;
//        log.warn("Business error[{}] : {}", code.name(), e.getMessage());
//        return fail(code, reason, null);
//    }

/*
     == serviceImpl 에서 사용
    if (projectId == null) {
       잘못된 요청 → 400
       throw new BusinessException(BAD_REQUEST, "PROJECT_ID_REQUIRED");
  }
 */


    // == 409 : CONFLICT - 무결성 위반 (중복/제약조건) ==
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<Object>> handleConflict(DataIntegrityViolationException e){
        log.warn("USER_DUPLICATED: {}", e.getMessage());
        return fail(ErrorCode.USER_DUPLICATED, null, null);
    }
    // == 500 : 그밖의 모든 에러==
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleException(Exception e){
        log.warn("Iternal Error: {}", e.getMessage());
        return fail(ErrorCode.INTERNAL_ERROR, null, null);
    }
}
