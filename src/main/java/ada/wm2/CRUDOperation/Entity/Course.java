package ada.wm2.CRUDOperation.Entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @NotNull
    @Positive
    private Integer id;
    @NotBlank
    private String courseName;
    private Date deletedDate;
    @ManyToMany
    List<Student> students;
}
