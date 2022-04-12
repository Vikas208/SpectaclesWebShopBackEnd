package com.example.SpectaclesWebShop.Bean;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private long order_id;
    private long c_id;
    private long service_id;
    private Double shipping_charges;
    private String order_status;
    private LocalDateTime localDateTime;
    private OrderAddress orderAddress;
    private OrderPayment orderPayment;
    private List<OrderedProducts> orderedProducts;
    private Service service;
    private Login login;

    public Order(long order_id, long c_id, long service_id, Double shipping_charges, String order_status,
            LocalDateTime localDateTime, OrderPayment orderPayment, OrderAddress orderAddress, Service service,
            List<OrderedProducts> orderedProducts,Login login) {
        this.order_id = order_id;
        this.c_id = c_id;
        this.service_id = service_id;
        this.shipping_charges = shipping_charges;
        this.order_status = order_status;
        this.localDateTime = localDateTime;
        this.orderAddress = orderAddress;
        this.orderPayment = orderPayment;
        this.service = service;
        this.orderedProducts = orderedProducts;
        this.login = login;
    }

    public Order() {

    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<OrderedProducts> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProducts> orderedProducts) {
        this.orderedProducts = orderedProducts;
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

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", c_id=" + c_id +
                ", service_id=" + service_id +
                ", shipping_charges=" + shipping_charges +
                ", order_status='" + order_status + '\'' +
                ", localDateTime=" + localDateTime +
                ", orderAddress=" + orderAddress +
                ", orderPayment=" + orderPayment +
                ", orderedProducts=" + orderedProducts +
                ", service=" + service +
                ", login=" + login +
                '}';
    }
}
