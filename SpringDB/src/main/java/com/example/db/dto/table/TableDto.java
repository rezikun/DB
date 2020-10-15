package com.example.db.dto.table;

import com.example.db.dto.row.RowDto;
import com.example.db.dto.column.ColumnDto;
import com.example.db.database.entities.Table;
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

    public static TableDto fromEntity(Table table) {
        return TableDto.builder()
                .name(table.getName())
                .columns(ColumnDto.fromEntities(table.getColumns()))
                .rows(RowDto.fromEntities(table.getRows()))
                .build();
    }

    public static List<TableDto> fromEntities(List<Table> tables) {
        return tables.stream()
                .map(TableDto::fromEntity)
                .collect(Collectors.toList());
    }
}
