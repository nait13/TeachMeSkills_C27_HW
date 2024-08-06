package com.lesson49.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "grooup")
public class Grooup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private int room;

    @OneToMany(mappedBy = "grooup" , fetch = FetchType.EAGER)
    private List<Student> students;

    @Override
    public String toString() {
        return "Grooup{" +
                "id=" + id +
                ", tittle='" + title + '\'' +
                ", room=" + room +
                ", students=" + students.size() +
                '}';
    }
}
