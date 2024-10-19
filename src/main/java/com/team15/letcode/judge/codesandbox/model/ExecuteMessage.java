package com.team15.letcode.judge.codesandbox.model;

import lombok.Data;

@Data
public class ExecuteMessage {
    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;
}