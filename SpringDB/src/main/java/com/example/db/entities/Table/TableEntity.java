package com.example.db.entities.Table;

import com.example.db.entities.Column.ColumnEntity;
import com.example.db.entities.Database.DatabaseEntity;
import com.example.db.entities.Row.RowEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="db_tables", schema = "public")
public class TableEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="table_name")
    private String name;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "table")
    private List<RowEntity> rows;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "table")
    private List<ColumnEntity> columns;

    @ManyToOne
    @JoinColumn(name="database_id", nullable = false)
    private DatabaseEntity database;
}
