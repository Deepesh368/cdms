package com.cdms.securityapp.controller;

import com.cdms.securityapp.service.SecurityAppService;
import jakarta.persistence.criteria.Order;
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
@RequestMapping("/security")
@CrossOrigin
public class SecurityAppController {

    public static final Logger logger = LogManager.getLogger(SecurityAppController.class);

    private final SecurityAppService securityAppService;
    private final String gatewayIp = "http://192.168.49.2:30163";

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record OrderBody(String orderId, String rollNum, String deliveryFrom, LocalDate deliveryDate, LocalTime deliveryTime, String collectedByRollNum){}

    public record CollectedOrderBody(String orderId, String collectedByRollNum){}

    record FetchOrderBody(String rollNum){}

    record OnFetchOrderBody(ArrayList<OrderBody> orders){}

//    record CollectedOrderBody(String orderId, String collectedById){}

    public record Creds(String uname, String pwd){}

    public record SecurityDetailsBody(String secId, boolean loggedIn){}

    public record SecLoginResponse(String response, SecurityDetailsBody securityDetailsBody){}

    @PostMapping("/login")
    public HttpEntity<String> login(@RequestBody Creds creds){
//        String url = "http://localhost:9090/gateway/loginSecurity";
        String url = gatewayIp + "/gateway/loginSecurity";
        ResponseEntity<SecLoginResponse> re = this.restTemplate.postForEntity(url, creds, SecLoginResponse.class);
//        return new HttpEntity<ArrayList<OrderBody>>(Objects.requireNonNull(re.getBody()).orders);
        return new HttpEntity<>(Objects.requireNonNull(re.getBody()).response);
    }

    @GetMapping("/fetchOrders")
    public HttpEntity<ArrayList<OrderBody>> fetchOrders(){
        // call gateway
//        String url = "http://localhost:9090/gateway/fetchAllOrders";
        String url = gatewayIp + "/gateway/fetchAllOrders";
        logger.info("Requesting to fetch all orders.");
        ResponseEntity<OnFetchOrderBody> re = this.restTemplate.getForEntity(url, OnFetchOrderBody.class);
        logger.info("Fetch all orders, count: [" + re.getBody().orders.size()  + "]");
        return new HttpEntity<ArrayList<OrderBody>>(Objects.requireNonNull(re.getBody()).orders);
    }

    @PostMapping("/addOrder")
    public HttpEntity<OrderBody> addOrder(@RequestBody OrderBody orderBody){
        // call gateway
        OrderBody newOrder = new OrderBody(orderBody.orderId, orderBody.rollNum, orderBody.deliveryFrom,
                LocalDate.now(), LocalTime.now(), null);
//        String url = "http://localhost:9090/gateway/addOrder";
        logger.info("Requesting to save order");
        String url = gatewayIp + "/gateway/addOrder";
        HttpEntity<OrderBody> entity = new HttpEntity<>(newOrder);
        ResponseEntity<OrderBody> re = this.restTemplate.postForEntity(url, entity, OrderBody.class);
        OrderBody savedOrder = re.getBody();
        logger.info("Saved order: [" + savedOrder.orderId + "], for: [" + savedOrder.rollNum);

        return new HttpEntity<>(Objects.requireNonNull(re.getBody()));
    }

    @PostMapping("/collectedOrder")
    public HttpEntity<OrderBody> collectedOrder(@RequestBody CollectedOrderBody collectedOrderBody){
        // call gateway
//        String url = "http://localhost:9090/gateway/collectedOrder";
        String url = gatewayIp + "/gateway/collectedOrder";
        HttpEntity<CollectedOrderBody> entity = new HttpEntity<>(collectedOrderBody);
        logger.info("Requesting to save collected order");
        ResponseEntity<OrderBody> re = this.restTemplate.postForEntity(url, entity, OrderBody.class);
        OrderBody savedOrder = re.getBody();
        logger.info("Collected order: [" + savedOrder.orderId + "], for: [" + savedOrder.rollNum + "], by: [" + savedOrder.collectedByRollNum + "]");

        return new HttpEntity<>(Objects.requireNonNull(re.getBody()));
    }
}
