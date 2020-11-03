package com.example.db.entities.Type;

import com.example.db.entities.Type.ValueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueTypeRepository extends JpaRepository<ValueType, Integer> {
    ValueType findByColumnIdAndRowId(Integer columnId, Integer rowId);
}
