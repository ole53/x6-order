package ru.jabka.x6_order.model;

public class ApiError {

    final boolean success;
    final String message;

    public ApiError(final String message) {
        this.success = false;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}