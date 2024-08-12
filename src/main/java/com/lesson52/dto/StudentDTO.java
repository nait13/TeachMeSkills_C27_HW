package com.lesson52.dto;


import com.lesson52.entity.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class StudentDTO {

    private String name;

    private String surname;

    private boolean unPay;

    private Group group;

}
