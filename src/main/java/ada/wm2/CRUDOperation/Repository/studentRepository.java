package ada.wm2.CRUDOperation.Repository;

import ada.wm2.CRUDOperation.Entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface studentRepository extends CrudRepository <Student, Integer>{

    @Query(value = "select * from Student",nativeQuery = true)
    List<Student> getAllStudent();
}
