package com.example.techgo;

public class Trip {
    public boolean driverCancel;
    public String cost;
    public String partnerUid;
    public String paymentMethod;
    public Location_db from;
    public Location_db to;
    public boolean isActive;
    public String vehicleType;

    public Trip(boolean driverCancel,String cost,String partnerUid,String paymentMethod
    ,Location_db from,Location_db to,boolean isActive,String vehicleType){
        this.driverCancel = driverCancel;
        this.cost = cost;
        this.partnerUid = partnerUid;
        this.paymentMethod = paymentMethod;
        this.from = from;
        this.to = to;
        this.isActive = isActive;
        this.vehicleType = vehicleType;
    }
}
