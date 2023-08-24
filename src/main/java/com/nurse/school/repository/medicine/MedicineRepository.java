package com.nurse.school.repository.medicine;

import com.nurse.school.entity.Medicine;
import com.nurse.school.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicineRepository extends JpaRepository<Medicine, Long>, MedicineRepositoryCustom {

    @Query("select m from Medicine m where m.name = :name and m.school.id = :schoolId")
    Medicine findMedicineByNameAndSchool(String name, Long schoolId); // stock_name

}
