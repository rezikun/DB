package com.example.db.controller;

import com.example.db.dto.column.ColumnDto;
import com.example.db.dto.row.RowCreateDto;
import com.example.db.dto.row.RowDto;
import com.example.db.dto.table.TableCreationDto;
import com.example.db.dto.table.TableDto;
import com.example.db.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table")
public class TableController {
    private final DBService dbService;

    @Autowired
    public TableController(DBService dbService) {
        this.dbService = dbService;
    }

    @PostMapping
    public TableDto post(@RequestBody TableCreationDto dto) {
        var table = dbService.createTable(dto.getName(), ColumnDto.toEntities(dto.getColumns()));
        return TableDto.fromEntity(table);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        dbService.deleteTable(name);
    }

    @PutMapping("/{name}/row")
    public RowDto addRow(@PathVariable String name, @RequestBody RowCreateDto dto) {
        var row = dbService.addRowToTable(dbService.getTable(name), dto.getRow());
        return RowDto.fromEntity(row);
    }

    @GetMapping("/{name}")
    public TableDto get(@PathVariable String name) {
        return TableDto.fromEntity(dbService.getTable(name));
    }

    @PutMapping("/{name}")
    public TableDto getSorted(@PathVariable String name, @RequestParam(name = "column") String columnName) {
        return TableDto.fromEntity(dbService.sortByColumn(name, columnName));
    }

}
