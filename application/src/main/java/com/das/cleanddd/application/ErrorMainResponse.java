package com.das.cleanddd.application;

public class ErrorMainResponse {
    private int statusCode;
    private String message;

    public ErrorMainResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // Getters and setters
    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}