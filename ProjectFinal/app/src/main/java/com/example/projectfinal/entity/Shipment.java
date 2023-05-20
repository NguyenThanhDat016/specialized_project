package com.example.projectfinal.entity;

public class Shipment {
    public int ID;
    public double WoC;
    public double TotalPrice;
    public int NoinDay;
    public String Date;
    public int TotalPig;
    public double Price;
    public String Status;

    public Shipment(int ID, double woC, double totalPrice, int noinDay, String date, int totalPig, double price, String status) {
        this.ID = ID;
        WoC = woC;
        TotalPrice = totalPrice;
        NoinDay = noinDay;
        Date = date;
        TotalPig = totalPig;
        Price = price;
        Status = status;
    }
}
