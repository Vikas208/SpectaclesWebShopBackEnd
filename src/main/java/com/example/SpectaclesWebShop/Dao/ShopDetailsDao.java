package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.Carousel;
import com.example.SpectaclesWebShop.Bean.GlassPrice;
import com.example.SpectaclesWebShop.Bean.ShopDetails;
import com.example.SpectaclesWebShop.Bean.TaxDetails;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.Info.TableName;
import com.example.SpectaclesWebShop.DaoInterfaces.ShopDetailsInterface;
import com.example.SpectaclesWebShop.RawMapperImplement.ShopDetailsRaw.CarouselRawMapperImple;
import com.example.SpectaclesWebShop.RawMapperImplement.ShopDetailsRaw.ShopDetailsRawMapperImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class ShopDetailsDao implements ShopDetailsInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String CreateShopDetailsTable() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.SHOP_DETAILS
                + " (ID INT PRIMARY KEY,SHOP_NAME VARCHAR(100) NOT NULL,ADDRESS VARCHAR(100) NOT NULL,CITY VARCHAR(30) NOT NULL,PINCODE VARCHAR(6) NOT NULL,PHONE_NUMBER JSON NOT NULL,MAIL_ID JSON NOT NULL,LOGO_URL VARCHAR(2000))";
    }

    private String InsertQueryShopDetails() {

        return "INSERT INTO " + TableName.SHOP_DETAILS
                + " (ID,SHOP_NAME,ADDRESS,CITY,PINCODE,PHONE_NUMBER,MAIL_ID,LOGO_URL) VALUES(1,'Shree chasama ghar','263, Medical Complex, Sola Road,Naranpura Vistar','Ahmedabad','380013','[\"7927470104\",\"9638272444\"]','[\"shreechasamaghar263@gmail.com\"]','https://res.cloudinary.com/dyg4mksoz/image/upload/v1643353999/ShopDetails/Logo-removebg-preview_jmpsu6.png')";
    }

    private String CreateCarouselTable() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.CAROUSEL
                + " (ID INT AUTO_INCREMENT PRIMARY KEY,IMAGE VARCHAR(2000))";
    }

    private String CreateShippingChargesDataBase() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.SHIPPING_CHARGES
                + " (ID INT AUTO_INCREMENT PRIMARY KEY,CHARGE DOUBLE DEFAULT(0) NOT NULL,MAX_QTY INT DEFAULT(0))";
    }

    private String CreateTaxDataBase() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.TAX_DATABASE
                + " (ID INT AUTO_INCREMENT PRIMARY KEY,CATEGORY_NAME VARCHAR(50),GST DOUBLE DEFAULT(0),OTHER_TAX DOUBLE DEFAULT(0),CONSTRAINT FOREIGN KEY(CATEGORY_NAME) REFERENCES "
                + TableName.CATEGORY + " (CATEGORYNAME))";
    }

    private String CreateGlassPriceDataBase(){
        return  "CREATE TABLE IF NOT EXISTS "+TableName.GLASSPRICE+" (G_ID INT AUTO_INCREMENT PRIMARY KEY,GLASS_NAME VARCHAR(10) NOT NULL,PRICE DOUBLE NOT NULL)";
    }

    @Override
    public int createDatabase() {
        try {
            jdbcTemplate.update(CreateShopDetailsTable());
            jdbcTemplate.update(CreateCarouselTable());
            jdbcTemplate.update(CreateShippingChargesDataBase());
            jdbcTemplate.update(CreateTaxDataBase());
            jdbcTemplate.update(CreateGlassPriceDataBase());
            return Code.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    @Override
    public ShopDetails getShopDetails() {
        try {
            String query = "SELECT * FROM " + TableName.SHOP_DETAILS;
            RowMapper<ShopDetails> shopDetailsRowMapper = new ShopDetailsRawMapperImple();
            ShopDetails shopDetails = jdbcTemplate.queryForObject(query, shopDetailsRowMapper);
            return shopDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Carousel> getCarouselImages() {
        try {
            String query = "SELECT IMAGE FROM " + TableName.CAROUSEL;
            RowMapper<Carousel> carouselRowMapper = new CarouselRawMapperImple();
            return jdbcTemplate.query(query, carouselRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GlassPrice> getGlassDetails() {
        try{
            String query = "SELECT * FROM "+TableName.GLASSPRICE;
            RowMapper<GlassPrice> mapper = new RowMapper<GlassPrice>() {
                @Override
                public GlassPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GlassPrice glassPrice = new GlassPrice(rs.getLong("G_ID"),rs.getString("GLASS_NAME"),rs.getDouble("PRICE"));
                    return  glassPrice;
                }
            };
            return  jdbcTemplate.query(query,mapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TaxDetails> getTaxDetails() {
        try{
            String query = "SELECT * FROM "+TableName.TAX_DATABASE;
            RowMapper<TaxDetails> taxDetailsRowMapper = new RowMapper<TaxDetails>() {
                @Override
                public TaxDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                    TaxDetails taxDetails = new TaxDetails(
                            rs.getLong("ID"),
                            rs.getString("CATEGORY_NAME"),
                            rs.getDouble("GST"),
                            rs.getDouble("OTHER_TAX")
                    );
                    return  taxDetails;
                }
            };
            return  jdbcTemplate.query(query,taxDetailsRowMapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
