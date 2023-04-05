package com.cdms.securityapp.controller;

import com.cdms.securityapp.service.SecurityAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityAppController {
    private final SecurityAppService securityAppService;

    record FetchOrderBody(String rollNum){}

    record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

//    record CollectedOrderBody(String orderId, String collectedById){}

    @PostMapping("/fetchOrders")
    public void fetchOrders(@RequestBody FetchOrderBody fetchOrderBody){
        // call gateway
    }

    @PostMapping("/addOrder")
    public void addOrder(@RequestBody OrderBody orderBody){
        // call gateway
    }

    @PostMapping("/collectedOrder")
    public void collectedOrder(@RequestBody OrderBody orderBody){
        // call gateway
    }
}
