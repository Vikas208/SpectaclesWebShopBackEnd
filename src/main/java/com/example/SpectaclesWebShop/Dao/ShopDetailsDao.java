package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.ShopDetails;
import com.example.SpectaclesWebShop.CodeName.Code;
import com.example.SpectaclesWebShop.CodeName.TableName;
import com.example.SpectaclesWebShop.DaoInterfaces.ShopDetailsInterface;
import com.example.SpectaclesWebShop.RawMapperImplement.ShopDetailsRaw.ShopDetailsRawMapperImple;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDetailsDao implements ShopDetailsInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;


    private String CreateShopDetailsTable(){
        return "CREATE TABLE IF NOT EXISTS "+ TableName.SHOP_DETAILS+" (ID INT PRIMARY KEY,SHOP_NAME VARCHAR(100) NOT NULL,ADDRESS VARCHAR(100),CITY VARCHAR(30),PINCODE VARCHAR(6),PHONE_NUMBER JSON,MAIL_ID JSON)";
    }

    private String InsertQueryShopDetails(){

            return "INSERT INTO "+TableName.SHOP_DETAILS+" (ID,SHOP_NAME,ADDRESS,CITY,PINCODE,PHONE_NUMBER,MAIL_ID) VALUES(1,'Shree chasama ghar','263, Medical Complex, Sola Road,Naranpura Vistar','Ahmedabad','380013','[\"7927470104\",\"9638272444\"]','[\"shreechasamaghar263@gmail.com\"]')";
    }
    private int isDataInserted(){
        String query = "SELECT COUNT(*) FROM "+TableName.SHOP_DETAILS;
        return jdbcTemplate.queryForObject(query,Integer.class);
    }

    @Override
    public int createDatabase() {
        try{
            jdbcTemplate.update(CreateShopDetailsTable());

            if(isDataInserted()!=1){
                jdbcTemplate.update(InsertQueryShopDetails());
            }
            return Code.SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    @Override
    public ShopDetails getShopDetails() {
        try{
            String query = "SELECT * FROM "+TableName.SHOP_DETAILS;
            RowMapper<ShopDetails> shopDetailsRowMapper = new ShopDetailsRawMapperImple();
            ShopDetails shopDetails = jdbcTemplate.queryForObject(query,shopDetailsRowMapper);
            return shopDetails;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
