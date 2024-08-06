package com.lesson49.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int age;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Grooup grooup;

    @OneToOne
    @JoinColumn(name = "record_book_id")
    private RecordBook recordBook;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", grooup=" + grooup +
                ", recordBook=" + recordBook +
                '}';
    }
}

