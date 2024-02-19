package com.workintech.Sp17Challange.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class LowCourseGpa implements CourseGpa{
    @Override
    public int getGpa() {
        return 3;
    }
}
