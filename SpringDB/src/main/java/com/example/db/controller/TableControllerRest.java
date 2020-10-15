package com.example.db.controller;

import com.example.db.components.TableModelAssembler;
import com.example.db.dto.database.DBNameDto;
import com.example.db.dto.row.RowCreateDto;
import com.example.db.dto.table.TableCreationDto;
import com.example.db.dto.table.TableDto;
import com.example.db.exceptions.NotOpenDatabaseException;
import com.example.db.services.RowService;
import com.example.db.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rest/table")  // + hateoas
public class TableControllerRest {
    private final TableService tableService;
    private final RowService rowService;

    private final TableModelAssembler assembler;

    @Autowired
    public TableControllerRest(TableService tableService,
                               RowService rowService,
                               TableModelAssembler assembler) {
        this.tableService = tableService;
        this.rowService = rowService;
        this.assembler = assembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TableDto> post(@RequestBody TableCreationDto dto) throws NotOpenDatabaseException{
        return assembler.toModel(tableService.create(dto));
    }

    @GetMapping("/{name}")
    public EntityModel<TableDto> get(@PathVariable String name) throws NotOpenDatabaseException {
        return assembler.toModel(tableService.getOne(name));
    }

    @GetMapping("/all/{db}")
    public CollectionModel<EntityModel<String>> getAll(@PathVariable String db) throws NotOpenDatabaseException{
        List<EntityModel<String>> dbsNames =
                tableService.getAll(db).stream()
                        .map(name -> EntityModel.of(name,
                                linkTo(methodOn(DBControllerRest.class).get(name)).withSelfRel(),
                                linkTo(methodOn(DBControllerRest.class).all()).withRel("/all/"+db)))
                        .collect(Collectors.toList());
        return CollectionModel.of(dbsNames, linkTo(methodOn(DBControllerRest.class).all()).withSelfRel());
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) throws NotOpenDatabaseException {
        tableService.delete(name);
    }

    @PutMapping("/{name}/row")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TableDto> addRow(@PathVariable String name, @RequestBody RowCreateDto dto) throws NotOpenDatabaseException{
        rowService.addToTable(name, dto);
        return assembler.toModel(tableService.getOne(name));
    }

    @PutMapping("/{name}/row/{id}/update")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TableDto> updateRow(@PathVariable String name,
                                           @PathVariable Integer id,
                                           @RequestBody RowCreateDto dto) throws NotOpenDatabaseException{
        rowService.updateRow(name, id, dto);
        return assembler.toModel(tableService.getOne(name));
    }

    @PutMapping("/{name}/sort")
    public EntityModel<TableDto> getSorted(@PathVariable String name,
                                           @RequestParam(name = "column") String columnName)
            throws NotOpenDatabaseException {
        return assembler.toModel(tableService.sortByColumn(name, columnName));
    }

    @ResponseStatus(value=HttpStatus.FORBIDDEN,
            reason="You should choose database first")
    @ExceptionHandler(NotOpenDatabaseException.class)
    public void conflict() {
        // Nothing to do
    }
}
