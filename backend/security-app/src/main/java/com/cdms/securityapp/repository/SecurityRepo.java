package com.cdms.securityapp.repository;

import java.security.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepo extends JpaRepository<Security, String> {
}
