package com.example.db.dto.table;

import com.example.db.dto.column.ColumnDto;
import com.example.db.database.entities.Column;
import com.example.db.database.entities.Table;
import com.example.db.dto.database.DBCreationDto;
import com.example.db.entities.Database.DatabaseEntity;
import com.example.db.entities.Table.TableEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TableCreationDto {
    private String name;
    private List<ColumnDto> columns;

    @Deprecated
    public static Table toEntity(TableCreationDto dto, boolean old) {
        List<Column> columns = dto.getColumns()
                .stream()
                .map(columnDto -> ColumnDto.toEntity(columnDto, true))
                .collect(Collectors.toList());
        return new Table(dto.getName(), columns);
    }

    public static TableEntity toEntity(TableCreationDto dto, DatabaseEntity db) {
        TableEntity table = new TableEntity();
        table.setName(dto.getName());
        table.setColumns(ColumnDto.toEntities(dto.getColumns(), table));
        table.setDatabase(db);
        return table;
    }

    public static List<TableEntity> toEntities(List<TableCreationDto> dtos, DatabaseEntity db) {
        return dtos.stream()
                .map(dto -> toEntity(dto, db))
                .collect(Collectors.toList());
    }
}
