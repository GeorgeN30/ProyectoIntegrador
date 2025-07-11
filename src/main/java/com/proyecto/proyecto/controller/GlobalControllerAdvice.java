package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.exception.*;
import com.proyecto.proyecto.model.dto.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.proyecto.proyecto.util.ErrorCatalog.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ErrorResponse handleBookNotFoundException() {
        return ErrorResponse.builder()
            .code(BOOK_NOT_FOUND.getCode())
            .status(HttpStatus.NOT_FOUND)
            .message(BOOK_NOT_FOUND.getMessage())
            .timeStamp(LocalDateTime.now())
            .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthorNotFoundException.class)
    public ErrorResponse handleAuthorNotFoundException() {
        return ErrorResponse.builder()
                .code(AUTHOR_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(AUTHOR_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ErrorResponse handleCategoryNotFoundException() {
        return ErrorResponse.builder()
                .code(CATEGORY_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(CATEGORY_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReservationNotFoundException.class)
    public ErrorResponse handleReservationNotFoundException() {
        return ErrorResponse.builder()
                .code(RESERVATION_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(RESERVATION_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException() {
        return ErrorResponse.builder()
                .code(USER_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(USER_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException
            (MethodArgumentNotValidException exception,
             WebRequest request) {

        BindingResult rst = exception.getBindingResult();
        String path = request.getDescription(false); //Obtener la URL sin detalles de la solicitud.
        String errorCode = INVALID_AUTHOR.getCode();
        String errorMessage = INVALID_AUTHOR.getMessage();

        if (path.contains("/books")) {
            errorCode = INVALID_BOOK.getCode();
            errorMessage = INVALID_BOOK.getMessage();
        } else if (path.contains("/categories")) {
            errorCode = INVALID_CATEGORY.getCode();
            errorMessage = INVALID_CATEGORY.getMessage();
        } else if (path.contains("/reservations")) {
            errorCode = INVALID_RESERVATION.getCode();
            errorMessage = INVALID_RESERVATION.getMessage();
        } else if (path.contains("/users")) {
            errorCode = INVALID_USER.getCode();
            errorMessage = INVALID_USER.getMessage();
        }

        return ErrorResponse.builder()
                .code(errorCode)
                .status(HttpStatus.BAD_REQUEST)
                .message(errorMessage)
                .detailMessage(rst.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()))
                .timeStamp(LocalDateTime.now())
                .build();
    }



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleInternalServerError(Exception exception) {
        return ErrorResponse.builder()
                .code(GENERIC_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(GENERIC_ERROR.getMessage())
                .detailMessage(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
