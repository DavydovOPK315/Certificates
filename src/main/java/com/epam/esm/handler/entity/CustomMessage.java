package com.epam.esm.handler.entity;

import lombok.Data;

@Data
public class CustomMessage {
    private String errorMessage;
    private int errorCode;
}
