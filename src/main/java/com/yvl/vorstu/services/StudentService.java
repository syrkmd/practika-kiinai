package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.student.request.CreateStudentRequest;
import com.yvl.vorstu.dto.student.request.UpdateStudentRequest;
import com.yvl.vorstu.dto.student.response.StudentResponse;
import com.yvl.vorstu.entities.Student;
import com.yvl.vorstu.exception.StudentNotFoundException;
import com.yvl.vorstu.mapper.StudentMapper;
import com.yvl.vorstu.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final StudentMapper mapper;

    public Page<StudentResponse> getStudents(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    public StudentResponse getStudentById(Long id) {
        return mapper.toResponse(findStudent(id));
    }

    public List<StudentResponse> getStudentsByGroup(String group) {
        return mapper.toResponseList(repository.getByGroup(group));
    }

    public StudentResponse createStudent(CreateStudentRequest request) {
        Student student = mapper.toEntity(request);

        Student saved = repository.save(student);

        return mapper.toResponse(saved);
    }

    public StudentResponse updateStudent(Long id, UpdateStudentRequest request) {
        Student student = findStudent(id);

        mapper.updateEntity(student, request);

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
}
