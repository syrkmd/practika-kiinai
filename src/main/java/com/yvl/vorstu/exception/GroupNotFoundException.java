package com.yvl.vorstu.exception;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long id) {
        super("Group with id: " + id + " was not found");
    }
}
