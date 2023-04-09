package com.cdms.securityapp.controller;

import com.cdms.securityapp.service.SecurityAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityAppController {
    private final SecurityAppService securityAppService;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    record FetchOrderBody(String rollNum){}

    record OnFetchOrderBody(ArrayList<OrderBody> orders){}

//    record CollectedOrderBody(String orderId, String collectedById){}

    public record Creds(String uname, String pwd){}

    @GetMapping("/fetchOrders")
    public HttpEntity<ArrayList<OrderBody>> fetchOrders(){
        // call gateway
        String url = "http://localhost:9090/gateway/fetchAllOrders";
        ResponseEntity<OnFetchOrderBody> re = this.restTemplate.getForEntity(url, OnFetchOrderBody.class);
        return new HttpEntity<ArrayList<OrderBody>>(Objects.requireNonNull(re.getBody()).orders);
    }

    @PostMapping("/addOrder")
    public boolean addOrder(@RequestBody OrderBody orderBody){
        // call gateway
        String url = "http://localhost:9090/gateway/addOrder";
        HttpEntity<OrderBody> entity = new HttpEntity<>(orderBody);
        ResponseEntity<Boolean> re = this.restTemplate.postForEntity(url, entity, Boolean.class);
        return Boolean.TRUE.equals(re.getBody());
    }

    @PostMapping("/collectedOrder")
    public boolean collectedOrder(@RequestBody OrderBody orderBody){
        // call gateway
        String url = "http://localhost:9090/gateway/collectedOrder";
        HttpEntity<OrderBody> entity = new HttpEntity<>(orderBody);
        ResponseEntity<Boolean> re = this.restTemplate.postForEntity(url, entity, Boolean.class);
        return Boolean.TRUE.equals(re.getBody());
    }
}
