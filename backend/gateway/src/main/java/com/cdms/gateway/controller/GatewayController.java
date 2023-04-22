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

    record FetchOrderBody(String rollNum){}

    record OnFetchOrderBody(ArrayList<OrderBody> orders){}

    public record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    public record Creds(String uname, String pwd){}

    public record StudentDetailsBody(String rollNum, String name, String email, boolean loggedIn){}

    public record LoginResponse(String response, StudentDetailsBody studentDetailsBody){}

    @PostMapping("/addOrder")
    public HttpEntity<OrderBody> addOrder(@RequestBody OrderBody orderBody){
        OrderBody savedOrder = this.gatewayService.saveOrder(orderBody);
        return new HttpEntity<OrderBody>(savedOrder);
    }

    @PostMapping("/collectedOrder")
    public HttpEntity<OrderBody> collectedOrder(@RequestBody OrderBody orderBody){
        OrderBody savedOrder = this.gatewayService.saveOrder(orderBody);
        return new HttpEntity<OrderBody>(savedOrder);
    }

    @PostMapping("/fetchOrders")
    public HttpEntity<OnFetchOrderBody> fetchOrders(@RequestBody FetchOrderBody fetchOrderBody){
        ArrayList<OrderBody> orders = this.gatewayService.fetchOrders(fetchOrderBody.rollNum);
        OnFetchOrderBody ofob = new OnFetchOrderBody(orders);
        HttpEntity<OnFetchOrderBody> ordersEntity = new HttpEntity<>(ofob);
        return ordersEntity;
    }

    @GetMapping("/fetchAllOrders")
    public HttpEntity<OnFetchOrderBody> fetchAllOrders(){
        ArrayList<OrderBody> orders = this.gatewayService.fetchAllOrders();
        OnFetchOrderBody ofob = new OnFetchOrderBody(orders);
        HttpEntity<OnFetchOrderBody> ordersEntity = new HttpEntity<>(ofob);
        return ordersEntity;
    }

    @PostMapping("/loginStudent")
    public HttpEntity<LoginResponse> loginStudent(@RequestBody Creds creds){
        StudentDetailsBody sdb = this.gatewayService.verifyStudent(creds.uname, creds.pwd);

        if (sdb.loggedIn){
            LoginResponse lr = new LoginResponse("Verified", sdb);
            return new HttpEntity<>(lr);
        }

        LoginResponse lr = new LoginResponse("Invalid Credentials", sdb);
        return new HttpEntity<>(lr);
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
