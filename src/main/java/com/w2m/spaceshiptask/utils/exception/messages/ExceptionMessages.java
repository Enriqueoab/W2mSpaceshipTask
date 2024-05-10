package com.w2m.spaceshiptask.utils.exception.messages;

public enum ExceptionMessages {

    SPACESHIP_NOT_FOUND("The spaceship was not found"),
    SOURCE_NOT_FOUND("No source found"),
    REMOVE_SPACESHIP_ERROR("We were unable to delete the spaceship"),
    REQUEST_RETURN_EMPTY("The requested return no results."),
    UNAUTHORIZED_ACCESS("Unauthorized access to the resource."),
    SERVER_ERROR("An unexpected error occurred on the server.");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
