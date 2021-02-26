package ada.wm2.CRUDOperation.controllers;

import ada.wm2.CRUDOperation.Entity.Course;
import ada.wm2.CRUDOperation.Entity.Student;
import ada.wm2.CRUDOperation.Repository.courseRepository;
import ada.wm2.CRUDOperation.exception.StudentAndCourseException;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    courseRepository courseRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    static Course keepCourse;
    @GetMapping("/courseList")
    public String showAllCourses(Model model){
        Iterable<Course> allCourses = courseRepository.findAll();
        model.addAttribute("courses",allCourses);
        return "Course/allCourses";
    }

    @GetMapping("/getCourse")
    public String editPage(Model model,@RequestParam(value = "id", required=false) Integer id) {
        if (id==null){
            model.addAttribute("emptyInputOrNotFound2","Kursun Kodun Daxil Edin");
            return "index";
        }
        else{
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()){
                keepCourse=course.get();
                model.addAttribute("singleCourse",course.get());
                logger.info("Course Finded");
                return "Course/simpleCourse";
            }
            else{
                model.addAttribute("emptyInputOrNotFound2","Kurs Tapılmadı");
                return "index";
            }
        }
    }

    @PostMapping("/updateSubmit")
    public String update(Model model,@Valid Course course, BindingResult bRes) {
    try{
        boolean checkCourse=false;
//        if (course.getId()==null||course.getCourseName().equals("")){
//            model.addAttribute("emptyInputt","Bütün xanaları doldurun");
//            model.addAttribute("singleCourse",course);
//            return "Course/simpleCourse";
//        }
        if (bRes.hasErrors()){

            model.addAttribute("singleCourse",course);
            return "Course/simpleCourse";
        }
        Iterable<Course> courses = courseRepository.findAll();
        for (Course cs:courses){
            if(cs.getId()==course.getId()){
                checkCourse=true;
                break;
            }
            else{
                checkCourse=false;
            }
        }
        if (checkCourse==false){
            courseRepository.delete(keepCourse);
            courseRepository.save(course);
            model.addAttribute("courses",courseRepository.findAll());
        }
        else {
            if (keepCourse.getId() == course.getId()) {
                courseRepository.save(course);
                model.addAttribute("courses",courseRepository.findAll());
            } else {
                model.addAttribute("singleCourse", course);
                model.addAttribute("emptyInputt", "Bu kodda Kurs var!!");
                return "Course/simpleCourse";
            }
        }
        return "Course/allCourses";
    }
    catch (Exception ex){
        model.addAttribute("singleCourse", course);
        model.addAttribute("emptyInputt", "Bu kursda tələbə var, kodu deyişmək qadağandı!!");
        return "Course/simpleCourse";
    }
    }

    @PostMapping("/addCourse")
    public String addCourse(Model model, @Valid Course newCourses, BindingResult bRes) {
        boolean existOrNot=false;
//        if (newCourses.getId()==null||newCourses.getCourseName().equals("")){
//            model.addAttribute("emptyInputs","Bütün xanaları doldurun");
//            return "Course/index";
//        }
        if (bRes.hasErrors()){
            model.addAttribute("emptyInputs","Bütün xanaları doldurun");
            model.addAttribute("course",newCourses);
            return "Course/index";
        }
        Iterable<Course> allCourses = courseRepository.findAll();
        for (Course cr : allCourses) {
            if (cr.getId()==newCourses.getId()){
                existOrNot=true;
                break;
            }
            else{
                existOrNot=false;
            }
        }
        if (existOrNot==true){
            model.addAttribute("singleCourse",newCourses);
            model.addAttribute("existCourse","Bu Kodda Kurs mövcuddur!!");
            return "Course/index";
        }
        else{
            courseRepository.save(newCourses);
            model.addAttribute("courses",courseRepository.findAll());
            return "Course/allCourses";
        }
    }

    @GetMapping("/deleteCourse")
    public String delete(Model model,@RequestParam Integer id) throws StudentAndCourseException {
        try {
            courseRepository.deleteById(id);
            Iterable<Course> allCourses = courseRepository.findAll();
            logger.debug("Deleted Course");
            model.addAttribute("courses",allCourses);
            return "Course/allCourses";
        }
        catch (Exception ex){
            throw new StudentAndCourseException("Bu kursda tələbə var. Silmək Qadağandı");
        }
    }
}
