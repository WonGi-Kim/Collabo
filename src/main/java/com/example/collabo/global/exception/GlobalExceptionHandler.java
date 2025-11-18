package com.example.collabo.global.exception;

import com.example.collabo.global.common.CommonErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonErrorResponse> handleCustomException(final CustomException e) {
        log.error("CustomException: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(
                CommonErrorResponse.builder()
                        .message(e.getMessage())
                        .error(e.getStatusCode().getReasonPhrase())
                        .statusCode(e.getStatusCode().value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());

        // 모든 필드 에러를 수집
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonErrorResponse.builder()
                        .message("입력값 검증에 실패했습니다: " + errors)
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(CommonErrorResponse.builder()
                        .message("지원하지 않는 HTTP 메서드입니다: " + ex.getMethod())
                        .error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                        .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CommonErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        log.error("NoResourceFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonErrorResponse.builder()
                        .message("요청한 리소스를 찾을 수 없습니다")
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonErrorResponse> handleException(Exception ex) {
        log.error("Unexpected Exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonErrorResponse.builder()
                        .message("서버 오류가 발생했습니다")
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
