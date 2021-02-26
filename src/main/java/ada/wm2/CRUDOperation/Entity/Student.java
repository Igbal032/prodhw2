package ada.wm2.CRUDOperation.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Student {
    @Id
    @NotNull
    @Positive
    private Integer Id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @ManyToMany
    @JoinTable(name = "ENROLLMENT",
            joinColumns = @JoinColumn(name = "stId"),
            inverseJoinColumns = @JoinColumn(name = "crs_Id"))
    List<Course> courses;
}
