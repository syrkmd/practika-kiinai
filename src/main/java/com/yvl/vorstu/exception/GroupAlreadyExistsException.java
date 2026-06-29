package com.yvl.vorstu.exception;

public class GroupAlreadyExistsException extends RuntimeException {

    public GroupAlreadyExistsException(String name) {
        super("Group with name: " + name + " already exists");
    }
}