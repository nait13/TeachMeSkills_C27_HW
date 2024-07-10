package org.lesson44.models;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Student {
    private int id;

    @NotBlank(message = "Name should not be empty")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{2,15}$", message = "The name must not contain the symbol")
    private String name;

    @NotBlank(message = "Last name should not be empty")
    @Size(min = 2,max = 30 , message = "Last name should be between 2 and 30 character")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{2,30}$", message = "The last name must not contain the symbol")
    private String surname;

    @NotBlank(message = "Not empty")
    @NotNull
    @Pattern(regexp = "^(https?|ftp):\\/\\/[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\/\\S*)?$" , message = "Not valid url")
    private String github;
}
