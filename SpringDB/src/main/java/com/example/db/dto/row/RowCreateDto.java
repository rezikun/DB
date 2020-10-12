package com.example.db.dto.row;

import com.example.db.entities.types.Mapper;
import com.example.db.entities.types.Type;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RowCreateDto {
    private Map<String, String> row; //column name to value

}
