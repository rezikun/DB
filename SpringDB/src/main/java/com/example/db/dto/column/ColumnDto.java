package com.example.db.dto.column;

import com.example.db.entities.Column;
import com.example.db.entities.types.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ColumnDto {
    private String name;
    private String type;

    public static Column toEntity(ColumnDto dto) {
        return new Column(dto.getName(), Mapper.toType(dto.getType()));
    }

    public static ColumnDto fromEntity(Column column) {
        return new ColumnDto(column.getName(), column.getType().toString());
    }

    public static List<ColumnDto> fromEntities(List<Column> columns) {
        return columns.stream()
                .map(ColumnDto::fromEntity)
                .collect(Collectors.toList());
    }

    public static List<Column> toEntities(List<ColumnDto> dtos) {
        return dtos.stream()
                .map(ColumnDto::toEntity)
                .collect(Collectors.toList());
    }
}
