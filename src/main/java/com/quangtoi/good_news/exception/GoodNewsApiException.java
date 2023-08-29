package com.quangtoi.good_news.exception;

import org.springframework.http.HttpStatus;

public class GoodNewsApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public GoodNewsApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
