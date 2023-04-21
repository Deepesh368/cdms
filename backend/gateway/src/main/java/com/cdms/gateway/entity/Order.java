package com.cdms.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
//@Data
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Orders")

public class Order {
    @Id
    String orderId;

    @Column
    String rollNum;

    @Column
    String deliveryFrom;

    @Column
    LocalDate deliveryDate;

    @Column
    LocalTime deliveryTime;

    @Column
    String collectedById;
}
