package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void addOrderService(Order Order){
        orderRepository.addOrderToDB(Order);
    }

    public void addDeliveryPartnerService(String deliveryPartnerId){
        orderRepository.addDeliveryPartnerToDB(deliveryPartnerId);
    }

    public void addOrderDeliveryPartnerPairService(String OrderId, String DeliveryPartnerId){
        orderRepository.addOrderDeliveryPartnerPairToDB(OrderId, DeliveryPartnerId);
    }

    public Order getOrderService(String OrderId){
        return orderRepository.getOrderFromDB(OrderId);
    }

    public DeliveryPartner getDeliveryPartnerService(String DeliveryPartnerId){
        return orderRepository.getDeliveryPartnerFromDB(DeliveryPartnerId);
    }

    public int getOrdersCountService(String DeliveryPartnerId){
        return orderRepository.getOrdersCountFromDB(DeliveryPartnerId);
    }

    public List<String> getOrdersListService(String DeliveryPartnerId){
        return orderRepository.getOrdersListFromDB(DeliveryPartnerId);
    }

    public List<String> getAllOrdersService(){
        return orderRepository.getAllOrdersFromDB();
    }

    public Integer getCountAllUnassignedOrdersService(){
        return orderRepository.getCountAllUnassignedOrdersFromDB();
    }

    public Integer getCountAllUndeliveredOrdersService(String deliveryTime, String DeliveryPartnerId){
        return orderRepository.getCountAllUndeliveredOrdersFromDB(deliveryTime, DeliveryPartnerId);
    }

    public String getLastDeliveryTime(String DeliveryPartnerId){
        return orderRepository.getLastDeliveryTime(DeliveryPartnerId);
    }

    public void deleteDeliveryPartnerOrdersService(String DeliveryPartnerId){
        orderRepository.deleteDeliveryPartnerOrdersFromDB(DeliveryPartnerId);
    }

    public void deleteOrderAndPartnerService(String orderId){
        orderRepository.deleteOrderAndPartnerFromDB(orderId);
    }
}
