package com.workintech.Sp17Challange.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private HttpStatus status;
    private String message;
    private Long timestamp;

}
