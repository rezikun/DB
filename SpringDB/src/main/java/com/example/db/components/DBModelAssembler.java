package com.example.db.components;

import com.example.db.controller.DBControllerRest;
import com.example.db.dto.database.DBDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DBModelAssembler implements RepresentationModelAssembler<DBDto, EntityModel<DBDto>> {
    @Override
    public EntityModel<DBDto> toModel(DBDto dbDto) {
        return EntityModel.of(dbDto,
                linkTo(methodOn(DBControllerRest.class).get(dbDto.getName())).withSelfRel(),
                linkTo(methodOn(DBControllerRest.class).all()).withRel("/all"));
    }
}
