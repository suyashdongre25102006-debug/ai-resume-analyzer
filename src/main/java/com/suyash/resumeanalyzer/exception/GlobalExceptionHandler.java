package com.suyash.resumeanalyzer.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(StudentNotFoundException.class)
    public Map<String, String> handleStudentNotFound(
            StudentNotFoundException ex)
    {
        return Map.of(
                "error",
                ex.getMessage()
        );
    }
}