package com.example.db.entities.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseRepository extends JpaRepository<DatabaseEntity, Integer> {
    DatabaseEntity findByName(String name);

    void deleteByName(String name);
}
