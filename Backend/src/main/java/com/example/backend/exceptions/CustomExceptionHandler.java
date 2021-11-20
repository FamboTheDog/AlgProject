package com.example.backend.exceptions;

import com.example.backend.db.User;
import com.example.backend.exceptions.errors.UserCredentialsError;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
@AllArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private UserRepository repo;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiResponseError error = new ApiResponseError("Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserCredentialsError.class)
    public final ResponseEntity<Object> handleUserCredentialsError(UserCredentialsError ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiResponseError error = new ApiResponseError(ex.getMessage(), details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        User user = (User) Objects.requireNonNull(ex.getTarget());
        System.out.println(user.getName());
        Optional<User> duplicateName = repo.findByName(user.getName());
        if (duplicateName.isPresent()) details.add("Username is already taken");

        ApiResponseError error = new ApiResponseError("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }


}
