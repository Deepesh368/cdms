package com.cdms.securityapp.controller;

import com.cdms.securityapp.service.SecurityAppService;
import jakarta.persistence.criteria.Order;
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
@RequestMapping("/security")
@CrossOrigin
public class SecurityAppController {
    private final SecurityAppService securityAppService;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    record FetchOrderBody(String rollNum){}

    record OnFetchOrderBody(ArrayList<OrderBody> orders){}

//    record CollectedOrderBody(String orderId, String collectedById){}

    public record Creds(String uname, String pwd){}

    public record SecurityDetailsBody(String secId, boolean loggedIn){}

    public record SecLoginResponse(String response, SecurityDetailsBody securityDetailsBody){}

    @PostMapping("/login")
    public HttpEntity<String> login(@RequestBody Creds creds){
        String url = "http://localhost:9090/gateway/loginSecurity";
        ResponseEntity<SecLoginResponse> re = this.restTemplate.postForEntity(url, creds, SecLoginResponse.class);
//        return new HttpEntity<ArrayList<OrderBody>>(Objects.requireNonNull(re.getBody()).orders);
        return new HttpEntity<>(Objects.requireNonNull(re.getBody()).response);
    }

    @GetMapping("/fetchOrders")
    public HttpEntity<ArrayList<OrderBody>> fetchOrders(){
        // call gateway
        String url = "http://localhost:9090/gateway/fetchAllOrders";
        ResponseEntity<OnFetchOrderBody> re = this.restTemplate.getForEntity(url, OnFetchOrderBody.class);
        return new HttpEntity<ArrayList<OrderBody>>(Objects.requireNonNull(re.getBody()).orders);
    }

    @PostMapping("/addOrder")
    public HttpEntity<OrderBody> addOrder(@RequestBody OrderBody orderBody){
        // call gateway
        OrderBody newOrder = new OrderBody(orderBody.orderId, orderBody.rollNum, orderBody.deliveryFrom,
                LocalDate.now(), LocalTime.now(), null);
        String url = "http://localhost:9090/gateway/addOrder";
        HttpEntity<OrderBody> entity = new HttpEntity<>(newOrder);
        ResponseEntity<OrderBody> re = this.restTemplate.postForEntity(url, entity, OrderBody.class);
        return new HttpEntity<>(Objects.requireNonNull(re.getBody()));
    }

    @PostMapping("/collectedOrder")
    public HttpEntity<OrderBody> collectedOrder(@RequestBody OrderBody orderBody){
        // call gateway
        String url = "http://localhost:9090/gateway/collectedOrder";
        HttpEntity<OrderBody> entity = new HttpEntity<>(orderBody);
        ResponseEntity<OrderBody> re = this.restTemplate.postForEntity(url, entity, OrderBody.class);
        return new HttpEntity<>(Objects.requireNonNull(re.getBody()));
    }
}
