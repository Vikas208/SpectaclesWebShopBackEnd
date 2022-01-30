package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.Products;
import com.example.SpectaclesWebShop.DaoInterfaces.ProductsInterface;
import com.example.SpectaclesWebShop.Info.TableName;
import com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw.ProductsRawMapperImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductsDao implements ProductsInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public String createProductDatabase() {
        return "create table if not exists " + TableName.PRODUCTS
                + " (P_ID int auto_increment primary key,P_NAME varchar(100) NOT NULL,P_DESCRIPTION varchar(2000),P_PRICE double NOT NULL,P_CATEGORY varchar(50) NOT NULL,P_GROUP VARCHAR(6) NOT NULL,P_FRAMESTYLE VARCHAR(20) NOT NULL,BANNER_IMAGE VARCHAR(2000) NOT NULL,P_STOCK INT NOT NULL DEFAULT (0),TOTALSALES INT DEFAULT (0));";
    }

    public String createReviewDatabase() {
        return "CREATE table if not exists " + TableName.REVIEWS
                + " (PR_ID INT auto_increment primary key,P_ID INT references " + TableName.PRODUCTS
                + "(P_ID) ON DELETE CASCADE,C_ID INT references " + TableName.LOGIN_TABLE
                + "(ID) ON DELETE CASCADE,RATING double NOT NULL default(0),FEEDBACK VARCHAR(500));";
    }

    @Override
    public void createDataBase() {
        try {
            jdbcTemplate.update(createProductDatabase());
            jdbcTemplate.update(createReviewDatabase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Products> getAllProducts(int limit, int offset) {
        try {
            String query = "SELECT distinct P.P_ID,P.P_NAME,P.P_DESCRIPTION,P.P_PRICE,P.P_CATEGORY,P.P_GROUP,P.P_FRAMESTYLE,P.BANNER_IMAGE,P.P_STOCK,P.TOTALSALES,PR.RATING FROM "
                    + TableName.PRODUCTS + " P LEFT JOIN " + TableName.REVIEWS
                    + " PR ON P.P_ID=PR.P_ID LIMIT ? OFFSET ?";

            RowMapper<Products> productsRowMapper = new ProductsRawMapperImple();
            return jdbcTemplate.query(query, productsRowMapper, limit, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Products> getTrendingProducts() {
        try {
            String query = "SELECT distinct P.P_ID,P.P_NAME,P.P_DESCRIPTION,P.P_PRICE,P.P_CATEGORY,P.P_GROUP,P.P_FRAMESTYLE,P.BANNER_IMAGE,P.P_STOCK,P.TOTALSALES,PR.RATING FROM "
                    + TableName.PRODUCTS + " P LEFT JOIN " + TableName.REVIEWS
                    + " PR ON P.P_ID=PR.P_ID WHERE (SELECT AVG(RATING) FROM PRODUCT_REVIEWS WHERE P_ID = P.P_ID) >=3 OR P.TOTALSALES >= (SELECT AVG(TOTALSALES) FROM PRODUCTS) LIMIT 5";
            RowMapper<Products> pRowMapper = new ProductsRawMapperImple();
            return jdbcTemplate.query(query, pRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Products getProduct() {
        return null;
    }
}

/*
 * --create table if not exists PROUCTS_SALE(PS_ID INT auto_increment primary
 * KEY,P_ID INT REFERENCES PRODUCTS(P_ID),SALE_FROM DATE,SALE_TO DATE);
 * -- create table if not exists PRODUCTS(P_ID int auto_increment primary
 * key,P_NAME varchar(100) NOT NULL,P_DESCRIPTION varchar(2000),P_PRICE double
 * NOT NULL,P_CATEGORY varchar(50) NOT NULL,P_GROUP VARCHAR(6) NOT NULL,IMAGE
 * VARCHAR(2000) NOT NULL);
 * -- create table if not exists PRODUCTS_IMAGES(PI_ID INT auto_increment
 * primary key,P_ID INT references PRODUCTS(P_ID),PI_IMAGE varchar(2000));
 * -- create table if not exists PRODUCT_CATEGORY(C_ID INT auto_increment
 * primary key,C_CATEGORY varchar(50));
 * -- CREATE table if not exists PRODUCT_REVIEWS(PR_ID INT auto_increment
 * primary key,P_ID INT references PRODUCTS(P_ID),C_ID INT references
 * LOGIN(ID),RATING DOUBLE default(0),FEEDBACK VARCHAR(500));
 */