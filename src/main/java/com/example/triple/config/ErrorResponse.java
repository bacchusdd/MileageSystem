package com.example.triple.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse<T> extends BasicResponse {

    private String errMsg;
    private String errCode;

    public ErrorResponse(String errMsg){
        this.errMsg = errMsg;
        this.errCode = "404";
    }

    public ErrorResponse(String errMsg, String errCode){
        this.errMsg = errMsg;
        this.errCode = errCode;
    }
}
