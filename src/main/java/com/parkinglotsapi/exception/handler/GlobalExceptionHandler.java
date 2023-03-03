package com.parkinglotsapi.exception.handler;

import com.parkinglotsapi.domain.dto.ErrorResponseDto;
import com.parkinglotsapi.exception.BadRequestException;
import com.parkinglotsapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<ErrorResponseDto> handleBadRequest(final BadRequestException e) {
        return createResponseEntity(createExceptionWrapper(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<ErrorResponseDto> handleNotFound(final NotFoundException e) {
        return createResponseEntity(createExceptionWrapper(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<ErrorResponseDto> createResponseEntity(ErrorResponseDto errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    private ErrorResponseDto createExceptionWrapper(String message, HttpStatus httpStatus) {
        return new ErrorResponseDto(request, message, httpStatus);
    }

}
