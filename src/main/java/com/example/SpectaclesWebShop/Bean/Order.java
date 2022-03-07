package com.example.SpectaclesWebShop.Bean;

import java.time.LocalDateTime;
import java.util.Date;

public class Order {

    private long order_id;
    private long c_id;
    private long service_id;
    private Double shipping_charges;
    private String order_status;
    private LocalDateTime localDateTime;

    private OrderAddress orderAddress;
    private OrderPayment orderPayment;
    private Service service;

    public Order(long order_id, long c_id, long service_id, Double shipping_charges, String order_status,LocalDateTime localDateTime,OrderPayment orderPayment,OrderAddress orderAddress,Service service) {
        this.order_id = order_id;
        this.c_id = c_id;
        this.service_id = service_id;
        this.shipping_charges = shipping_charges;
        this.order_status = order_status;
        this.localDateTime = localDateTime;
        this.orderAddress = orderAddress;
        this.orderPayment=  orderPayment;
        this.service =service;
    }

    public Order() {

    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public OrderPayment getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(OrderPayment orderPayment) {
        this.orderPayment = orderPayment;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getC_id() {
        return c_id;
    }

    public void setC_id(long c_id) {
        this.c_id = c_id;
    }

    public long getService_id() {
        return service_id;
    }

    public void setService_id(long service_id) {
        this.service_id = service_id;
    }

    public Double getShipping_charges() {
        return shipping_charges;
    }

    public void setShipping_charges(Double shipping_charges) {
        this.shipping_charges = shipping_charges;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}

