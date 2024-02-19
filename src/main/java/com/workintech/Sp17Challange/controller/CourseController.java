package com.workintech.Sp17Challange.controller;

import com.workintech.Sp17Challange.entity.*;
import com.workintech.Sp17Challange.exceptions.ApiException;
import com.workintech.Sp17Challange.validaditon.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses;
    private CourseGpa highCourseGpa;
    private CourseGpa mediumCourseGpa;
    private CourseGpa lowCourseGpa;
    @Autowired
    public CourseController(@Qualifier("highCourseGpa") CourseGpa highCourseGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumCourseGpa,
                            @Qualifier("lowCourseGpa")  CourseGpa lowCourseGpa) {
        this.highCourseGpa = highCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.lowCourseGpa = lowCourseGpa;
    }
    @PostConstruct
    public void init(){
        courses= new ArrayList<>();
    }
    @GetMapping
    public List<Course> getAllCourses(){
        return this.courses;
    }
    @GetMapping("/{name}")
    public Course getCourseById(@PathVariable("name") String name){
        CourseValidation.checkName(name);

        return courses.stream()
                        .filter(course-> course.getName().equalsIgnoreCase("name"))
                        .findFirst()
                        .orElseThrow(()-> new ApiException("Course not found with this name", HttpStatus.BAD_REQUEST));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody Course course){
        CourseValidation.checkName(course.getName());
        CourseValidation.checkCredit(course.getCredit());
        CourseValidation.checkCourseExistence(courses,course.getName());
        courses.add(course);
        Double totalGpa= getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(totalGpa,course);
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putCourse( @PathVariable("id") Integer id,@RequestBody Course course){
        Course existingCourse  = courses.stream().filter(c->c.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new ApiException("This id cannot be updated, because id not found!", HttpStatus.BAD_REQUEST));
        int existingCourseIndex= courses.indexOf(existingCourse);
        course.setId(id);
        courses.set(existingCourseIndex,course);
        Double totalGpa= getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(totalGpa,courses.get(existingCourseIndex) );
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
      Course existenceCourse=  courses.stream()
              .filter(c->c.getId().equals(id))
              .findFirst()
              .orElseThrow(()->new ApiException("there is no such id in courses: ", HttpStatus.BAD_REQUEST ));
      courses.remove(existenceCourse);

    }


    private double getTotalGpa(Course course){
        if(course.getCredit()<=2){
            return course.getGrade().getCoefficient()*course.getCredit()* lowCourseGpa.getGpa();
        } else if (course.getCredit()==3) {
            return course.getGrade().getCoefficient()*course.getCredit()*mediumCourseGpa.getGpa();
        }else{
            return course.getGrade().getCoefficient()*course.getCredit()*highCourseGpa.getGpa();
        }
    }

}
