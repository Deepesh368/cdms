package com.cdms.gateway.service;

import com.cdms.gateway.controller.GatewayController;
import com.cdms.gateway.entity.Order;
import com.cdms.gateway.entity.SecurityCred;
import com.cdms.gateway.entity.StudentCred;
import com.cdms.gateway.repository.OrderRepo;
import com.cdms.gateway.repository.SecurityCredRepo;
import com.cdms.gateway.repository.StudentCredRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Component
public class GatewayService {

    private final OrderRepo orderRepo;
    private final StudentCredRepo studentCredRepo;
    private final SecurityCredRepo securityCredRepo;

    public void saveOrder(GatewayController.OrderBody orderBody) {
        this.orderRepo.save(orderOf(orderBody));
    }

    public ArrayList<GatewayController.OrderBody> fetchOrders(String id) {
        ArrayList<Order> orders = (ArrayList<Order>) this.orderRepo.findByOrderId(id);
        ArrayList<GatewayController.OrderBody> orderBodies = new ArrayList<>();

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

    public boolean verifyStudent(String uname, String pwd) {
        ArrayList<StudentCred> sc = (ArrayList<StudentCred>) this.studentCredRepo.findByRollNum(uname);

        if (sc.size()<1){
            return false;
        }

        return Objects.equals(sc.get(0).getPassword(), pwd);
    }

    public boolean verifySecurity(String uname, String pwd) {
        ArrayList<SecurityCred> sc = (ArrayList<SecurityCred>) this.securityCredRepo.findBySecId(uname);

        if (sc.size()<1){
            return false;
        }

        return Objects.equals(sc.get(0).getPassword(), pwd);
    }
}
