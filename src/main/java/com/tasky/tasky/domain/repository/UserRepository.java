package com.tasky.tasky.domain.repository;

import com.tasky.tasky.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
