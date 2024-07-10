package org.lesson44.controller;

import jakarta.validation.Valid;
import org.lesson44.dao.StudentDAO;
import org.lesson44.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentsController {
    private StudentDAO studentDAO;

    @GetMapping()
    public String getStudents(Model model) {
        model.addAttribute("students", studentDAO.index());
        return "students/index";
    }

    @GetMapping("/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        return "students/new";
    }

    @PostMapping("/new")
    public String createStudent(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "students/new";
        }else {
            boolean isSave = studentDAO.save(student);
            return isSave ? "redirect:/student" : "students/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit (@PathVariable("id") int id, Model model){
        Student student =  studentDAO.getStudent(id);

        if(student != null) {
            model.addAttribute("student", student);
            return "students/edit";
        }else {
            return "students/index";
        }
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult , @PathVariable("id") int id) {
        if (bindingResult.hasErrors()){
            return "students/edit";
        }else {
            boolean isUpdate = studentDAO.update(student,id);

            return isUpdate ? "redirect:/student" : "students/error";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        boolean isDelete = studentDAO.delete(id);
        return isDelete ? "redirect:/student" : "students/error";
    }

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
}
