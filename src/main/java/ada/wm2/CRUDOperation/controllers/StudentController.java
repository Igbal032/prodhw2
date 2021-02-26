package ada.wm2.CRUDOperation.controllers;
import ada.wm2.CRUDOperation.Entity.Course;
import ada.wm2.CRUDOperation.Entity.Student;
import ada.wm2.CRUDOperation.Repository.studentRepository;
import ada.wm2.CRUDOperation.Repository.courseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentController {
    courseRepository courseRepository;
    studentRepository studentRepository;
    static Student keepSt;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public StudentController(studentRepository sr,courseRepository cr){
       studentRepository=sr;
       courseRepository=cr;
   }

    @GetMapping("/index")
    public String index(Model model)
    {
        return "index";
    }
    @GetMapping("/course")
    public String coursePage(Model model) {
       model.addAttribute("course",new Course());
        return "Course/index";
    }


    @GetMapping("/studenPage")
    public String studetPg(Model model) {


        model.addAttribute("student",new Student());
        logger.info("You can add student from here");
        model.addAttribute("courses",courseRepository.findAll());
        return "Student/studenPage";
    }
    @GetMapping("/allStudents")
    public String studentList(Model model) {
        logger.debug("Entered student list method");
//        Iterable<Student> students = studentRepository.findAll();
        //using Query
        Iterable<Student> students = studentRepository.getAllStudent();
        model.addAttribute("students",students);
        logger.info("Get the List of students");
        return "Student/allStudents";
    }
    @PostMapping("/updateSubmit")
    public String update(Model model, @Valid Student student, BindingResult bindingResult) {
        logger.debug("Entered update student method");
       boolean checkStud=false;
//        if (student.getId()==null||student.getFirstName().equals("")||student.getLastName().equals("")){
//            model.addAttribute("courses",courseRepository.findAll());
//            model.addAttribute("emptyInputt","Bütün xanaları doldurun");
//            model.addAttribute("singleStudent",student);
//            return "Student/simpleStudent";
//        }
        if (bindingResult.hasErrors()){
            model.addAttribute("singleStudent",student);
            System.out.println("alindi");
            model.addAttribute("courses",courseRepository.findAll());
            logger.error("False input entry");
            return "Student/simpleStudent";
        }
        else{
            Iterable<Student> students = studentRepository.findAll();
            for (Student st:students) {
                if (st.getId()==student.getId()){
                    checkStud=true;
                    break;
                }
                else{
                    checkStud=false;
                }
            }
            if (checkStud==false){
                studentRepository.delete(keepSt);
                studentRepository.save(student);
                model.addAttribute("students",studentRepository.findAll());
            }
            else {
                if (keepSt.getId()==student.getId()){
                    studentRepository.save(student);
                    model.addAttribute("students",studentRepository.findAll());
                }
                else{
                    model.addAttribute("singleStudent",student);
                    model.addAttribute("emptyInputt","Bu kodda Tələbə var!!");
                    model.addAttribute("courses",courseRepository.findAll());
                    logger.info("Student updated");
                    return "Student/simpleStudent";
                }
            }
            return "Student/allStudents";
        }
    }
    @PostMapping("/addStudent")
    public String add(Model model, @Valid Student newStudent, BindingResult bindingResult) {
        logger.debug("Add  new student  method");
       boolean existOrNot=false;

        if (bindingResult.hasErrors()){
            model.addAttribute("singleStudent",newStudent);
            System.out.println("adding alindi");
            model.addAttribute("courses",courseRepository.findAll());
            logger.error("False entry for fields");
            return "Student/studenPage";
        }
//        if (newStudent.getId()==null||newStudent.getFirstName().equals("")||newStudent.getLastName().equals("")){
//            model.addAttribute("emptyInputs","Bütün xanaları doldurun");
//            model.addAttribute("student",newStudent);
//            model.addAttribute("courses",courseRepository.findAll());
//            return "Student/studenPage";
//        }
        Iterable<Student> students = studentRepository.findAll();
        for (Student student : students) {
            if (student.getId()==newStudent.getId()){
                existOrNot=true;
                break;
            }
            else{
                existOrNot=false;
            }
        }
        if (existOrNot==true){
            model.addAttribute("student",newStudent);
            model.addAttribute("existStudent","Bu Kodda Tələbə mövcuddur!!");
            model.addAttribute("courses",courseRepository.findAll());
            logger.error("Bu kodda telebe movcuddur");
            return "Student/studenPage";
        }
        else{
            studentRepository.save(newStudent);
            model.addAttribute("students",studentRepository.findAll());
            logger.info("New student added");
            return "Student/allStudents";
        }
    }
    @GetMapping("/deleteStudent")
    public String delStud(Model model,@RequestParam Integer id) {
        logger.debug("Entered student delete method");
        try{
            studentRepository.deleteById(id);
            Iterable<Student> students = studentRepository.findAll();
            model.addAttribute("students",students);
            logger.info("student deleted ");
            return "Student/allStudents";
        }
        catch (Exception ex){

            Iterable<Student> students = studentRepository.findAll();
            model.addAttribute("students",students);
            logger.error("student can not be deleted for some reasons");
            return "Student/allStudents";
        }
    }

    @GetMapping("/getStudent")
    public String searchStudent(Model model,@RequestParam(value = "id", required=false) Integer id) {
        if (id==null){
            System.out.println("success");
            model.addAttribute("emptyInputOrNotFound","Tələbənin Kodun Daxil Edin");
            return "index";
        }
        else{
            Optional<Student> student = studentRepository.findById(id);
            if (student.isPresent()){
                keepSt=student.get();
                model.addAttribute("singleStudent",student.get());
                model.addAttribute("courses",courseRepository.findAll());
                return "Student/simpleStudent";
            }
            else{
                model.addAttribute("emptyInputOrNotFound","Tələbə tapilmadı");
                return "index";
            }
        }
    }


}
