package com.example.db.entities.Database;

import com.example.db.entities.DBList.DBList;
import com.example.db.entities.Table.TableEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Database {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @OneToMany(mappedBy = "database")
    private List<TableEntity> tables;

    @ManyToOne
    @JoinColumn(name="db_id", nullable = false)
    private DBList list;
}
