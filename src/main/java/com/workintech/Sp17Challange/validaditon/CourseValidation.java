package com.workintech.Sp17Challange.validaditon;

import com.workintech.Sp17Challange.entity.Course;
import com.workintech.Sp17Challange.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class CourseValidation {
    public static void checkName(String name){
        if(name==null || name.isEmpty()){
            throw new ApiException("Name cannot be null or empty!", HttpStatus.BAD_REQUEST);
        }

    }
    public static void checkCredit(Integer credit){
        if(credit==null || credit<0 || credit>4 ){
            throw new ApiException("Credit should be between 0 and 4", HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkCourseExistence(List<Course> courses, String name){
        Optional<Course> courseOptional = courses.stream()
                .filter(c->c.getName().equalsIgnoreCase(name))
                .findAny();
        if(courseOptional.isPresent()){
            throw new ApiException("Course already exist!", HttpStatus.BAD_REQUEST);
        }
    }
}
