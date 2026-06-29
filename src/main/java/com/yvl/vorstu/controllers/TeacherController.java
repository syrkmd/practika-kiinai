package com.yvl.vorstu.controllers;

import com.yvl.vorstu.dto.teacher.request.CreateTeacherRequest;
import com.yvl.vorstu.dto.teacher.request.UpdateTeacherRequest;
import com.yvl.vorstu.dto.teacher.response.TeacherResponse;
import com.yvl.vorstu.services.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService service;

    @GetMapping
    public Page<TeacherResponse> getTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getTeachers(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public TeacherResponse getTeacherById(@PathVariable Long id) {
        return service.getTeacherById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponse createTeacher(@Valid @RequestBody CreateTeacherRequest request) {
        return service.createTeacher(request);
    }

    @PutMapping("/{id}")
    public TeacherResponse updateTeacher(@PathVariable Long id, @Valid @RequestBody UpdateTeacherRequest request) {
        return service.updateTeacher(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacherById(@PathVariable Long id) {
        service.deleteTeacherById(id);
    }
}
