package com.example.db.services;

import com.example.db.config.Config;
import com.example.db.database.entities.DataBase;
import com.example.db.database.helpers.StorageHelper;
import com.example.db.database.services.MainDBService;
import com.example.db.dto.column.ColumnDto;
import com.example.db.dto.database.DBCreationDto;
import com.example.db.dto.database.DBDto;
import com.example.db.dto.database.DBNameDto;
import com.example.db.exceptions.DataBaseAlreadyExists;
import com.example.db.exceptions.NotOpenDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DBService {

    private final MainDBService mainDBService;

    @Autowired
    public DBService(MainDBService mainDBService) {
        this.mainDBService = mainDBService;
    }

    public DBDto create(DBCreationDto dbCreationDto) throws DataBaseAlreadyExists {
        var db = mainDBService.createDB(dbCreationDto.getName());
        if (db.isEmpty()) {
            throw new DataBaseAlreadyExists(dbCreationDto.getName());
        }
        dbCreationDto.getTables()
                .forEach(tableCreationDto ->
                        mainDBService.createTable(tableCreationDto.getName(),
                                ColumnDto.toEntities(tableCreationDto.getColumns())));
        return DBDto.fromEntity(db.get());
    }

    public List<DBNameDto> getAll(){
        return mainDBService.getAllDataBasesNames().stream()
                .map(DBNameDto::new)
                .collect(Collectors.toList());
    }

    public DBDto getByName(String name) {
        return DBDto.fromEntity(mainDBService.getDBByName(name));
    }

    public void delete(String name) throws NotOpenDatabaseException{
        mainDBService.deleteDB(name);
    }

}
