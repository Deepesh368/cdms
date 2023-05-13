package com.cdms.gateway.service;

import com.cdms.gateway.controller.GatewayController;
import com.cdms.gateway.entity.Cred;
import com.cdms.gateway.entity.Order;
import com.cdms.gateway.entity.StudentDetails;
import com.cdms.gateway.repository.OrderRepo;
import com.cdms.gateway.repository.CredRepo;
import com.cdms.gateway.repository.StudentDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Component
public class GatewayService {

    private final OrderRepo orderRepo;
    private final CredRepo credRepo;
    private final StudentDetailsRepo studentDetailsRepo;

    public GatewayController.OrderBody saveOrder(GatewayController.OrderBody orderBody) {
        Order order = orderOf(orderBody);
        order.setDeliveryDate(LocalDate.now());
        order.setDeliveryTime(LocalTime.now());
        order = this.orderRepo.save(order);
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

    public GatewayController.StudentDetailsBody verifyLogin(String uname, String pwd) {
        ArrayList<Cred> sc = (ArrayList<Cred>) this.credRepo.findByRollNum(uname);

        if (sc.size()<1){
            return sdBodyOf(new StudentDetails("", "", ""), false);
        }

        if (Objects.equals(sc.get(0).getRollNum(), "admin")){
            return sdBodyOf(new StudentDetails("admin", "admin", "admin@mail"), true);
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

    private GatewayController.StudentDetailsBody sdBodyOf(StudentDetails sd, boolean loggedIn){
        return new GatewayController.StudentDetailsBody(sd.getRollNum(), sd.getName(), sd.getEmail(), loggedIn);
    }

    public void saveCreds(String uname, String pwd) {
        this.credRepo.save(new Cred(uname, pwd));
    }

    public ArrayList<Cred> getCreds(){
        return (ArrayList<Cred>) this.credRepo.findAll();
    }

    public String populateData() {
        this.credRepo.save(new Cred("admin", "admin"));

        this.credRepo.save(new Cred("imt1", "imt1"));
        this.studentDetailsRepo.save(new StudentDetails("imt1", "name1", "imt1@mail"));

        this.credRepo.save(new Cred("imt2", "imt2"));
        this.studentDetailsRepo.save(new StudentDetails("imt2", "name2", "imt2@mail"));

        this.credRepo.save(new Cred("imt3", "imt3"));
        this.studentDetailsRepo.save(new StudentDetails("imt3", "name3", "imt3@mail"));

        return "Completed data population.";
    }
}
