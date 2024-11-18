package com.example.mykpp4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

class SalonDataTest {
    List<SalonUsersData> salonUsers;
    List<SalonServicesData> salonServicesData;
    List<SalonEntriesData> salonEntriesData;
    public void initialize(){
        SalonUsersData.initializeUsers();
        SalonServicesData.initializeServices();
        SalonEntriesData.initializeEntries();
        salonUsers = SalonUsersData.getAllUsers();
        salonServicesData = SalonServicesData.getAllServices();
        salonEntriesData = SalonEntriesData.getAllEntries();
    }
    public void AddTest(){
        initialize();
        int id = 4;
        String userName = "Olga";
        String surName = "Osadchuk";
        String thirdName = "Ivanivna";
        String phoneNum = "+380971234567";
        String gmail = "olga@gmail.com";
        SalonUsersData.addUser(id, userName, surName, thirdName, phoneNum, gmail);
        salonUsers = SalonUsersData.getAllUsers();
    }
}