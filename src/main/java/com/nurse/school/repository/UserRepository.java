package com.nurse.school.repository;

import com.nurse.school.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByLoginIdAndPassword(String loginId, String password);

    User findUserByLoginId(String loginId);
}
