package com.hmsapp.controller;

import com.hmsapp.entity.TaskEntity;
import com.hmsapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskEntity>> getTasks(@RequestParam (required = false) String name,
                                                     @RequestParam (defaultValue = "0") int page,
                                                     @RequestParam (defaultValue = "10") int size,
                                                     @RequestParam (defaultValue = "asc") String sortDir){

        Page<TaskEntity> tasks = (Page<TaskEntity>) taskService.getTasks(name, page, size, sortDir);
        return tasks.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tasks);
    }
}
