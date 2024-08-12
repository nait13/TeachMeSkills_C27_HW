package com.lesson52.controller;

import com.lesson52.dto.StudentDTO;
import com.lesson52.dto.StudentDetailsDTO;
import com.lesson52.entity.Group;
import com.lesson52.entity.Student;
import com.lesson52.exception.ResourceNotFoundException;
import com.lesson52.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/group/{title}")
    public ResponseEntity<?> getStudentsByGroup(@PathVariable(value = "title") String title) {
        List<StudentDTO> studentList = studentService.getStudentsByGroup(title);

        if (studentList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student in group " + title + " not found!");
        } else {
            return ResponseEntity.ok(studentList);
        }
    }

    @GetMapping("/unpaid")
    public ResponseEntity<?> getUnpaidStudents() {
        List<StudentDTO> studentList = studentService.getUnpaidStudents();

        if (studentList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        } else {
            return ResponseEntity.ok(studentList);
        }
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<?> getStudentByName(@PathVariable("name") String name) {
        List<StudentDTO> students = studentService.getStudentByName(name);

        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        } else {
            return ResponseEntity.ok(students);
        }
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<?> findStudentBySurname(@PathVariable("surname") String surname) {
        List<StudentDTO> studentDTOList = studentService.findStudentBySurname(surname);

        if (studentDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        } else {
            return ResponseEntity.ok(studentDTOList);
        }
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<?> updateStudent(@RequestBody StudentDetailsDTO studentDTO) {
        Student student = studentService.updateStudent(studentDTO);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return new ResponseEntity<>("Student id: " + studentDTO.getId() + " not found ", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") int studentId) {
        Boolean isDelete = studentService.deleteStudent(studentId);

        if (isDelete) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student id: " + studentId + " not found");
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = studentService.createStudent(studentDTO);
        if (student != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Student: " + student + " created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid group");
        }
    }


    @PostMapping("/{studentId}/transfer")
    public ResponseEntity<?> transferStudent(@PathVariable("studentId") int id, @RequestBody Group newGroup) {
        Student student = null;
        try{
            student = studentService.transferStudent(id,newGroup);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

}
