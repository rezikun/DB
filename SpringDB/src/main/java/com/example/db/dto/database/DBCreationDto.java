package com.example.db.dto.database;

import com.example.db.dto.table.TableCreationDto;
import com.example.db.entities.DataBase;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DBCreationDto {
    private String name;
    private List<TableCreationDto> tables;

    public static DataBase toEntity(DBCreationDto dto){
        DataBase db = new DataBase(dto.getName());
        dto.tables.forEach(table -> db.addTable(TableCreationDto.toEntity(table)));
        return db;
    }
}
