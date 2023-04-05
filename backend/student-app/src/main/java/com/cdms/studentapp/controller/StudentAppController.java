package com.cdms.studentapp.controller;

import com.cdms.studentapp.service.StudentAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentAppController {
    private final StudentAppService studentAppService;

    record FetchOrderBody(String rollNum){}

    record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    @PostMapping("/fetchOrders")
    public void fetchOrders(@RequestBody FetchOrderBody fetchOrderBody){
        // call gateway
    }
}
