package com.example.db.dto.database;

import com.example.db.dto.table.TableDto;
import com.example.db.database.entities.DataBase;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DBDto {
    private String name;
    private List<TableDto> tables;

    public static DBDto fromEntity(DataBase dataBase) {
        return DBDto.builder()
                .name(dataBase.getName())
                .tables(TableDto.fromEntities(new ArrayList<>(dataBase.getTables().values())))
                .build();
    }
}
