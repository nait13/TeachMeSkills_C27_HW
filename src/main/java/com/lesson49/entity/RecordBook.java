package com.lesson49.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "record_book")
public class RecordBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rating")
    private int rating;

    @OneToOne(mappedBy = "recordBook")
    private Student student;

    @Override
    public String toString() {
        return "RecordBook{" +
                "id=" + id +
                ", rating=" + rating +
                '}';
    }
}
