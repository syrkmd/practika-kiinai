package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.teacher.request.CreateTeacherRequest;
import com.yvl.vorstu.dto.teacher.request.UpdateTeacherRequest;
import com.yvl.vorstu.dto.teacher.response.TeacherResponse;
import com.yvl.vorstu.entities.Role;
import com.yvl.vorstu.entities.Teacher;
import com.yvl.vorstu.entities.User;
import com.yvl.vorstu.exception.EmailAlreadyExistsException;
import com.yvl.vorstu.exception.TeacherNotFoundException;
import com.yvl.vorstu.exception.UsernameAlreadyExistsException;
import com.yvl.vorstu.mapper.TeacherMapper;
import com.yvl.vorstu.repositories.TeacherRepository;
import com.yvl.vorstu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherMapper mapper;

    public Page<TeacherResponse> getTeachers(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    public TeacherResponse getTeacherById(Long id) {
        return mapper.toResponse(findTeacher(id));
    }

    public TeacherResponse createTeacher(CreateTeacherRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.TEACHER)
                .build();

        userRepository.save(user);

        Teacher teacher = mapper.toEntity(request);

        teacher.setUser(user);

        Teacher saved = repository.save(teacher);

        return mapper.toResponse(saved);
    }

    public TeacherResponse updateTeacher(Long id, UpdateTeacherRequest request) {

        Teacher teacher = findTeacher(id);

        User user = teacher.getUser();

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        user.setEmail(request.getEmail());
        userRepository.save(user);

        mapper.updateEntity(teacher, request);

        Teacher saved = repository.save(teacher);

        return mapper.toResponse(saved);
    }

    public void deleteTeacherById(Long id) {
        Teacher teacher = findTeacher(id);
        userRepository.delete(teacher.getUser());
    }

    private Teacher findTeacher(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }
}
