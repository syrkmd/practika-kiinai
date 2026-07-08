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

    @ExceptionHandler(InvalidRegistrationInvitationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleInvalidRegistrationInvitation(InvalidRegistrationInvitationException exception) {
        return new ApiError("INVALID_REGISTRATION_INVITATION", exception.getMessage());
    }

    @ExceptionHandler(RegistrationInvitationExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ApiError handleRegistrationInvitationExpired(RegistrationInvitationExpiredException exception) {
        return new ApiError("REGISTRATION_INVITATION_EXPIRED", exception.getMessage());
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ApiError handleEmailAlreadyExists(EmailAlreadyExistsException exception) {
        return new ApiError("EMAIL_ALREADY_EXISTS", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RegistrationInvitationAlreadyExistsException.class)
    public ApiError handleRegistrationInvitationAlreadyExists(RegistrationInvitationAlreadyExistsException exception) {
        return new ApiError("REGISTRATION_INVITATION_ALREADY_EXISTS", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ApiError handleUserAlreadyExists(UserAlreadyExistsException exception) {
        return new ApiError("USER_ALREADY_EXISTS", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ApiError handleStudentAccessDenied(AccessDeniedException exception) {
        return new ApiError("ACCESS_DENIED", exception.getMessage());
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleInvalidRefreshToken(InvalidRefreshTokenException exception) {
        return new ApiError("INVALID_REFRESH_TOKEN", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEmailException.class)
    public ApiError handleInvalidEmail(InvalidEmailException exception) {
        return new ApiError("INVALID_EMAIL", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRoleException.class)
    public ApiError handleInvalidRole(InvalidRoleException exception) {
        return new ApiError("INVALID_ROLE", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyCsvFileException.class)
    public ApiError handleEmptyCsvFile(EmptyCsvFileException exception) {
        return new ApiError("EMPTY_CSV_FILE", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CsvProcessingException.class)
    public ApiError handleCsvProcessing(CsvProcessingException exception) {
        return new ApiError("CSV_PROCESSING_ERROR", exception.getMessage());
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

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmailSendingException.class)
    public ApiError handleEmailSending(EmailSendingException exception) {
        return new ApiError("EMAIL_SENDING_ERROR", exception.getMessage());
    }
}
