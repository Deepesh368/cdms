package com.cdms.gateway.controller;

import com.cdms.gateway.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gateway")
@CrossOrigin
public class GatewayController {

    private final GatewayService gatewayService;

    record FetchOrderBody(String rollNum){}

    record OnFetchOrderBody(ArrayList<OrderBody> orders){}

    public record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    public record Creds(String uname, String pwd){}

    public record StudentDetailsBody(String rollNum, String name, String email, boolean loggedIn){}

    public record SecurityDetailsBody(String secId, boolean loggedIn){}

    public record LoginResponse(String response, StudentDetailsBody studentDetailsBody){}

    public record SecLoginResponse(String response, SecurityDetailsBody securityDetailsBody){}

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
    public HttpEntity<SecLoginResponse> loginSecurity(@RequestBody Creds creds){
        SecurityDetailsBody sdb = this.gatewayService.verifySecurity(creds.uname, creds.pwd);

        if (sdb.loggedIn){
            SecLoginResponse lr = new SecLoginResponse("Verified", sdb);
            return new HttpEntity<>(lr);
        }

        SecLoginResponse lr = new SecLoginResponse("Invalid Credentials", sdb);
        return new HttpEntity<>(lr);
    }
}
