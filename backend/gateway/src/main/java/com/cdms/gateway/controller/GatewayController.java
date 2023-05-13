package com.cdms.gateway.controller;

import com.cdms.gateway.entity.Cred;
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

    public record CollectedOrderBody(String orderId, String collectedByRollNum){}

    public record Creds(String uname, String pwd){}

    public record CredsResponse(String response){}

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
    public HttpEntity<OrderBody> collectedOrder(@RequestBody CollectedOrderBody collectedOrderBody){
        OrderBody savedOrder = this.gatewayService.collectedOrder(collectedOrderBody.orderId, collectedOrderBody.collectedByRollNum);
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

    @PostMapping("/login")
    public HttpEntity<String> login(@RequestBody Creds creds){
        StudentDetailsBody sdb = this.gatewayService.verifyLogin(creds.uname, creds.pwd);

        if (sdb.loggedIn){
//            LoginResponse lr = new LoginResponse("Verified", sdb);
            return new HttpEntity<>("Verified");
        }

//        LoginResponse lr = new LoginResponse("Invalid Credentials", sdb);
        return new HttpEntity<>("Invalid Credentials");
    }

    @PostMapping("/addCredentials")
    public HttpEntity<CredsResponse> addCredentials(@RequestBody Creds creds){
        this.gatewayService.saveCreds(creds.uname, creds.pwd);
        return new HttpEntity<>(new CredsResponse("added"));
    }

    @GetMapping("/getCredentials")
    public HttpEntity<ArrayList<Cred>> getCredentials(){
        return new HttpEntity<>(this.gatewayService.getCreds());
    }

    @GetMapping("/populateData")
    public HttpEntity<String> populateData(){
        return new HttpEntity<>(this.gatewayService.populateData());
    }
}
