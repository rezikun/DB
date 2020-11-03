package com.example.db.entities.Row;

import com.example.db.entities.Table.TableEntity;
import com.example.db.entities.Type.Base.BaseType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="rows")
public class Row {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "row")
    private List<BaseType> values;

    @ManyToOne
    @JoinColumn(name="table_id", nullable = false)
    private TableEntity table;
}
