package com.blog.blogging.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


//    @ExceptionHandler(CustomerNotFoundException.class)
//    @ResponseBody
//    public ResponseEntity<ExceptionResponse> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException)
//    {
//        return  ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ExceptionResponse.builder()
//                        .ErrorDescription(BusinessErrorCodes.CUSTOMER_NOT_FOUND.getDescription())
//                        .ErrorCode(BusinessErrorCodes.CUSTOMER_NOT_FOUND.getCode())
//                        .error(customerNotFoundException.getMessage())
//                        .build()
//                );
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleCustomerNotFoundException(ResourceNotFoundException resourceNotFoundException)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorDescription(BusinessErrorCodes.RESOURCE_NOT_FOUND.getDescription());
        exceptionResponse.setErrorCode(BusinessErrorCodes.RESOURCE_NOT_FOUND.getCode());
        exceptionResponse.setError(resourceNotFoundException.getMessage());
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleMethodArgsNotValidException(MethodArgumentNotValidException validException)
    {
        Map<String, String> validationErrors = new HashMap<>();
        validException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorDescription("Validation Failed");
        exceptionResponse.setErrors(validationErrors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }
}