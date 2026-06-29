package com.yvl.vorstu.exception;

public class TeacherGroupAssigmentAlreadyExistsException extends RuntimeException {

    public TeacherGroupAssigmentAlreadyExistsException(Long teacherId, Long groupId) {
        super("Teacher with id: " + teacherId + " is already assigned to group with id: " + groupId);
    }
}