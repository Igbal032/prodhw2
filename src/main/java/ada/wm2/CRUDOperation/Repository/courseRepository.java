package ada.wm2.CRUDOperation.Repository;

import ada.wm2.CRUDOperation.Entity.Course;
import ada.wm2.CRUDOperation.Entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public  interface courseRepository extends CrudRepository <Course, Integer> {

//
//    @Query(value = "SELECT Student.courses, Course.id,Course.courseName from Course where Course.deletedDate is Null")
//    List<Course> selectedCourses();
@Query(value = "select * from Course",nativeQuery = true)
List<Course> getAllCourse();
}
