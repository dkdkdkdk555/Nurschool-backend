package com.nurse.school.repository.medicine;

import com.nurse.school.entity.Medicine;
import com.nurse.school.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long>, MedicineRepositoryCustom {

    @Query("select m from Medicine m where m.medicine_name = :name and m.school.id = :schoolId")
    Medicine findMedicineByNameAndSchool(String name, Long schoolId); // stock_name

    @Modifying
    @Query("delete from Medicine m where m.id in :stock_id")
    int deleteMedicineByIds(@Param("stock_id") List<Long> stock_id);
}
