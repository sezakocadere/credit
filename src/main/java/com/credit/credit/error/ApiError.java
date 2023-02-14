package com.credit.credit.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public record ApiError(String message, HttpStatus status, LocalDateTime timeStamp) {
}
