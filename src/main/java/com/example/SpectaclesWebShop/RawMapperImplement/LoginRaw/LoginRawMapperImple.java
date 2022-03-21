package com.example.SpectaclesWebShop.RawMapperImplement.LoginRaw;

import com.example.SpectaclesWebShop.Bean.Login;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRawMapperImple implements RowMapper<Login> {

    public LoginRawMapperImple(){

    }
    @Override
    public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
        Login l = new Login();
        l.setId(rs.getInt(1));
        l.setMailId(rs.getString(2));
        l.setName(rs.getString(3));
        l.setPassword(rs.getString(4));
        l.setHasRole(rs.getString(5));
        return l;
    }
}
