package com.Majid.Board_API2.Request;

import lombok.Data;

@Data

public class CardRequest {
    private String title;
    private String description;
    private int section;
}