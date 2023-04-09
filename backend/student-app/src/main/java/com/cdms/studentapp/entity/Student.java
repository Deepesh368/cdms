package com.cdms.studentapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Student")

public class Student {
    @Id
    private String rollNum;

    @Column
    private String name;

    @Column
    private String email;

    @Column(nullable = false)
    private boolean loggedIn;
}
