package com.yvl.vorstu.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Long id) {
        super("Teacher with id: " + id + " was not found");
    }
}
