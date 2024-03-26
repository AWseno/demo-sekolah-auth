package com.demo.sekolahauth.http.exception;

import java.util.Map;

public class AuthException extends RuntimeException   {

    private String code ;
    private String description ;
    private Map<String, Object> additionalEntity ;
    private Map<String, String> defaultInfos ;

    public AuthException(String message, String code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }


    public AuthException(String message, String code, String description ,Map<String, Object> additionalEntity , Map<String, String> defaultInfos) {
        super(message);
        this.code = code;
        this.description = description;
        this.additionalEntity = additionalEntity ;
        this.defaultInfos = defaultInfos;
    }
}