package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<DeliveryPartner, List<Order>> hm=new HashMap<>();
    public void addOrderToDB(Order Order){
        List<Order> Orders;
        if(hm.size()==0){
            Orders = new ArrayList<>();
        }
        else{
            for(DeliveryPartner d:hm.keySet()){
                List<Order> x=hm.get(d);
                for(Order m:x){
                    if(m.getId().equals(Order.getId())) return;
                }
            }
            Orders = hm.get(null);
        }
        Orders.add(Order);
        hm.put(null, Orders);
    }

    public void addDeliveryPartnerToDB(String deliveryPartnerId){
        for(DeliveryPartner d:hm.keySet()){
            if(d.getId().equals(deliveryPartnerId)) return;
        }
        DeliveryPartner d=new DeliveryPartner(deliveryPartnerId);
        hm.put(d, new ArrayList<Order>());
    }

    public void addOrderDeliveryPartnerPairToDB(String OrderId, String DeliveryPartnerId){
        List<Order> x=null;
        DeliveryPartner dp=null;
        for(DeliveryPartner d:hm.keySet()) {
            if (d != null && d.getId().equals(DeliveryPartnerId)) {
                x = hm.get(d);
                dp=d;
                break;
            }
        }
        Order m1=null;
        List<Order> Orders=hm.get(null);
        for(Order m:Orders){
            if(m.getId().equals(OrderId)) {
                m1 = m;
                break;
            }
        }
        if(m1==null) return;
        if(x.contains(m1)) return;
        List<Order> y=hm.get(null);
        y.remove(m1);
        hm.put(null, y);
        if(hm.get(null).size()==0) hm.remove(null);
        if(!x.contains(m1)){
            x.add(m1);
            dp.setNumberOfOrders(dp.getNumberOfOrders()+1);
        }
        for(DeliveryPartner d:hm.keySet()){
            if (d != null && d.getId().equals(DeliveryPartnerId)) hm.put(d, x);
        }
    }

    public Order getOrderFromDB(String OrderId){
        for(DeliveryPartner d:hm.keySet()){
            List<Order> Orders=hm.get(d);
            for(Order m:Orders) if(m.getId().equals(OrderId)) return m;
        }
        return new Order(null, null);
    }

    public DeliveryPartner getDeliveryPartnerFromDB(String DeliveryPartnerId){
        for(DeliveryPartner d:hm.keySet()) if(d!=null && d.getId().equals(DeliveryPartnerId)) return d;
        return new DeliveryPartner(null);
    }

    public Integer getOrdersCountFromDB(String DeliveryPartnerId){
        Integer x=null;
        for(DeliveryPartner d:hm.keySet()){
            if(d!=null && d.getId().equals(DeliveryPartnerId)){
                 x=Integer.valueOf(d.getNumberOfOrders());
            }
        }
        return x;
    }

    public List<String> getOrdersListFromDB(String DeliveryPartnerId){
        List<String> res=new ArrayList<>();
        for(DeliveryPartner d:hm.keySet()){
            if(d!=null && d.getId().equals(DeliveryPartnerId)){
                List<Order> Orders=hm.get(d);
                for(Order m:Orders) res.add(m.getId());
                break;
            }
        }
        return res;
    }

    public List<String> getAllOrdersFromDB(){
        List<String> allOrders=new ArrayList<>();
        for(DeliveryPartner d:hm.keySet()){
            for(Order m:hm.get(d)) allOrders.add(m.getId());
        }
        return allOrders;
    }

    public Integer getCountAllUnassignedOrdersFromDB(){ return Integer.valueOf(hm.get(null).size());}

    public Integer getCountAllUndeliveredOrdersFromDB(String deliveryTime, String DeliveryPartnerId){
        Integer x=null;
        int time=Integer.parseInt(deliveryTime.substring(0,2))*60+Integer.parseInt(deliveryTime.substring(3));
        int c=0;
        for(DeliveryPartner d:hm.keySet()){
            if(d!=null && d.getId().equals(DeliveryPartnerId)){
                List<Order> Orders=hm.get(d);
                for(Order m:Orders) if(m.getDeliveryTime()>time) c++;
                break;
            }
        }
        x=Integer.valueOf(c);
        return x;
    }

    public String getLastDeliveryTime(String DeliveryPartnerId){
        int max=0;
        for(DeliveryPartner d:hm.keySet()){
            if(d!=null && d.getId().equals(DeliveryPartnerId)){
                List<Order> Orders=hm.get(d);
                for(Order m:Orders) if(m.getDeliveryTime()>max) max=m.getDeliveryTime();
                break;
            }
        }
        int h=max/60, m=max%60;
        String hr=(h<10)?"0"+h:String.valueOf(h);
        String min=(m<10)?"0"+m:String.valueOf(m);
        return hr+":"+min;
    }

    public void deleteDeliveryPartnerOrdersFromDB(String DeliveryPartnerId){
        if(hm.size()==0) return;
        for(DeliveryPartner d:new ArrayList<>(hm.keySet())){
            if(d!=null && d.getId().equals(DeliveryPartnerId)){
                List<Order> o1=hm.get(d);
                List<Order> o2=hm.get(null);
                for(Order m:o1) o2.add(m);
                hm.remove(d);
                hm.put(null, o2);
                break;
            }
        }
    }

    public void deleteOrderAndPartnerFromDB(String orderId){
        if(hm.size()==0) return;
        for(DeliveryPartner d:hm.keySet()){
            List<Order> Orders=hm.get(d);
            List<Order> dummy=new ArrayList<>(Orders);
            boolean f=false;
            for(Order m:dummy){
                if(m.getId().equals(orderId)){
                    f=true;
                    Orders.remove(m);
                    d.setNumberOfOrders(d.getNumberOfOrders()-1);
                    hm.put(d, Orders);
                }
            }
            if(f) break;
        }
    }
}
