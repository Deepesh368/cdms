package com.cdms.studentapp.service;

import com.cdms.studentapp.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor
public class StudentAppService {
    private final StudentRepo studentRepo;
}
