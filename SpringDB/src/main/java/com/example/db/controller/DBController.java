package com.example.db.controller;

import com.example.db.dto.column.ColumnDto;
import com.example.db.dto.database.DBCreationDto;
import com.example.db.dto.database.DBDto;
import com.example.db.dto.database.DBNameDto;
import com.example.db.database.entities.DataBase;
import com.example.db.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        return dbService.create(dbCreationDto);
    }

    @GetMapping("/all")
    public List<DBNameDto> all() {
        return dbService.getAll();
    }

    @GetMapping("/{name}")
    public DBDto get(@PathVariable String name) {
        return dbService.getByName(name);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
        dbService.delete(name);
    }
}
