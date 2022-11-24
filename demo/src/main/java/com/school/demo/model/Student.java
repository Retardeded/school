package com.school.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Student {

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
    private String fieldOfStudy;

    @JsonIgnore
    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private List<Teacher> teachers = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                //", studentTeachers=" + teachers +
                '}';
    }
}