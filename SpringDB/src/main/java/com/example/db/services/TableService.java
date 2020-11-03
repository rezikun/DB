package com.example.db.services;

import com.example.db.database.entities.Table;
import com.example.db.database.services.MainDBService;
import com.example.db.dto.column.ColumnDto;
import com.example.db.dto.row.RowDto;
import com.example.db.dto.table.TableCreationDto;
import com.example.db.dto.table.TableDto;
import com.example.db.exceptions.NotOpenDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Deprecated
public class TableService {

    private final MainDBService mainDBService;

    @Autowired
    public TableService(MainDBService mainDBService) {
        this.mainDBService = mainDBService;
    }

    public TableDto create(TableCreationDto dto) throws NotOpenDatabaseException {
        var table = mainDBService.createTable(dto.getName(), ColumnDto.toEntities(dto.getColumns(), true));
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

    public TableDto addColumn(String tableName, ColumnDto dto) {
        return TableDto.fromEntity(
                mainDBService.addColumnToTable(tableName, dto.getName(), dto.getType()));
    }

    public TableDto storeFileCell(String tableName, String columnName, Integer rowIndex, byte[] file) throws IOException {
        return TableDto.fromEntity(
                mainDBService.storeFileType(tableName, columnName, rowIndex - 1, file));
    }

    public void delete(String name) throws NotOpenDatabaseException {
        mainDBService.deleteTable(name);
    }
}
