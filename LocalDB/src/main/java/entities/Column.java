package entities;

import entities.types.Mapper;
import entities.types.TypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Column implements Serializable {
    private String name;
    private TypeName type;

    public void setType(String type) {
        this.type = Mapper.toType(type);
    }
}
