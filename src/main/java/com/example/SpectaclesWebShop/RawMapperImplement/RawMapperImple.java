package com.example.SpectaclesWebShop.RawMapperImplement;

import com.example.SpectaclesWebShop.Bean.Login;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RawMapperImple implements RowMapper<Login> {

    public RawMapperImple(){

    }
    @Override
    public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
        Login l = new Login();
        l.setId(rs.getInt(1));
        l.setMailId(rs.getString(2));
        l.setName(rs.getString(3));
        l.setPassword(rs.getString(4));
        return l;
    }
}
