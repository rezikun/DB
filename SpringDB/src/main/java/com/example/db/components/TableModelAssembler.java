package com.example.db.components;

import com.example.db.controller.TableControllerRest;
import com.example.db.dto.table.TableDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TableModelAssembler implements RepresentationModelAssembler<TableDto, EntityModel<TableDto>> {
    @Override
    public EntityModel<TableDto> toModel(TableDto entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(TableControllerRest.class).get(entity.getName())).withSelfRel());
    }
}
