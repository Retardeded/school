package com.school.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Teacher {

    @Id
    @Column
    @GeneratedValue
    private Integer id;

    private String name;

    @Column
    private String surname;

    @Column
    private Integer age;

    @Column
    private String email;

    @Column
    private String subject;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_teacher",
            joinColumns = @JoinColumn(name = "studentId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacherId",
                    referencedColumnName = "id"))
    private List<Student> students;

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", teacherStudents=" + students +
                '}';
    }

}