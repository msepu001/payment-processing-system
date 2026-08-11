package com.example.payment;

public class Payment {
    private String id;
    private double amount;
    private String status;

    public Payment(String id, double amount, String status){
        this.id = id;
        this.amount = amount;
        this.status = status;
    }

    public String getId(){
        return id;
    }

    public double getAmount(){
        return amount;
    }

    public String getStatus(){
        return status;
    }
}
