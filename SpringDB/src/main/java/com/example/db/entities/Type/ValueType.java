package com.example.db.entities.Type.Base;

import com.example.db.entities.Column.ColumnEntity;
import com.example.db.entities.Row.RowEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="values")
public class ValueType {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="data")
    private String data;

    @Column(name="file");
    

    @ManyToOne
    @JoinColumn(name="row_id", nullable = false)
    private RowEntity row;

    @ManyToOne
    @JoinColumn(name="column_id", nullable = false)
    private ColumnEntity column;
}
