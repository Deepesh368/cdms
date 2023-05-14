package com.cdms.studentapp.controller;

import com.cdms.studentapp.service.StudentAppService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public static final Logger logger = LogManager.getLogger(StudentAppController.class);

    private final StudentAppService studentAppService;
    private final String gatewayIp = "http://192.168.49.2:30163";

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record FetchOrderBody(String rollNum){}

    record OnFetchOrderBody(ArrayList<OrderBody> orders){}

    public record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    public record Creds(String uname, String pwd){}

    public record StudentDetails(String rollNum, String name, String email, boolean loggedIn){}

    public record LoginResponse(String response, StudentDetails studentDetails){}

    @PostMapping("/login")
    public HttpEntity<String> login(@RequestBody Creds creds){
        String url = gatewayIp + "/gateway/loginStudent";
        ResponseEntity<LoginResponse> re = this.restTemplate.postForEntity(url, creds, LoginResponse.class);
        if(Objects.requireNonNull(re.getBody()).response=="Verified"){
            boolean saved = this.studentAppService.saveDetails(re.getBody().studentDetails);
        }
        return new HttpEntity<>(Objects.requireNonNull(re.getBody()).response);
    }

    @PostMapping("/fetchOrders")
    public HttpEntity<ArrayList<OrderBody>> fetchOrders(@RequestBody FetchOrderBody fetchOrderBody){
        // call gateway
//        HttpEntity<Boolean> he = checkLogin();
//        if(Boolean.FALSE.equals(he.getBody())){
//            return new HttpEntity<>(null);
//        }

//        FetchOrderBody fetchOrderBody = new FetchOrderBody(this.studentAppService.getRollNum());
        String url = gatewayIp + "/gateway/fetchOrders";
        logger.info("Request to fetch orders of: [" + fetchOrderBody.rollNum);
        ResponseEntity<OnFetchOrderBody> re = this.restTemplate.postForEntity(url, fetchOrderBody, OnFetchOrderBody.class);
        logger.info("Fetched orders of: [" + fetchOrderBody.rollNum + "], count: [" + Objects.requireNonNull(re.getBody()).orders.size() + "]");
        for(OrderBody orderBody: Objects.requireNonNull(re.getBody()).orders()){
            System.out.println(orderBody);
        }
        return new HttpEntity<>(Objects.requireNonNull(re.getBody()).orders);
    }

    @GetMapping("/checkLogin")
    public HttpEntity<Boolean> checkLogin(){
        boolean loggedIn = this.studentAppService.checkLogin();
        return new HttpEntity<Boolean>(true);
    }
}
