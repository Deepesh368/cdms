package com.cdms.gateway.repository;

import com.cdms.gateway.entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDetailsRepo extends JpaRepository<StudentDetails, String> {
    List<StudentDetails> findByRollNum(String rollNum);
}
