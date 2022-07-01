package com.example.triple.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponse<T> extends BasicResponse {

    private String type;
    private String action;
    private T result;

    public CommonResponse(String type, String action, T result) {
        this.type = type;
        this.action = action;
        this.result = result;
    }
}
