package com.example.db.services;

import com.example.db.database.services.MainDBService;
import com.example.db.dto.row.RowCreateDto;
import com.example.db.dto.row.RowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RowService {
    private final MainDBService mainDBService;

    @Autowired
    public RowService(MainDBService mainDBService) {
        this.mainDBService = mainDBService;
    }

    public RowDto addToTable(String tableName, RowCreateDto dto) {
        var row = mainDBService.addRowToTable(mainDBService.getTable(tableName), dto.getRow());
        return RowDto.fromEntity(row);
    }

    public RowDto updateRow(String tableName, Integer index, RowCreateDto dto) {
        var row = mainDBService.updateRow(mainDBService.getTable(tableName), index - 1, dto.getRow());
        return RowDto.fromEntity(row);
    }

}
