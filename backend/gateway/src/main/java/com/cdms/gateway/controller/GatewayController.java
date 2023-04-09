package com.cdms.gateway.controller;

import com.cdms.gateway.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gateway")
public class GatewayController {

    private final GatewayService gatewayService;

    record FetchOrderBody(String id){}

    public record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    public record Creds(String uname, String pwd){}

    @PostMapping("/addOrder")
    public void addOrder(@RequestBody OrderBody orderBody){
        this.gatewayService.saveOrder(orderBody);
    }

    @PostMapping("/collectedOrder")
    public void collectedOrder(@RequestBody OrderBody orderBody){
        this.gatewayService.saveOrder(orderBody);
    }

    @PostMapping("/fetchOrders")
    public HttpEntity<ArrayList<OrderBody>> fetchOrders(@RequestBody FetchOrderBody fetchOrderBody){
        ArrayList<OrderBody> orders = this.gatewayService.fetchOrders(fetchOrderBody.id);
        HttpEntity<ArrayList<OrderBody>> ordersEntity = new HttpEntity<>(orders);
        return ordersEntity;
    }

    @GetMapping("/fetchAllOrders")
    public HttpEntity<ArrayList<OrderBody>> fetchAllOrders(){
        ArrayList<OrderBody> orders = this.gatewayService.fetchAllOrders();
        HttpEntity<ArrayList<OrderBody>> ordersEntity = new HttpEntity<>(orders);
        return ordersEntity;
    }

    @PostMapping("/loginStudent")
    public HttpEntity<String> loginStudent(@RequestBody Creds creds){
        boolean valid = this.gatewayService.verifyStudent(creds.uname, creds. pwd);
        if (valid){
            return new HttpEntity<String>("Verified");
        }
        return new HttpEntity<String>("Invalid credentials");
    }

    @PostMapping("/loginSecurity")
    public HttpEntity<String> loginSecurity(@RequestBody Creds creds){
        boolean valid = this.gatewayService.verifySecurity(creds.uname, creds. pwd);
        if (valid){
            return new HttpEntity<String>("Verified");
        }
        return new HttpEntity<String>("Invalid credentials");
    }
}
