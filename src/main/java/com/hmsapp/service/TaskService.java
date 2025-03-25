package com.hmsapp.service;

import com.hmsapp.entity.TaskEntity;
import com.hmsapp.repository.TaskRepository;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskEntity> getTasks(String name, int page, int size, String sortDir){

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC, "createdDate");
        Pageable pageable = (Pageable) PageRequest.of(page, size, sort);

        return (name == null || name.isEmpty()) ? (Page<TaskEntity>) taskRepository.findAll((Sort) pageable) :
                taskRepository.findByNameContainingIgnoreCase(name, pageable);


    }

}
