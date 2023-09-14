package com.example.entity;

public class ApiError {

    private int status;
    private String reason;

    public ApiError() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ApiError(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "status=" + status +
                ", reason='" + reason + '\'' +
                '}';
    }
}
