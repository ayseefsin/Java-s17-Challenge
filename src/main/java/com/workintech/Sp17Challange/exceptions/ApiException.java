package com.workintech.Sp17Challange.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ApiException extends RuntimeException{

    HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
