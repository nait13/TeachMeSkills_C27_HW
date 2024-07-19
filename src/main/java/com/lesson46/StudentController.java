package com.lesson46;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Student Service", description = "Service to find Students")
@RestController
@RequestMapping("/students")
public class StudentController
{
    @GetMapping(value = "/test")
    public String testMethod()
    {
        return "test";
    }

    @GetMapping
    public List<Student> findStudents()
    {
        return new ArrayList<>();
    }

    @Operation(summary = "Get student by the ID.",
            description = "Test description for method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find the student",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content) })
    @GetMapping(value = "/{id}")
    public Student findStudent(@PathVariable @Parameter(description = "Description of id parameter") int id)
    {
        return new Student();
    }
}
