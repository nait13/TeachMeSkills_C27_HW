package com.lesson45.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Client {
    private int id;
    private String name;
    private List<Card> cards;
}
