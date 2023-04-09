package com.cdms.gateway.repository;

import com.cdms.gateway.entity.SecurityCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityCredRepo extends JpaRepository<SecurityCred, String> {
    List<SecurityCred> findBySecId(String secId);
}
