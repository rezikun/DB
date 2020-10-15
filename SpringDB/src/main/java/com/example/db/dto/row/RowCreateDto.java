package com.example.db.dto.row;

import lombok.Data;

import java.util.Map;

@Data
public class RowCreateDto {
    private Map<String, String> row; //column name to value
}
