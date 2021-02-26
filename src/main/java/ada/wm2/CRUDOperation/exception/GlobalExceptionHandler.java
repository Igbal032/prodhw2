package ada.wm2.CRUDOperation.exception;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error/ErrorHtml");
        mv.addObject("excep",ex.getMessage());
        return mv;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleExceptionForWrongParam(Exception ex){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error/ErrorHtml");
        mv.addObject("excep",ex.getMessage());
        return mv;
    }
}
