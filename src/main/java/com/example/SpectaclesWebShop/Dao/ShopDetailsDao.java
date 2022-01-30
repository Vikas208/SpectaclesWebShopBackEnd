package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.Carousel;
import com.example.SpectaclesWebShop.Bean.ShopDetails;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.Info.TableName;
import com.example.SpectaclesWebShop.DaoInterfaces.ShopDetailsInterface;
import com.example.SpectaclesWebShop.RawMapperImplement.ShopDetailsRaw.CarouselRawMapperImple;
import com.example.SpectaclesWebShop.RawMapperImplement.ShopDetailsRaw.ShopDetailsRawMapperImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopDetailsDao implements ShopDetailsInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;


    private String CreateShopDetailsTable(){
        return "CREATE TABLE IF NOT EXISTS "+ TableName.SHOP_DETAILS+" (ID INT PRIMARY KEY,SHOP_NAME VARCHAR(100) NOT NULL,ADDRESS VARCHAR(100) NOT NULL,CITY VARCHAR(30) NOT NULL,PINCODE VARCHAR(6) NOT NULL,PHONE_NUMBER JSON NOT NULL,MAIL_ID JSON NOT NULL,LOGO_URL VARCHAR(2000))";
    }

    private String InsertQueryShopDetails(){

            return "INSERT INTO "+TableName.SHOP_DETAILS+" (ID,SHOP_NAME,ADDRESS,CITY,PINCODE,PHONE_NUMBER,MAIL_ID,LOGO_URL) VALUES(1,'Shree chasama ghar','263, Medical Complex, Sola Road,Naranpura Vistar','Ahmedabad','380013','[\"7927470104\",\"9638272444\"]','[\"shreechasamaghar263@gmail.com\"]','https://res.cloudinary.com/dyg4mksoz/image/upload/v1643353999/ShopDetails/Logo-removebg-preview_jmpsu6.png')";
    }

    private String CreateCarouselTable(){
        return "CREATE TABLE IF NOT EXISTS "+TableName.CAROUSEL+" (ID INT AUTO_INCREMENT PRIMARY KEY,IMAGE VARCHAR(2000))";
    }

    @Override
    public int createDatabase() {
        try{
            jdbcTemplate.update(CreateShopDetailsTable());
            jdbcTemplate.update(CreateCarouselTable());
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

    @Override
    public List<Carousel> getCarouselImages(){
        try{
            String query = "SELECT IMAGE FROM "+TableName.CAROUSEL;
            RowMapper<Carousel> carouselRowMapper = new CarouselRawMapperImple();
            return jdbcTemplate.query(query,carouselRowMapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
