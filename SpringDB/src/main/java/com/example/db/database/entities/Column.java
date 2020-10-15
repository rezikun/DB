package com.example.db.database.entities;

import com.example.db.database.entities.types.TypeName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Column implements Serializable {
    private String name;
    private TypeName type;
}
