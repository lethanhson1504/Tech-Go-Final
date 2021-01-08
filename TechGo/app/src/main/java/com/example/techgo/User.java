package com.example.techgo;

class User {
    public String userId;
    public String email;
    public String password;
    public String phoneNumber;

    public double lat;
    public double lng;
    public boolean isActive;
    public String displayname;
    public String usrType;
    public int wallet;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User( String email,String password, String phone
            ,String dpayname,String type,double lat,double lng) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phone;

        this.displayname = dpayname;
        this.lat = lat;
        this.lng = lng;
        this.usrType = type;
        isActive = false;
        wallet = 0;
    }

}



