package com.yvl.vorstu.config;

import com.yvl.vorstu.entities.Student;
import com.yvl.vorstu.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StudentService service;

    @Override
    public void run(String @NonNull ... args) {

        if (service.getAllStudents().isEmpty()) {
            service.createStudent(
                    Student.builder()
                            .fio("Ivan Ivanov")
                            .group("IFST-12")
                            .phoneNumber("88005553535")
                            .build()
            );

            service.createStudent(
                    Student.builder()
                            .fio("Petya Petin")
                            .group("IFST-33")
                            .phoneNumber("88005553535")
                            .build()
            );

            service.createStudent(
                    Student.builder()
                            .fio("Anna Annovna")
                            .group("IFST-32")
                            .phoneNumber("88005553535")
                            .build()
            );

            service.createStudent(
                    Student.builder()
                            .fio("Vasya Vasin")
                            .group("IFST-12")
                            .phoneNumber("88005553535")
                            .build()
            );
        }
    }
}
