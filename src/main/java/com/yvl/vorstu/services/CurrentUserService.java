package com.yvl.vorstu.services;

import com.yvl.vorstu.entities.Role;
import com.yvl.vorstu.entities.Student;
import com.yvl.vorstu.entities.Teacher;
import com.yvl.vorstu.entities.User;
import com.yvl.vorstu.exception.StudentNotFoundException;
import com.yvl.vorstu.exception.TeacherNotFoundException;
import com.yvl.vorstu.repositories.StudentRepository;
import com.yvl.vorstu.repositories.TeacherRepository;
import com.yvl.vorstu.security.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public User getCurrentUser() {
        return ((UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }

    public Role getCurrentRole() {
        return getCurrentUser().getRole();
    }

    public Student getCurrentStudent() {
        User user = getCurrentUser();

        return studentRepository.findByUser(user)
                .orElseThrow(() -> new StudentNotFoundException(user.getId()));
    }

    public Teacher getCurrentTeacher() {
        User user = getCurrentUser();

        return teacherRepository.findByUser(user)
                .orElseThrow(() -> new TeacherNotFoundException(user.getId()));
    }
}
