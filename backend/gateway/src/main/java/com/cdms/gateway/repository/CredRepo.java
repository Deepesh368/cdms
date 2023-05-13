package com.cdms.gateway.repository;

import com.cdms.gateway.entity.Cred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredRepo extends JpaRepository<Cred, String> {
    List<Cred> findByRollNum(String rollNum);
}
