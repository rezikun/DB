package com.example.db.controller;

import com.example.db.components.DBModelAssembler;
import com.example.db.dto.database.DBCreationDto;
import com.example.db.dto.database.DBDto;
import com.example.db.dto.database.DBNameDto;
import com.example.db.exceptions.DataBaseAlreadyExists;
import com.example.db.exceptions.NotOpenDatabaseException;
import com.example.db.services.DBService;
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
@RequestMapping("/rest/db") // + hateoas
public class DBControllerRest {
    private final DBService dbService;

    private final DBModelAssembler assembler;

    @Autowired
    public DBControllerRest(DBService dbService, DBModelAssembler assembler) {
        this.dbService = dbService;
        this.assembler = assembler;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<DBDto> post(@RequestBody DBCreationDto dbCreationDto) {
        try {
            return assembler.toModel(dbService.create(dbCreationDto));
        } catch (DataBaseAlreadyExists ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage()
            );
        }
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<DBNameDto>> all() {
        List<EntityModel<DBNameDto>> dbsNames =
                dbService.getAll().stream()
                        .map(dto -> EntityModel.of(dto,
                                linkTo(methodOn(DBControllerRest.class).get(dto.getName())).withSelfRel(),
                                linkTo(methodOn(DBControllerRest.class).all()).withRel("/all")))
                        .collect(Collectors.toList());
        return CollectionModel.of(dbsNames, linkTo(methodOn(DBControllerRest.class).all()).withSelfRel());
    }

    @GetMapping("/{name}")
    public EntityModel<DBDto> get(@PathVariable String name) {
        return assembler.toModel(dbService.getByName(name));
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
            dbService.delete(name);
    }
}
