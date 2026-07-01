package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.teacherGroupAssignment.request.CreateTeacherGroupAssignmentRequest;
import com.yvl.vorstu.dto.teacherGroupAssignment.response.TeacherGroupAssignmentResponse;
import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.entities.Teacher;
import com.yvl.vorstu.entities.TeacherGroupAssignment;
import com.yvl.vorstu.exception.GroupNotFoundException;
import com.yvl.vorstu.exception.TeacherGroupAssigmentNotFoundException;
import com.yvl.vorstu.exception.TeacherGroupAssigmentAlreadyExistsException;
import com.yvl.vorstu.exception.TeacherNotFoundException;
import com.yvl.vorstu.mapper.TeacherGroupAssignmentMapper;
import com.yvl.vorstu.repositories.StudentGroupRepository;
import com.yvl.vorstu.repositories.TeacherGroupAssignmentRepository;
import com.yvl.vorstu.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherGroupAssignmentService {

    private final TeacherGroupAssignmentRepository repository;
    private final TeacherRepository teacherRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final TeacherGroupAssignmentMapper mapper;

    public Page<TeacherGroupAssignmentResponse> getAssignments(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    public TeacherGroupAssignmentResponse getAssignmentById(Long id) {
        return mapper.toResponse(findAssignment(id));
    }

    public TeacherGroupAssignmentResponse createAssignment(CreateTeacherGroupAssignmentRequest request) {

        Teacher teacher = findTeacher(request.getTeacherId());
        StudentGroup group = findGroup(request.getGroupId());

        if (repository.existsByTeacherAndStudentGroup(teacher, group)) {
            throw new TeacherGroupAssigmentAlreadyExistsException(
                    teacher.getId(),
                    group.getId()
            );
        }

        TeacherGroupAssignment assignment = mapper.toEntity(request);

        assignment.setTeacher(teacher);
        assignment.setStudentGroup(group);

        TeacherGroupAssignment saved = repository.save(assignment);

        return mapper.toResponse(saved);
    }

    public void deleteAssignment(Long id) {
        repository.delete(findAssignment(id));
    }

    private TeacherGroupAssignment findAssignment(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TeacherGroupAssigmentNotFoundException(id));
    }

    private Teacher findTeacher(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    private StudentGroup findGroup(Long id) {
        return studentGroupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }

}
