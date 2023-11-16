package com.nurse.school.repository.main;

import com.nurse.school.entity.Main;
import com.nurse.school.entity.Symp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SympRepository extends JpaRepository<Symp, Long>, MainRepositoryCustom{

}
