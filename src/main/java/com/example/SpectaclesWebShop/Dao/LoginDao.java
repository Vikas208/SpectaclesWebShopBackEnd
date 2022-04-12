package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.DaoInterfaces.LoginInterface;
import com.example.SpectaclesWebShop.Info.TableName;
import com.example.SpectaclesWebShop.RawMapperImplement.LoginRaw.LoginRawMapperImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
                    + " (ID INT AUTO_INCREMENT PRIMARY KEY,MAILID  VARCHAR(100) UNIQUE NOT NULL,NAME VARCHAR(100) NOT NULL,PASSWORD VARCHAR(300) NOT NULL,HASROLE VARCHAR(10) NOT NULL DEFAULT('user'),JOINTIME DATETIME)";
            return jdbcTemplate.update(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    // REGISTER USER
    @Override
    public int SaveData(Login l) {
        try {

            String query = "INSERT INTO " + TableName.LOGIN_TABLE
                    + " (MAILID,NAME,PASSWORD,HASROLE,JOINTIME) VALUES(?,?,?,?,?);";
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = format.format(date);
            String password = bCryptPasswordEncoder.encode(l.getPassword());
            return jdbcTemplate.update(query, l.getMailId(), l.getName(), password, l.getHasRole().toLowerCase(),currentDateTime);

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
            return jdbcTemplate.update(query, id);
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

            return jdbcTemplate.update(query, password, details.getMailId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    @Override
    public List<Login> getAllUser() {
        try {
            String query = "SELECT ID,MAILID,NAME,JOINTIME FROM " + TableName.LOGIN_TABLE + " WHERE HASROLE='user'";
            RowMapper<Login> loginRowMapper = new RowMapper<Login>() {

                @Override
                public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Login l = new Login();
                    l.setId(rs.getLong("ID"));
                    l.setMailId(rs.getString("MAILID"));
                    l.setName(rs.getString("NAME"));
                    l.setDateTime(rs.getObject("JOINTIME", LocalDateTime.class));

                    return l;
                }

            };
            return jdbcTemplate.query(query, loginRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Login findByMailId(String value) {
        try {

            String query = "SELECT * FROM " + TableName.LOGIN_TABLE + " WHERE MAILID=?";
            RowMapper<Login> loginRowMapper = new LoginRawMapperImple();
            return jdbcTemplate.queryForObject(query, loginRowMapper, value);
        } catch (EmptyResultDataAccessException e) {
            if (e.getActualSize() == 0) {
                return new Login();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Login findUserByIdAdmin(String mailid) {
        try {

            String query = "SELECT * FROM " + TableName.LOGIN_TABLE + " WHERE MAILID=? AND HASROLE='admin'";
            RowMapper<Login> loginRowMapper = new LoginRawMapperImple();
            return jdbcTemplate.queryForObject(query, loginRowMapper, mailid);
        } catch (EmptyResultDataAccessException e) {
            if (e.getActualSize() == 0) {
                return new Login();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Login findById(long l) {
        try {

            String query = "SELECT * FROM " + TableName.LOGIN_TABLE + " WHERE ID=?";
            RowMapper<Login> loginRowMapper = new LoginRawMapperImple();
            return jdbcTemplate.queryForObject(query, loginRowMapper, l);
        } catch (EmptyResultDataAccessException e) {
            if (e.getActualSize() == 0) {
                return new Login();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int ChangeName(Login l) {
        try {

            String query = "UPDATE " + TableName.LOGIN_TABLE + " SET NAME=? WHERE ID=?";
            return jdbcTemplate.update(query, l.getName(), l.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }
}
