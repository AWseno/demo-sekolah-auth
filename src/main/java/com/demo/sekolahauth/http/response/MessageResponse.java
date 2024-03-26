package com.demo.sekolahauth.http.response;

import java.util.HashMap;
import java.util.Map;

public class MessageResponse {

    private Integer code;
    private String description;
    private Map<String, Object> additionalEntity = new HashMap<String, Object>();
    private Map<String, String> defaultInfos = new HashMap<String, String>();

    public MessageResponse() {

    }

    public MessageResponse(Integer code, String description, Map<String, Object> additionalEntity, Map<String, String> defaultInfos) {
        this.code = code;
        this.description = description;
        this.additionalEntity = additionalEntity;
        this.defaultInfos = defaultInfos;
    }

    public MessageResponse(Integer code, String description, Object additionalEntity, Map<String, String> defaultInfos) {
        HashMap resultMap = new HashMap();
        resultMap.put("result", additionalEntity);
        this.code = code;
        this.description = description;
        this.additionalEntity = resultMap;
        this.defaultInfos = defaultInfos;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getAdditionalEntity() {
        return additionalEntity;
    }

    public void setAdditionalEntity(Map<String, Object> additionalEntity) {
        this.additionalEntity = additionalEntity;
    }

    public Map<String, String> getDefaultInfos() {
        return defaultInfos;
    }

    public void setDefaultInfos(Map<String, String> defaultInfos) {
        this.defaultInfos = defaultInfos;
    }

}
