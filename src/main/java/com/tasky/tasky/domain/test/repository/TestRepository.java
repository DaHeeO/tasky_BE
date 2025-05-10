package com.tasky.tasky.domain.test.repository;

import com.tasky.tasky.domain.test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findById(Long id);
}
