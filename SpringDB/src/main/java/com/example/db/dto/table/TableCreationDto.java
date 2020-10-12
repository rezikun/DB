package com.example.db.dto.table;

import com.example.db.dto.column.ColumnDto;
import com.example.db.entities.Column;
import com.example.db.entities.Table;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TableCreationDto {
    private String name;
    private List<ColumnDto> columns;

    public static Table toEntity(TableCreationDto dto) {
        List<Column> columns = dto.getColumns()
                .stream()
                .map(ColumnDto::toEntity)
                .collect(Collectors.toList());
        return new Table(dto.getName(), columns);
    }
}
