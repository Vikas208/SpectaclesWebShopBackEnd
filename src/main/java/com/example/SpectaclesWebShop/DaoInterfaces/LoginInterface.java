package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.Login;

import java.util.List;

public interface LoginInterface {
    int createDataBase();

    int SaveData(Login l);

    int deleteData(int id);

    int UpdatePassword(Login details);

    List<Login> getAllUser();

    Login findByMailId(String mailId);

    int ChangeName(Login l);
}
