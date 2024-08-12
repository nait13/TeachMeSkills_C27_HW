package com.lesson52.service;

import com.lesson52.dao.GroupRepository;
import com.lesson52.dao.StudentRepository;
import com.lesson52.dto.StudentDTO;
import com.lesson52.dto.StudentDetailsDTO;
import com.lesson52.entity.Group;
import com.lesson52.entity.Student;
import com.lesson52.exception.ResourceNotFoundException;
import com.lesson52.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;


    public List<StudentDTO> getStudentsByGroup(String title) {
        List<Student> students = studentRepository.getStudentsByGroupIgnoreCaseTitle(title);
        List<StudentDTO> studentDTOList = students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
        return studentDTOList;
    }

    public List<StudentDTO> getUnpaidStudents() {
        List<Student> studentList = studentRepository.findStudentByUnPayIsFalse();

        List<StudentDTO> studentDTOList = studentList.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());

        return studentDTOList;
    }

    public List<StudentDTO> getStudentByName(String name) {
        List<Student> studentList = studentRepository.findByNameIgnoreCase(name);

        List<StudentDTO> studentDTOList = studentList.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());

        return studentDTOList;
    }

    public List<StudentDTO> findStudentBySurname(String surname) {
        List<Student> studentList = studentRepository.findStudentBySurnameIgnoreCase(surname);

        if (studentList.isEmpty()) {
            return Collections.emptyList();
        }
        List<StudentDTO> studentDTOList = studentList.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());

        return studentDTOList;
    }

    public Student updateStudent(StudentDetailsDTO studentDetailsDTO) {

        Optional<Student> optionalStudent = studentRepository.findById(studentDetailsDTO.getId());

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            student.setName(studentDetailsDTO.getName());
            student.setSurname(studentDetailsDTO.getSurname());
            student.setUnPay(studentDetailsDTO.isUnPay());

            studentRepository.save(student);
            return student;
        } else {
            return null;
        }
    }

    public boolean deleteStudent(int id) {
        boolean isExistsStudent = studentRepository.existsById(id);
        if (isExistsStudent) {
            studentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Student createStudent(StudentDTO studentDTO) {
        Student entityStudent = StudentMapper.toEntity(studentDTO);

        if (entityStudent.getGroup() != null) {
            Group group = groupRepository.findById(entityStudent.getGroup().getId()).orElse(null);
            if (group != null) {
                System.out.println("GROUP " +group);
                entityStudent.setGroup(group);
            }else {
                return null;
            }
        }
        studentRepository.save(entityStudent);

        return entityStudent;
    }

    public Student transferStudent(int id, Group group){
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student id: " + id + " not found!")
        );
        Group newGroup = groupRepository.findById(group.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Group not found")
        );

        student.setGroup(newGroup);
        studentRepository.save(student);
        return student;
    }
}
