package com.nurse.school.repository.main;

import com.nurse.school.entity.Main;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainRepository extends JpaRepository<Main, Long>, MainRepositoryCustom {


}
