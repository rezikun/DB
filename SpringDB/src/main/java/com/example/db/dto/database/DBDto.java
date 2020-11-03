package com.example.db.dto.database;

import com.example.db.dto.table.TableDto;
import com.example.db.database.entities.DataBase;
import com.example.db.entities.Database.DatabaseEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DBDto {
    private String name;
    private List<TableDto> tables;

    @Deprecated
    public static DBDto fromEntity(DataBase dataBase) {
        return DBDto.builder()
                .name(dataBase.getName())
                .tables(TableDto.fromEntities(new ArrayList<>(dataBase.getTables().values()), true))
                .build();
    }

//    public static DBDto fromEntity(DatabaseEntity database) {
//        return DBDto.builder()
//                .name(database.getName())
//                .tables(TableDto.fromEntities(database.getTables()))
//                .build();
//    }

//    public static List<DBDto> fromEntities(List<DatabaseEntity> databases) {
//        return databases.stream()
//                .map(DBDto::fromEntity)
//                .collect(Collectors.toList());
//    }
}
