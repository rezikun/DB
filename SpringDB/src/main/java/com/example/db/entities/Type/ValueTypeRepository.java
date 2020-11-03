package com.example.db.entities.Type.Base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueTypeRepository extends JpaRepository<ValueType, Integer> {
}
