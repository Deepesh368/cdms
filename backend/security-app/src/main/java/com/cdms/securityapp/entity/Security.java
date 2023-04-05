package com.cdms.securityapp.entity;

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
@Table(name="Security")

public class Security {
    @Id
    private String rollNum;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}
