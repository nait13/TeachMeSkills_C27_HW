package com.lesson52.mapper;

import com.lesson52.dto.StudentDTO;
import com.lesson52.entity.Student;

public class StudentMapper {
    public static StudentDTO toDTO(Student student) {
        if (student == null) return null;

        return StudentDTO.builder()
                .name(student.getName())
                .surname(student.getSurname())
                .unPay(student.isUnPay())
                .group(student.getGroup())
                .build();
    }

    public static Student toEntity(StudentDTO studentDTO) {
        if (studentDTO == null) return null;

        Student student = new Student();

        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setUnPay(studentDTO.isUnPay());
        student.setGroup(studentDTO.getGroup());

        return student;
    }
}
