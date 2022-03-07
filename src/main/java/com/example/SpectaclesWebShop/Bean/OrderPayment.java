package com.example.SpectaclesWebShop.Bean;

public class OrderPayment {
    private long id;
    private long order_id;
    private String payment_type;
    private boolean paymeny_status;
    private String transactionid;

    public OrderPayment(long id, long order_id, String payment_type, boolean paymeny_status, String transactionid) {
        this.id = id;
        this.order_id = order_id;
        this.payment_type = payment_type;
        this.paymeny_status = paymeny_status;
        this.transactionid = transactionid;
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

    public boolean isPaymeny_status() {
        return paymeny_status;
    }

    public void setPaymeny_status(boolean paymeny_status) {
        this.paymeny_status = paymeny_status;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }
}
