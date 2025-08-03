package Springboot.springboot.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class StudentDto {
    private Long id;
    private String name;
    private String course;

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
