package com.lesson52.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString(exclude = "students")
@Entity
@Table(name = "grooup")
@JsonIgnoreProperties({"students"})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "room")
    private int room;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<Student> students;

}
