package com.cdms.securityapp.repository;

import com.cdms.securityapp.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepo extends JpaRepository<Security, String> {
}
