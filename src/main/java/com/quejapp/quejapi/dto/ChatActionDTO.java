package com.quejapp.quejapi.dto;

import java.util.List;

public class ChatActionDTO {

    private String action;

    private String entity;

    private String fileName;

    private List<String> headers;

    // GET ACTION
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    // GET ENTITY
    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    // GET FILENAME
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // GET HEADERS
    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}