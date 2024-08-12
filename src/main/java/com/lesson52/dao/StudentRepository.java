package com.lesson52.dao;

import com.lesson52.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>
{
    List<Student> findByNameIgnoreCase(String name);
    List<Student> findStudentBySurnameIgnoreCase(String surname);
    List<Student> getStudentsByGroupIgnoreCaseTitle(String title);
    List<Student> findStudentByUnPayIsFalse();
}
