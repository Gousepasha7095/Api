package Springboot.springboot.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String course;
    private boolean isDeleted = false;

    public Student(Long id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }
    public Student() {}

    public Long getId() {
        return id;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setCourse(String course){
        this.course=course;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
