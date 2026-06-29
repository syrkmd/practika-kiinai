package com.yvl.vorstu.controllers;

import com.yvl.vorstu.dto.teacher_group_assignment.request.CreateTeacherGroupAssignmentRequest;
import com.yvl.vorstu.dto.teacher_group_assignment.response.TeacherGroupAssignmentResponse;
import com.yvl.vorstu.services.TeacherGroupAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/teacher-group-assignments")
@RequiredArgsConstructor
public class TeacherGroupAssignmentController {

    private final TeacherGroupAssignmentService service;

    @GetMapping
    public Page<TeacherGroupAssignmentResponse> getAssignments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getAssignments(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public TeacherGroupAssignmentResponse getAssignmentById(@PathVariable Long id) {
        return service.getAssignmentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherGroupAssignmentResponse createAssignment(@Valid @RequestBody CreateTeacherGroupAssignmentRequest request) {
        return service.createAssignment(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable Long id) {
        service.deleteAssignment(id);
    }
}
