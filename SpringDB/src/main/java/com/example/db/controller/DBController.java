package com.example.db.controller;

import com.example.db.dto.column.ColumnDto;
import com.example.db.dto.database.DBCreationDto;
import com.example.db.dto.database.DBDto;
import com.example.db.dto.database.DBEmptyCreationDto;
import com.example.db.entities.DataBase;
import com.example.db.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db")
public class DBController {
    private final DBService dbService;

    @Autowired
    public DBController(DBService dbService) {
        this.dbService = dbService;
    }

    @PostMapping()
    public DBDto post(@RequestBody DBCreationDto dbCreationDto) {
        DataBase db = dbService.createDB(dbCreationDto.getName());
        dbCreationDto.getTables()
                .forEach(tableCreationDto ->
                        dbService.createTable(tableCreationDto.getName(),
                                ColumnDto.toEntities(tableCreationDto.getColumns())));
        return DBDto.fromEntity(db);
    }

    @PostMapping("/empty")
    public DBDto post(@RequestBody DBEmptyCreationDto dto) {
        DataBase db = dbService.createDB(dto.getName());
        return DBDto.fromEntity(db);
    }


    @GetMapping("/{name}")
    public DBDto get(@PathVariable String name) {
        return DBDto.fromEntity(dbService.getDBByName(name));
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        dbService.deleteDB(name);
    }
}
