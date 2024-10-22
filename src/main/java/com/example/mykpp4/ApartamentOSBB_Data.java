package com.example.mykpp4;

import java.util.Date;

public class ApartamentOSBB_Data {

    private Integer ID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;
    private String apartamentNum;
    private Date joinDate;
    private Double paymentAmouth;

    public ApartamentOSBB_Data(Integer ID,String firstName,String lastName,String email,String phoneNum, String apartamentNum, Date joinDate, Double paymentAmouth){
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.apartamentNum = apartamentNum;
        this.joinDate = joinDate;
        this.paymentAmouth = paymentAmouth;
    }

    public Integer getID() {
        return ID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getApartamentNum() {
        return apartamentNum;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public Date getJoinDate() {
        return joinDate;
    }
    public Double getPaymentAmouth() {
        return paymentAmouth;
    }
}
