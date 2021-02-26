package ada.wm2.CRUDOperation.exception;

public class StudentAndCourseException extends Exception {
     public StudentAndCourseException(String mes){
      super(mes);
     }
     @Override
    public String getMessage(){
         return "Error "+ super.getMessage();
     }
}
