package com.cdms.studentapp.service;

import com.cdms.studentapp.entity.Student;
import com.cdms.studentapp.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Component
@RequiredArgsConstructor
public class StudentAppService {
    private final StudentRepo studentRepo;

    public String getRollNum() {
        String rollNum = this.studentRepo.findAll().get(0).getRollNum();
        return rollNum;
    }

    public boolean checkLogin() {
        ArrayList<Student> students = (ArrayList<Student>) this.studentRepo.findAll();
        if (students.size()<1){
            return false;
        }
        if (students.get(0).isLoggedIn()){
            return true;
        }
        return false;
    }
}
