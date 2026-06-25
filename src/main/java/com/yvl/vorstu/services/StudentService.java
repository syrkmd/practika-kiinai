package com.yvl.vorstu.services;

import com.yvl.vorstu.entities.Student;
import com.yvl.vorstu.exception.StudentNotFoundException;
import com.yvl.vorstu.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(Long id) {
        return findStudent(id);
    }

    public List<Student> getStudentsByGroup(String group) {
        return repository.getByGroup(group);
    }

    public Student createStudent(Student student) {
        student.setId(null);
        return repository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        Student s = findStudent(id);

        s.setFio(student.getFio());
        s.setGroup(student.getGroup());
        s.setPhoneNumber(student.getPhoneNumber());

        return repository.save(s);
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
