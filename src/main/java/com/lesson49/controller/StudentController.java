package com.lesson49.controller;

import com.lesson49.dao.StudentDAO;
import com.lesson49.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;

    @GetMapping
    public String enterGroupView(){
        return "students/enterGrooup";
    }
    @GetMapping("/top")
    public String getTopStudentInGroup(Model model){
        Map<String,List<Student>> topStudentsByGroup = studentDAO.getTopStudentsByGroup();
        model.addAttribute("topStudentsByGroup",topStudentsByGroup);
        model.addAttribute("title","Top 3 Students by Group");
        return "students/showStudent";
    }

    @GetMapping("/avg")
    public String getAvgStudent(Model model){
        Map<String,List<Student>> belowAverageStudents = studentDAO.getAveragePerformanceStudentInGroups();
        model.addAttribute("topStudentsByGroup",belowAverageStudents);
        model.addAttribute("title","Below average students");
        return "students/showStudent";
    }
    
    @GetMapping("/group")
    public String getGroup(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {

        String upTitle = title.toUpperCase();
        int pageSize = 3;
        boolean isAscending = false;
        if(sortOrder.equals("asc")){
               isAscending = true;
        }

        List<Student> students = studentDAO.getStudentsByGroup(upTitle, page, pageSize, isAscending);
        long totalStudents = studentDAO.getTotalStudentsCountByGroup(upTitle);

        int totalPages = (int) Math.ceil((double) totalStudents / pageSize);

        if (!students.isEmpty()) {
            String titleGroup  = students.get(0).getGrooup().getTitle();
            model.addAttribute("title", titleGroup);
        }

        model.addAttribute("students", students);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "students/index";
    }

}