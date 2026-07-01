package com.yvl.vorstu.controllers;

import com.yvl.vorstu.dto.teacherGroupAssignment.request.CreateTeacherGroupAssignmentRequest;
import com.yvl.vorstu.dto.teacherGroupAssignment.response.TeacherGroupAssignmentResponse;
import com.yvl.vorstu.services.TeacherGroupAssignmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("api/teacher-group-assignments")
@RequiredArgsConstructor
public class TeacherGroupAssignmentController {

    private final TeacherGroupAssignmentService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<TeacherGroupAssignmentResponse> getAssignments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getAssignments(PageRequest.of(page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public TeacherGroupAssignmentResponse getAssignmentById(@PathVariable Long id) {
        return service.getAssignmentById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherGroupAssignmentResponse createAssignment(@Valid @RequestBody CreateTeacherGroupAssignmentRequest request) {
        return service.createAssignment(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable Long id) {
        service.deleteAssignment(id);
    }
}
