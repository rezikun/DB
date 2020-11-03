package com.example.db.dto.table;

import com.example.db.dto.row.RowDto;
import com.example.db.dto.column.ColumnDto;
import com.example.db.database.entities.Table;
import com.example.db.entities.Table.TableEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TableDto {
    private String name;
    private List<ColumnDto> columns;
    private List<RowDto> rows;

    @Deprecated
    public static TableDto fromEntity(Table table) {
        return TableDto.builder()
                .name(table.getName())
                .columns(ColumnDto.fromEntities(table.getColumns(),true))
                .rows(RowDto.fromEntities(table.getRows(), true))
                .build();
    }

    @Deprecated
    public static List<TableDto> fromEntities(List<Table> tables, boolean old) {
        return tables.stream()
                .map(TableDto::fromEntity)
                .collect(Collectors.toList());
    }


//    public static TableDto fromEntity(TableEntity table) {
//        return TableDto.builder()
//                .name(table.getName())
//                .columns(ColumnDto.fromEntities(table.getColumns()))
//                .rows(RowDto.fromEntities(table.getRows()))
//                .build();
//    }

//    public static List<TableDto> fromEntities(List<TableEntity> tables) {
//        return tables.stream()
//                .map(TableDto::fromEntity)
//                .collect(Collectors.toList());
//    }
}
