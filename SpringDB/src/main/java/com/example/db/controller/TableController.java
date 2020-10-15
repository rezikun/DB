package com.example.db.controller;

import com.example.db.dto.row.RowCreateDto;
import com.example.db.dto.table.TableCreationDto;
import com.example.db.dto.table.TableDto;
import com.example.db.exceptions.NotOpenDatabaseException;
import com.example.db.services.RowService;
import com.example.db.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/table")
public class TableController {
    private final TableService tableService;
    private final RowService rowService;

    @Autowired
    public TableController(TableService tableService, RowService rowService) {
        this.tableService = tableService;
        this.rowService = rowService;
    }

    @PostMapping
    public TableDto post(@RequestBody TableCreationDto dto) {
        try {
            return tableService.create(dto);
        } catch (NotOpenDatabaseException ex) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, ex.getMessage()
            );
        }
    }

    @GetMapping("/{name}")
    public TableDto get(@PathVariable String name) {
        return tableService.getOne(name);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
        tableService.delete(name);
    }

    @PutMapping("/{name}/row")
    public TableDto addRow(@PathVariable String name, @RequestBody RowCreateDto dto) {
        rowService.addToTable(name, dto);
        return tableService.getOne(name);
    }

    @PutMapping("/{name}/row/{id}/update")
    public TableDto updateRow(@PathVariable String name, @PathVariable Integer id, @RequestBody RowCreateDto dto) {
        rowService.updateRow(name, id, dto);
        return tableService.getOne(name);
    }

    @PutMapping("/{name}/sort")
    public TableDto getSorted(@PathVariable String name, @RequestParam(name = "column") String columnName) {
        return tableService.sortByColumn(name, columnName);
    }

    @ResponseStatus(value=HttpStatus.FORBIDDEN,
            reason="You should choose database first")
    @ExceptionHandler(NotOpenDatabaseException.class)
    public void conflict() {
        // Nothing to do
    }

}
