package com.PrototypeServer.spring.model;

// This is the response sent to the android application if the login is unsuccessful. We used ErrorResponse in the LoginController.
public class ErrorResponse {
    int id;
    String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
