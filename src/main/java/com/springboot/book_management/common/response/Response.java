package com.springboot.book_management.common.response;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Response<T> {
    private ResponseStatus status;
    private String message;
    private T data;

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public Response(ResponseStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
