package com.school.demo.controller;

import com.school.demo.model.*;
import com.school.demo.service.SchoolService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/studentsAdd")
    public Student addStudent(@RequestBody Student student) {
        System.out.println(student);
        schoolService.addStudent(student);
        return student;
    }

    @PostMapping("/studentsAddTeacher")
    public Student addTeacherToStudent(@RequestBody StudentTeacherNames names) {
        return schoolService.addTeacherToStudent(names.getTeacherName(), names.getStudentName());
    }

    @DeleteMapping("/studentsDeleteTeacher")
    public Student removeTeacherFromStudent(@RequestBody StudentTeacherNames names) {
        return schoolService.removeTeacherFromStudent(names.getTeacherName(), names.getStudentName());
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        var studentsData = schoolService.getStudents();
        return studentsData;
    }

    @PostMapping("/teachersAdd")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        schoolService.addTeacher(teacher);
        return teacher;
    }

    @GetMapping("/teachersPage")
    public ResponseEntity<Page<Teacher>> getEmployees(SchoolPage schoolPage,
                                                      SchoolSearchCriteria schoolSearchCriteria){
        return new ResponseEntity<>(schoolService.getTeachers(schoolPage, schoolSearchCriteria),
                HttpStatus.OK);
    }

    @GetMapping("/teachers")
    public List<Teacher> getTeachers() {
        var teachersData = schoolService.getTeachers();
        return teachersData;
    }

    @PostMapping("/teachersAddStudent")
    public Teacher addStudentToTeacher(@RequestBody StudentTeacherNames names) {
        return schoolService.addStudentToTeacher(names.getTeacherName(), names.getStudentName());
    }

    @DeleteMapping("/teachersDeleteStudent")
    public Teacher removeStudentFromTeacher(@RequestBody StudentTeacherNames names) {
        return schoolService.removeStudentFromTeacher(names.getTeacherName(), names.getStudentName());
    }
}
