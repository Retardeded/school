package com.school.demo.service;

import com.school.demo.model.Student;
import com.school.demo.model.Teacher;
import com.school.demo.model.SchoolPage;
import com.school.demo.model.SchoolSearchCriteria;
import com.school.demo.repository.StudentRepository;
import com.school.demo.repository.SchoolCriteriaRepository;
import com.school.demo.repository.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SchoolService {

    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;

    private SchoolCriteriaRepository schoolCriteriaRepository;

    public SchoolService(TeacherRepository teacherRepository, StudentRepository studentRepository, SchoolCriteriaRepository schoolCriteriaRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.schoolCriteriaRepository = schoolCriteriaRepository;
    }

    @Transactional
    public Student addStudent(Student student) {
        if(validateData(student.getName(), student.getSurname(), student.getAge(), student.getEmail())) {
            studentRepository.saveAndFlush(student);
        }

        var addedStudent = studentRepository.findByName(student.getName());
        System.out.println(student);
        return addedStudent.get();
    }
    @Transactional
    public Student addTeacherToStudent(String teacherName, String studentName) {
        Optional<Student> studentOptional = studentRepository.findByName(studentName);
        Optional<Teacher> teacherOptional = teacherRepository.findByName(teacherName);
        if(studentOptional.isPresent() && teacherOptional.isPresent()) {

            var student = studentOptional.get();
            var teacher = teacherOptional.get();
            List<Student> students = teacher.getStudents();
            students.add(student);
            teacherRepository.saveAndFlush(teacher);

            return student;
        }
        return null;
    }

    @Transactional
    public Student removeTeacherFromStudent(String teacherName, String studentName) {
        Optional<Student> studentOptional = studentRepository.findByName(studentName);
        Optional<Teacher> teacherOptional = teacherRepository.findByName(teacherName);
        if(studentOptional.isPresent() && teacherOptional.isPresent()) {

            var student = studentOptional.get();
            var teacher = teacherOptional.get();
            List<Student> students = teacher.getStudents();
            students.remove(student);
            teacherRepository.saveAndFlush(teacher);

            return student;
        }
        return null;
    }

    public List<Student> getStudents() {
        var students = studentRepository.findAll();

        return students;
    }
    @Transactional
    public Teacher addTeacher(Teacher teacher) {
        if(validateData(teacher.getName(), teacher.getSurname(), teacher.getAge(), teacher.getEmail())) {
            teacherRepository.saveAndFlush(teacher);
        }

        var addedTeacher = teacherRepository.findByName(teacher.getName());
        System.out.println(addedTeacher);
        return addedTeacher.get();
    }
    @Transactional
    public Teacher addStudentToTeacher(String teacherName, String studentName) {
        Optional<Student> studentOptional = studentRepository.findByName(studentName);
        Optional<Teacher> teacherOptional = teacherRepository.findByName(teacherName);
        if(studentOptional.isPresent() && teacherOptional.isPresent()) {

            var student = studentOptional.get();
            var teacher = teacherOptional.get();
            List<Student> students = teacher.getStudents();
            students.add(student);
            teacherRepository.saveAndFlush(teacher);

            return teacher;
        }
        return null;
    }

    @Transactional
    public Teacher removeStudentFromTeacher(String teacherName, String studentName) {
        Optional<Student> studentOptional = studentRepository.findByName(studentName);
        Optional<Teacher> teacherOptional = teacherRepository.findByName(teacherName);
        if(studentOptional.isPresent() && teacherOptional.isPresent()) {

            var student = studentOptional.get();
            var teacher = teacherOptional.get();
            List<Student> students = teacher.getStudents();
            students.remove(student);
            teacherRepository.saveAndFlush(teacher);

            return teacher;
        }
        return null;
    }

    public List<Teacher> getTeachers() {
        var teachers = teacherRepository.findAll();
        return teachers;
    }

    public Page<Teacher> getTeachers(SchoolPage schoolPage,
                                     SchoolSearchCriteria schoolSearchCriteria){
        return schoolCriteriaRepository.findAllTeachersWithFilters(schoolPage, schoolSearchCriteria);
    }

    public static boolean validateData(String name, String surname, Integer age, String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        if(!patternMatches(email, regexPattern)) {
            return false;
        }
        if(!(name.length() > 2)) {
            return false;
        }
        if(!(surname.length() > 2)) {
            return false;
        }
        if(!(age > 18)) {
            return false;
        }

            return true;
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
