package com.cdms.gateway.repository;

import com.cdms.gateway.entity.StudentCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCredRepo extends JpaRepository<StudentCred, String> {
    List<StudentCred> findByRollNum(String rollNum);
}
