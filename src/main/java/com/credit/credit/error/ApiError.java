package com.credit.credit.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private final String message;
    private final HttpStatus status;
    private final String timeStamp;

}
