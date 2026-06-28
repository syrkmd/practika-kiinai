package com.yvl.vorstu.controllers;

import com.yvl.vorstu.dto.student.request.CreateStudentRequest;
import com.yvl.vorstu.dto.student.request.UpdateStudentRequest;
import com.yvl.vorstu.dto.student.response.StudentResponse;
import com.yvl.vorstu.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping
    public Page<StudentResponse> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getStudents(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(
            @PathVariable Long id
    ) {
        return service.getStudentById(id);
    }

    @GetMapping("/group")
    public Page<StudentResponse> getStudentByGroup(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam String group) {
        return service.getStudentsByGroup(PageRequest.of(page, size), group);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createStudent(@Valid @RequestBody CreateStudentRequest request) {
        return service.createStudent(request);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentRequest request) {
        return service.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentById(@PathVariable Long id) {
        service.deleteStudentById(id);
    }
}
