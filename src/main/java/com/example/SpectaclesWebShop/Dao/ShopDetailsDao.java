package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.Carousel;
import com.example.SpectaclesWebShop.Bean.GlassType;
import com.example.SpectaclesWebShop.Bean.ShippingCharge;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ShopDetailsDao implements ShopDetailsInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String CreateShopDetailsTable() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.SHOP_DETAILS
                + " (ID INT PRIMARY KEY,SHOP_NAME VARCHAR(100) NOT NULL,ADDRESS VARCHAR(100) NOT NULL,CITY VARCHAR(30) NOT NULL,PINCODE VARCHAR(6) NOT NULL,PHONE_NUMBER JSON NOT NULL,MAIL_ID JSON NOT NULL,LOGO_URL VARCHAR(2000))";
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

    private String CreateGlassPriceDataBase() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.GLASSPRICE
                + " (G_ID INT AUTO_INCREMENT PRIMARY KEY,GLASS_NAME VARCHAR(10) NOT NULL,PRICE DOUBLE NOT NULL)";
    }

    // Category DataBase
    public String createCategoryTable() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.CATEGORY
                + " (CAT_ID INT AUTO_INCREMENT PRIMARY KEY,CATEGORYNAME VARCHAR(50) NOT NULL UNIQUE)";
    }

    // FrameStyle DataBase
    public String createFrameStyleTable() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.FRAME_STYLE
                + " (FRAME_ID INT AUTO_INCREMENT PRIMARY KEY,FRAMENAME VARCHAR(50) NOT NULL UNIQUE)";
    }

    // Company Name DataBase
    public String createCompanyNameTable() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.COMPANY_NAME
                + " (COMPANY_ID INT AUTO_INCREMENT PRIMARY KEY,COMPANY_NAME VARCHAR(50)NOT NULL UNIQUE)";
    }

    public String CreateOrderServiceTable() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.ORDERSERVICE
                + " (ID INT AUTO_INCREMENT PRIMARY KEY,SERVICE_PERSON VARCHAR(50) NOT NULL,PHONENUMBER VARCHAR(10) NOT NULL)";
    }

    @Override
    public int createDatabase() {
        try {
            jdbcTemplate.update(CreateShopDetailsTable());
            jdbcTemplate.update(CreateCarouselTable());
            jdbcTemplate.update(CreateShippingChargesDataBase());
            jdbcTemplate.update(CreateTaxDataBase());
            jdbcTemplate.update(CreateGlassPriceDataBase());
            jdbcTemplate.update(createCategoryTable());
            jdbcTemplate.update(createFrameStyleTable());
            jdbcTemplate.update(createCompanyNameTable());
            jdbcTemplate.update(CreateOrderServiceTable());
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
        return new ShopDetails();
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
    public ShippingCharge getShippingCharge() {
        try {
            String query = "SELECT * FROM " + TableName.SHIPPING_CHARGES;
            RowMapper<ShippingCharge> sMapper = new RowMapper<ShippingCharge>() {

                @Override
                public ShippingCharge mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ShippingCharge shippingCharge = new ShippingCharge();
                    shippingCharge.setId(rs.getLong("ID"));
                    shippingCharge.setCharge(rs.getDouble("CHARGE"));
                    shippingCharge.setMax(rs.getInt("MAX_QTY"));
                    return shippingCharge;
                }
            };
            ShippingCharge shippingCharge = jdbcTemplate.queryForObject(query, sMapper);
            return shippingCharge;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ShippingCharge();
    }

    @Override
    public List<GlassType> getGlassPricing() {
        try {
            String query = "select * from " + TableName.GLASSPRICE;
            RowMapper<GlassType> gMapper = new RowMapper<GlassType>() {

                @Override
                public GlassType mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GlassType glassType = new GlassType();
                    glassType.setId(rs.getLong("G_ID"));
                    glassType.setGlass_name(rs.getString("GLASS_NAME"));
                    glassType.setPrice(rs.getDouble("PRICE"));
                    return glassType;
                }
            };
            return jdbcTemplate.query(query, gMapper);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
