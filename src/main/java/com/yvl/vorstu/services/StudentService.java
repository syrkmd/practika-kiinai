package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.student.request.CreateStudentRequest;
import com.yvl.vorstu.dto.student.request.UpdateStudentRequest;
import com.yvl.vorstu.dto.student.response.StudentResponse;
import com.yvl.vorstu.entities.Role;
import com.yvl.vorstu.entities.Student;
import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.entities.User;
import com.yvl.vorstu.exception.GroupNotFoundException;
import com.yvl.vorstu.exception.StudentNotFoundException;
import com.yvl.vorstu.mapper.StudentMapper;
import com.yvl.vorstu.repositories.StudentGroupRepository;
import com.yvl.vorstu.repositories.StudentRepository;
import com.yvl.vorstu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final StudentGroupRepository studentGroupRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentMapper mapper;

    public Page<StudentResponse> getStudents(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    public StudentResponse getStudentById(Long id) {
        return mapper.toResponse(findStudent(id));
    }

    public Page<StudentResponse> getStudentsByGroup(Pageable pageable, String group) {
        return repository.findByStudentGroupName(group, pageable)
                .map(mapper::toResponse);
    }

    public StudentResponse createStudent(CreateStudentRequest request) {

        StudentGroup group = findGroup(request.getGroupId());

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();

        userRepository.save(user);

        Student student = mapper.toEntity(request);

        student.setStudentGroup(group);
        student.setUser(user);

        Student saved = repository.save(student);

        return mapper.toResponse(saved);
    }

    public StudentResponse updateStudent(Long id, UpdateStudentRequest request) {

        Student student = findStudent(id);

        mapper.updateEntity(student, request);

        student.setStudentGroup(findGroup(request.getGroupId()));

        Student saved = repository.save(student);

        return mapper.toResponse(saved);
    }

    public void deleteStudentById(Long id) {
        repository.delete(findStudent(id));
    }

    private Student findStudent(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new StudentNotFoundException(id)
        );
    }

    private StudentGroup findGroup(Long id) {
        return studentGroupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }
}
