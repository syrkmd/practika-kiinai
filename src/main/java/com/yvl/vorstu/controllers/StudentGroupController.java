package com.yvl.vorstu.controllers;

import com.yvl.vorstu.dto.student_group.request.CreateStudentGroupRequest;
import com.yvl.vorstu.dto.student_group.request.UpdateStudentGroupRequest;
import com.yvl.vorstu.dto.student_group.response.StudentGroupResponse;
import com.yvl.vorstu.services.StudentGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/groups")
@RequiredArgsConstructor
public class StudentGroupController {

    private final StudentGroupService service;

    @GetMapping
    public Page<StudentGroupResponse> getGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getGroups(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public StudentGroupResponse getGroupById(@PathVariable Long id) {
        return service.getGroupById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentGroupResponse createGroup(@Valid @RequestBody CreateStudentGroupRequest request) {
        return service.createGroup(request);
    }

    @PutMapping("/{id}")
    public StudentGroupResponse updateGroup(@PathVariable Long id, @Valid @RequestBody UpdateStudentGroupRequest request) {
        return service.updateGroup(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroupById(@PathVariable Long id) {
        service.deleteGroupById(id);
    }
}
