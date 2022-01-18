package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.CodeName.Code;
import com.example.SpectaclesWebShop.DaoInterfaces.LoginInterface;
import com.example.SpectaclesWebShop.CodeName.TableName;
import com.example.SpectaclesWebShop.RawMapperImplement.RawMapperImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginDao implements LoginInterface {
    @Autowired
    JdbcTemplate jdbcTemplate;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    LoginDao() {
        // default Constructor
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public int createDataBase() {
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + TableName.LOGIN_TABLE
                    + " (ID INT AUTO_INCREMENT PRIMARY KEY,MAILID VARCHAR(100) UNIQUE NOT NULL,NAME VARCHAR(100) NOT NULL,PASSWORD VARCHAR(200) NOT NULL);";
            int res = jdbcTemplate.update(query);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    // REGISTER USER
    @Override
    public int SaveData(Login l) {
        try {
            String query = "INSERT INTO " + TableName.LOGIN_TABLE + " (MAILID,NAME,PASSWORD) VALUES(?,?,?);";
            String password = bCryptPasswordEncoder.encode(l.getPassword());
            int row = jdbcTemplate.update(query, l.getMailId(), l.getName(), password);
            return row;
        } catch (DuplicateKeyException e) {
            return Code.DUPLICATE_KEY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    @Override
    public int deleteData(int id) {
        try {
            String query = "DELETE FROM " + TableName.LOGIN_TABLE + " WHERE ID=?";
            int res = jdbcTemplate.update(query, id);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    @Override
    public int UpdatePassword(Login details) {
        try {
            String password = bCryptPasswordEncoder.encode(details.getPassword());
            String query = "UPDATE " + TableName.LOGIN_TABLE + " SET PASSWORD=? WHERE MAILID=?";
            int row = jdbcTemplate.update(query, password, details.getMailId());

            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    @Override
    public int getUser(Login details) {
        try {
            String query = "SELECT * FROM " + TableName.LOGIN_TABLE + " WHERE MAILID=? AND PASSWORD=?";
            RowMapper<Login> login = new RawMapperImple();
            Login l = jdbcTemplate.queryForObject(query, login, details.getMailId(), details.getPassword());
            return 1;
        } catch (EmptyResultDataAccessException e) {
            if (e.getActualSize() == 0) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    @Override
    public List<Login> getAllUser() {
        try {
            String query = "SELECT * FROM " + TableName.LOGIN_TABLE;
            RowMapper<Login> loginRowMapper = new RawMapperImple();
            List<Login> loginList = jdbcTemplate.query(query, loginRowMapper);
            return loginList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Login findByMailId(String mailId) {
        try {
            System.out.println(mailId);
            String query = "SELECT * FROM " + TableName.LOGIN_TABLE + " WHERE MAILID=?";
            RowMapper<Login> loginRowMapper = new RawMapperImple();
            Login login = jdbcTemplate.queryForObject(query, loginRowMapper, mailId);
            return login;
        } catch (EmptyResultDataAccessException e) {
            if (e.getActualSize() == 0) {
                new Login();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
