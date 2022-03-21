package com.example.SpectaclesWebShop.Bean;

public class OrderPayment {
    private long id;
    private long order_id;
    private String payment_type;
    private boolean payment_status;
    private String transactionid;
    private double total_amount;

    public OrderPayment(long id, long order_id, String payment_type, boolean payment_status, String transactionid,
            double total_amount) {
        this.id = id;
        this.order_id = order_id;
        this.payment_type = payment_type;
        this.payment_status = payment_status;
        this.transactionid = transactionid;
        this.total_amount = total_amount;
    }

    public OrderPayment() {
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public boolean isPayment_status() {
        return payment_status;
    }

    public void setPayment_status(boolean paymeny_status) {
        this.payment_status = paymeny_status;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }
}
