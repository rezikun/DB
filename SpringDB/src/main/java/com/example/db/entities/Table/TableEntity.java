package com.example.db.entities.Table;

import com.example.db.database.entities.types.Type;
import com.example.db.entities.Column.ColumnData;
import com.example.db.entities.Database.Database;
import com.example.db.entities.Row.Row;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@javax.persistence.Table(name = "tables")
public class Table {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @OneToMany(mappedBy = "table")
    private List<Row> rows;

    @OneToMany(mappedBy = "table")
    private List<ColumnData> columns;

    @ManyToOne
    @JoinColumn(name="database_id", nullable = false)
    private Database database;
}
