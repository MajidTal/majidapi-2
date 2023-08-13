package com.Majid.Board_API2.Response;

import lombok.Data;

@Data
public class CardResponse {
    private Long card_id;
    private String title;
    private String description;
    private int section;
}