package com.example.db.entities.Database;

import com.example.db.entities.Table.TableEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="databases", schema = "public")
public class DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="db_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "database", fetch = FetchType.EAGER)
    private List<TableEntity> tables;
}
