package com.example.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "error")
@AllArgsConstructor
@Getter
public class ApiResponseError {
    private String message;
    private List<String> errors;
}
