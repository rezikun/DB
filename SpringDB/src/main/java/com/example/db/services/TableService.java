package com.example.db.services;

import com.example.db.database.entities.Table;
import com.example.db.database.services.MainDBService;
import com.example.db.dto.column.ColumnDto;
import com.example.db.dto.table.TableCreationDto;
import com.example.db.dto.table.TableDto;
import com.example.db.exceptions.NotOpenDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private final MainDBService mainDBService;

    @Autowired
    public TableService(MainDBService mainDBService) {
        this.mainDBService = mainDBService;
    }

    public TableDto create(TableCreationDto dto) throws NotOpenDatabaseException {
        var table = mainDBService.createTable(dto.getName(), ColumnDto.toEntities(dto.getColumns()));
        return TableDto.fromEntity(table);
    }

    public TableDto getOne(String tableName) throws NotOpenDatabaseException {
        var table = mainDBService.getTable(tableName);
        return TableDto.fromEntity(table);
    }

    public List<String> getAll(String dbName) throws NotOpenDatabaseException{
        return mainDBService.getTablesByDB(dbName);
    }

    public TableDto sortByColumn(String tableName, String columnName) throws NotOpenDatabaseException {
        return TableDto.fromEntity(mainDBService.sortByColumn(tableName, columnName));
    }

    public void delete(String name) throws NotOpenDatabaseException {
        mainDBService.deleteTable(name);
    }
}
