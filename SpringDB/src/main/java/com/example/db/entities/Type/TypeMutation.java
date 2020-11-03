package com.example.db.entities.Type;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeMutation implements GraphQLMutationResolver {
    private final TypeService service;

    @Autowired
    public TypeMutation(TypeService service) {
        this.service = service;
    }

    public ValueType setValue(Integer rowId, Integer columnId, String data) {
        return this.service.setData(rowId, columnId, data);
    }
}
