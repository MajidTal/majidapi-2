package com.Majid.Board_API2.Response;

import lombok.Data;

import java.util.Map;

@Data
public class BoardResponse {
    private Long board_id;
    private String name;
    private Map<Integer, String> columns;
}