package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String customerName;
    private String phoneNumber;
    private List<OrderItem> orderItems;
    private static int orderCounter = 1;
    private final int orderId;

    public Order(String customerName, String phoneNumber) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber.replaceAll("[^0-9]", ""); // Normalize phone number
        if (this.phoneNumber.length() > 10) { // Trim if country code is included
            this.phoneNumber = this.phoneNumber.substring(this.phoneNumber.length() - 10);
        }
        this.orderItems = new ArrayList<>();
        this.orderId = orderCounter++;
    }

    public Order(Order other) {
        this.customerName = other.customerName;
        this.phoneNumber = other.phoneNumber;
        this.orderId = orderCounter++;
        this.orderItems = new ArrayList<>();
        for (OrderItem item : other.orderItems) {
            this.orderItems.add(new OrderItem(item)); // Deep copy each OrderItem
        }
    }

    public void addOrderItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null OrderItem.");
        }
        if (orderItems.size() >= 20) {
            throw new IllegalStateException("Cannot add more than 20 items to an order.");
        }
        orderItems.add(new OrderItem(item)); // Deep copy added
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (OrderItem item : orderItems) {
            totalPrice += item.calculateTotalPrice();
        }
        return totalPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderItem[] getOrderItems() {
        return orderItems.toArray(new OrderItem[0]);
    }

    public int getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\nCustomer: ").append(customerName)
                .append("\nPhone: ").append(phoneNumber);
        for (OrderItem item : orderItems) {
            sb.append("\n").append(item.toString());
        }
        sb.append("\nTotal Price: $").append(String.format("%.2f", calculateTotalPrice()));
        return sb.toString();
    }
}