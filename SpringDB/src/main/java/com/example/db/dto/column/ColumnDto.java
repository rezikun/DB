package com.example.db.dto.column;

import com.example.db.database.entities.Column;
import com.example.db.database.entities.types.Mapper;
import com.example.db.entities.Column.ColumnEntity;
import com.example.db.entities.Table.TableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ColumnDto {
    private String name;
    private String type;

    @Deprecated
    public static Column toEntity(ColumnDto dto, boolean old) {
        return new Column(dto.getName(), Mapper.toType(dto.getType()));
    }

    public static ColumnDto fromEntity(Column column) {
        return new ColumnDto(column.getName(), column.getType().toString());
    }

    @Deprecated
    public static List<ColumnDto> fromEntities(List<Column> columns, boolean old) {
        return columns.stream()
                .map(ColumnDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Deprecated
    public static List<Column> toEntities(List<ColumnDto> dtos, boolean old) {
        return dtos.stream()
                .map(columnDto -> toEntity(columnDto, true))
                .collect(Collectors.toList());
    }

    public static ColumnDto fromEntity(ColumnEntity column) {
        return new ColumnDto(column.getName(), column.getType().toString());
    }

    public static List<ColumnDto> fromEntities(List<ColumnEntity> columns) {
        return columns.stream()
                .map(ColumnDto::fromEntity)
                .collect(Collectors.toList());
    }

    public static ColumnEntity toEntity(ColumnDto dto, TableEntity table) {
        ColumnEntity columnEntity = new ColumnEntity();
        columnEntity.setName(dto.getName());
        columnEntity.setName(dto.getType());
        columnEntity.setTable(table);
        return columnEntity;
    }

    public static List<ColumnEntity> toEntities(List<ColumnDto> dtos, TableEntity table) {
        return dtos.stream()
                .map(column -> toEntity(column, table))
                .collect(Collectors.toList());
    }

}
