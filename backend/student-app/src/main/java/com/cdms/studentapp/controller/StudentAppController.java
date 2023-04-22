package com.cdms.studentapp.controller;

import com.cdms.studentapp.service.StudentAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
@CrossOrigin
public class StudentAppController {
    private final StudentAppService studentAppService;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record FetchOrderBody(String rollNum){}

    record OnFetchOrderBody(ArrayList<OrderBody> orders){}

    record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    public record Creds(String uname, String pwd){}

    public record StudentDetails(String rollNum, String name, String email, boolean loggedIn){}

    public record LoginResponse(String response, StudentDetails studentDetails){}

    @PostMapping("/login")
    public HttpEntity<String> login(@RequestBody Creds creds){
        String url = "http://localhost:9090/gateway/loginStudent";
        ResponseEntity<LoginResponse> re = this.restTemplate.postForEntity(url, creds, LoginResponse.class);
        if(Objects.requireNonNull(re.getBody()).response=="Verified"){
            boolean saved = this.studentAppService.saveDetails(re.getBody().studentDetails);
        }
        return new HttpEntity<>(Objects.requireNonNull(re.getBody()).response);
    }

    @GetMapping("/fetchOrders")
    public HttpEntity<ArrayList<OrderBody>> fetchOrders(){
        // call gateway
        HttpEntity<Boolean> he = checkLogin();
        if(Boolean.FALSE.equals(he.getBody())){
            return new HttpEntity<>(null);
        }

        FetchOrderBody fetchOrderBody = new FetchOrderBody(this.studentAppService.getRollNum());
        String url = "http://localhost:9090/gateway/fetchOrders";
        ResponseEntity<OnFetchOrderBody> re = this.restTemplate.postForEntity(url, fetchOrderBody, OnFetchOrderBody.class);
        return new HttpEntity<ArrayList<OrderBody>>(Objects.requireNonNull(re.getBody()).orders);
    }

    @GetMapping("/checkLogin")
    public HttpEntity<Boolean> checkLogin(){
        boolean loggedIn = this.studentAppService.checkLogin();
        return new HttpEntity<Boolean>(true);
    }
}
