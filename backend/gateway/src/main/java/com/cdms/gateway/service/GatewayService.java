package com.cdms.gateway.service;

import com.cdms.gateway.controller.GatewayController;
import com.cdms.gateway.entity.Order;
import com.cdms.gateway.entity.SecurityCred;
import com.cdms.gateway.entity.StudentCred;
import com.cdms.gateway.entity.StudentDetails;
import com.cdms.gateway.repository.OrderRepo;
import com.cdms.gateway.repository.SecurityCredRepo;
import com.cdms.gateway.repository.StudentCredRepo;
import com.cdms.gateway.repository.StudentDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Component
public class GatewayService {

    private final OrderRepo orderRepo;
    private final StudentCredRepo studentCredRepo;
    private final SecurityCredRepo securityCredRepo;
    private final StudentDetailsRepo studentDetailsRepo;

    public GatewayController.OrderBody saveOrder(GatewayController.OrderBody orderBody) {
        Order order = this.orderRepo.save(orderOf(orderBody));
        return orderBodyOf(order);
    }

    public GatewayController.OrderBody collectedOrder(String orderId, String collRollNum) {
        ArrayList<Order> orders = (ArrayList<Order>) this.orderRepo.findByOrderId(orderId);

        if (orders.size()==0){
            return orderBodyOf(blankOrder());
        }

        Order order = orders.get(0);
        order.setCollectedById(collRollNum);
        order = this.orderRepo.save(order);
        return orderBodyOf(order);
    }

    public ArrayList<GatewayController.OrderBody> fetchOrders(String rollNum) {
        ArrayList<Order> orders = (ArrayList<Order>) this.orderRepo.findByRollNum(rollNum);
        ArrayList<GatewayController.OrderBody> orderBodies = new ArrayList<>();

//        ArrayList<Order> orders = (ArrayList<Order>) this.orderRepo.findAll(Sort.by(Sort.Direction.DESC, "deliveryDate"));
//        orders.sort(Comparator.comparing(Order::getDeliveryTime));
//        Collections.reverse(orders);
//        orders.sort(Comparator.comparing(Order::getDeliveryDate));
//        Collections.reverse(orders);
//
        for(Order order: orders){
            orderBodies.add(orderBodyOf(order));
        }

        return orderBodies;
    }

    public ArrayList<GatewayController.OrderBody> fetchAllOrders() {
        ArrayList<Order> orders = (ArrayList<Order>) this.orderRepo.findAll(Sort.by(Sort.Direction.DESC, "deliveryDate"));
        orders.sort(Comparator.comparing(Order::getDeliveryTime));
        Collections.reverse(orders);
        orders.sort(Comparator.comparing(Order::getDeliveryDate));
        Collections.reverse(orders);

        ArrayList<GatewayController.OrderBody> orderBodies = new ArrayList<>();

        for(Order order: orders){
            orderBodies.add(orderBodyOf(order));
        }

        return orderBodies;
    }

    private GatewayController.OrderBody orderBodyOf(Order order) {
        return new GatewayController.OrderBody(order.getOrderId(), order.getRollNum(), order.getDeliveryFrom(), order.getDeliveryDate(), order.getDeliveryTime(), order.getCollectedById());
    }

    private Order orderOf(GatewayController.OrderBody orderBody){
        return new Order(orderBody.orderId(), orderBody.rollNum(), orderBody.deliveryFrom(), orderBody.deliveryDate(), orderBody.deliveryTime(), orderBody.collectedByRollNum());
    }

    private Order blankOrder(){
        return new Order(null, null, null, null, null, null);
    }

    public GatewayController.StudentDetailsBody verifyStudent(String uname, String pwd) {
        ArrayList<StudentCred> sc = (ArrayList<StudentCred>) this.studentCredRepo.findByRollNum(uname);

        if (sc.size()<1){
            return sdBodyOf(new StudentDetails("", "", ""), false);
        }

        if (Objects.equals(sc.get(0).getPassword(), pwd)){
            ArrayList<StudentDetails> sds = (ArrayList<StudentDetails>) this.studentDetailsRepo.findByRollNum(uname);
            if (sds.size()<1){
                return sdBodyOf(new StudentDetails("", "", ""), false);
            }
            return sdBodyOf(sds.get(0), true);
        }

        return sdBodyOf(new StudentDetails("", "", ""), false);
    }

    public GatewayController.SecurityDetailsBody verifySecurity(String uname, String pwd) {
        ArrayList<SecurityCred> sc = (ArrayList<SecurityCred>) this.securityCredRepo.findBySecId(uname);

        if (sc.size()<1){
            return new GatewayController.SecurityDetailsBody("Invalid Credentials", false);
        }

        if(Objects.equals(sc.get(0).getPassword(), pwd)){
            return new GatewayController.SecurityDetailsBody("Verified", true);
        }
        return new GatewayController.SecurityDetailsBody("Invalid Credentials", false);
    }

    private GatewayController.StudentDetailsBody sdBodyOf(StudentDetails sd, boolean loggedIn){
        return new GatewayController.StudentDetailsBody(sd.getRollNum(), sd.getName(), sd.getEmail(), loggedIn);
    }
}
