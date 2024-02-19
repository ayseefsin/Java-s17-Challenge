package com.workintech.Sp17Challange.exceptions;

import com.workintech.Sp17Challange.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException apiException){
        log.error("Api exception exist! Exception details: " + apiException.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(apiException.getHttpStatus(),apiException.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse,apiException.getHttpStatus());
    }
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleAllException(Exception exception){
        log.error("Exception exist! Exception details: " + exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
