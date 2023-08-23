package com.nurse.school.repository;

import com.nurse.school.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Medicine findMedicineByName(String name); // stock_name
}
