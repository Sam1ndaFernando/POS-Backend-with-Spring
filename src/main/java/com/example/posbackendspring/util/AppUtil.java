package com.example.posbackendspring.util;

import java.util.UUID;

public class AppUtil {

    public static String generateCustomerId(){
        return "customer" + UUID.randomUUID();
    }
    public static String generateItemId(){
        return "Item" + UUID.randomUUID();
    }
    public static String generateOrderId(){
        return "Order" + UUID.randomUUID();
    }
    public static String generateOrderDetailId(){
        return "OrderDetails-" + UUID.randomUUID();
    }
}
