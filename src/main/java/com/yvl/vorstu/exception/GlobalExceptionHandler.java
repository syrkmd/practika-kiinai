package com.yvl.vorstu.exception;

import com.yvl.vorstu.exception.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentNotFoundException.class)
    public ApiError handleStudentNotFound(StudentNotFoundException exception) {
        return new ApiError("STUDENT_NOT_FOUND", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GroupNotFoundException.class)
    public ApiError handleGroupNotFound(GroupNotFoundException exception) {
        return new ApiError("GROUP_NOT_FOUND", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TeacherNotFoundException.class)
    public ApiError handleTeacherNotFound(TeacherNotFoundException exception) {
        return new ApiError("TEACHER_NOT_FOUND", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TeacherGroupAssigmentNotFoundException.class)
    public ApiError handleTeacherGroupAssigmentNotFound(TeacherGroupAssigmentNotFoundException exception) {
        return new ApiError("TEACHER_GROUP_ASSIGMENT_NOT_FOUND", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ApiError handleUsernameAlreadyExists(UsernameAlreadyExistsException exception) {
        return new ApiError("USERNAME_ALREADY_EXISTS", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GroupAlreadyExistsException.class)
    public ApiError handleGroupAlreadyExists(GroupAlreadyExistsException exception) {
        return new ApiError("GROUP_ALREADY_EXISTS", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TeacherGroupAssigmentAlreadyExistsException.class)
    public ApiError handleTeacherGroupAssigmentAlreadyExists(TeacherGroupAssigmentAlreadyExistsException exception) {
        return new ApiError("TEACHER_GROUP_ASSIGMENT_ALREADY_EXISTS", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Validation error");

        return new ApiError("VALIDATION_ERROR", message);
    }
}
