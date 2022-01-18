package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.Login;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface LoginInterface {
    int createDataBase();
    int SaveData(Login l);
    int deleteData(int id);
    int getUser(Login details);
    int UpdatePassword(Login details);
    List<Login> getAllUser();
    Login findByMailId(String mailId);
}
