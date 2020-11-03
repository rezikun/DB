package com.example.db.entities.Column;

import com.example.db.database.entities.types.TypeName;
import com.example.db.entities.Table.TableEntity;
import com.example.db.entities.Type.Base.BaseType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="columns")
public class ColumnData {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TypeName type;

    @OneToMany(mappedBy = "column")
    private List<BaseType> column;

    @ManyToOne
    @JoinColumn(name="table_id", nullable = false)
    private TableEntity table;
}
