package com.yuuki.katou.inventory_management_app01.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    //ResourceNotFoundExceptionを処理するハンドラー
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(
            ResourceNotFoundException e, HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.NOT_FOUND);
    }

    //InternalConsistencyExceptionを処理するハンドラー
    @ExceptionHandler(value = InternalConsistencyException.class)
    public ResponseEntity<Map<String, String>> handleInternalConsistency(
            InternalConsistencyException e, HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // エラーメッセージとフィールド名をマップに保存
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // 応答ボディを作成
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", ZonedDateTime.now().toString());
        body.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", "Validation failed");
        body.put("details", errors);
        body.put("path", request.getRequestURI());

        // 応答ボディとHTTPステータスを使用して、ResponseEntityを返す
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //すべての例外ハンドラで共通のレスポンスを構築するためのメソッド
    private ResponseEntity<Map<String, String>> buildResponseEntity(
            Exception e, HttpServletRequest request, HttpStatus status) {


        //エラー情報を含むレスポンスボディをマップとして構築
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),//エラー発生時のタイムスタンプ
                "status", String.valueOf(status.value()),       //HTTPステータスコード
                "error", status.getReasonPhrase(),              //HTTPステータスの理由
                "message", e.getMessage(),                      //例外から取得したエラーメッセージ
                "path", request.getRequestURI());               //エラーが発生したリクエストのURI

        // 構築したレスポンスボディとHTTPステータスを使用してResponseEntityを作成して返す
        return new ResponseEntity<>(body, status);
    }
}
