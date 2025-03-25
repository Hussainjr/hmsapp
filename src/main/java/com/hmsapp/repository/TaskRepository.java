package com.hmsapp.repository;

import com.hmsapp.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Page<TaskEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

}