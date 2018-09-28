package handler;

import error.ErrorDetail;
import error.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import io.zipcoder.crudapp.exception.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired // Better then @Inject
    private MessageSource messageSource;


    // THIS IS REMOVED IN THE BOOK
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException
                                        rnfe, HttpServletRequest request) {
        ///////////////////// METHOD BODY BELOW ////////////////////////////////
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }



    // ON PAGE 87!!!
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException
                                         manve, HttpServletRequest request) {
        ///////////////////// METHOD BODY BELOW ////////////////////////////////

        ErrorDetail errorDetail = new ErrorDetail();
        //Populate errorDetail instance
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());

        String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
        if(requestPath == null) {
            requestPath = request.getRequestURI();
        }

        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail("Input validation failed"); //
        errorDetail.setDeveloperMessage(manve.getClass().getName());


        // Create Validation instances
        List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
        for(FieldError fe: fieldErrors) {

            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());

            if (validationErrorList == null) {
                validationErrorList = new ArrayList<ValidationError>();
                errorDetail.getErrors().put(fe.getField(),
                        validationErrorList);
            }// end of if statement

            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(messageSource.getMessage(fe, null));
            validationErrorList.add(validationError);

        }// end of for loop
        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);

    }



} // END OF CLASS
