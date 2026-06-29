package com.yvl.vorstu.exception;

public class TeacherGroupAssigmentNotFoundException extends RuntimeException {
    public TeacherGroupAssigmentNotFoundException(Long id) {
        super("Teacher group assigment with id: " + id + " was not found");
    }
}
