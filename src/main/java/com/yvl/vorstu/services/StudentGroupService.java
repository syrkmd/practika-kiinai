package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.studentGroup.request.CreateStudentGroupRequest;
import com.yvl.vorstu.dto.studentGroup.request.UpdateStudentGroupRequest;
import com.yvl.vorstu.dto.studentGroup.response.StudentGroupResponse;
import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.exception.GroupAlreadyExistsException;
import com.yvl.vorstu.exception.GroupNotFoundException;
import com.yvl.vorstu.mapper.StudentGroupMapper;
import com.yvl.vorstu.repositories.StudentGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentGroupService {

    private final StudentGroupRepository repository;
    private final StudentGroupMapper mapper;

    public Page<StudentGroupResponse> getGroups(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    public StudentGroupResponse getGroupById(Long id) {
        return mapper.toResponse(findGroupById(id));
    }

    public StudentGroupResponse createGroup(CreateStudentGroupRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new GroupAlreadyExistsException(request.getName());
        }

        StudentGroup studentGroup = mapper.toEntity(request);

        StudentGroup saved = repository.save(studentGroup);

        return mapper.toResponse(saved);
    }

    public StudentGroupResponse updateGroup(Long id, UpdateStudentGroupRequest request) {

        StudentGroup studentGroup = findGroupById(id);

        mapper.updateEntity(studentGroup, request);

        StudentGroup saved = repository.save(studentGroup);

        return mapper.toResponse(saved);
    }

    public StudentGroup findGroupByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new GroupNotFoundException(name));
    }
    public void deleteGroupById(Long id) {
        repository.delete(findGroupById(id));
    }

    private StudentGroup findGroupById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }
}
