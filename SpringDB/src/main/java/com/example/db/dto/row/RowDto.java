package com.example.db.dto.row;

import com.example.db.database.entities.types.Type;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class RowDto {
    private List<String> row;

    @Deprecated
    public static List<RowDto> fromEntities(List<List<String>> rows, boolean old) {
        return rows.stream()
                .map(RowDto::new)
                .collect(Collectors.toList());
    }

    public static RowDto fromEntity(List<Type> row) {
        return new RowDto(row
                .stream()
                .map(Type::getData)
                .collect(Collectors.toList()));
    }

//    public static RowDto fromEntity(Row row) {
//        return new RowDto(
//                row.getRow().stream()
//                .map(BaseType::getData)
//                .collect(Collectors.toList())
//        );
//    }

//    public static List<RowDto> fromEntities(List<Row> rows) {
//        return rows.stream()
//                .map(RowDto::fromEntity)
//                .collect(Collectors.toList());
//    }
}
