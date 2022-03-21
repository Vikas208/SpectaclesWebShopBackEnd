package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.Data;
import com.example.SpectaclesWebShop.Bean.FeedBack;
import com.example.SpectaclesWebShop.Bean.ProductDescription;
import com.example.SpectaclesWebShop.Bean.ProductImage;
import com.example.SpectaclesWebShop.Bean.ProductSales;
import com.example.SpectaclesWebShop.Bean.Products;
import com.example.SpectaclesWebShop.DaoInterfaces.ProductsInterface;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.Info.TableName;
import com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw.ProductFeedBackRawMapper;
import com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw.ProductImageRawMapper;
import com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw.ProductRawMapperImple;
import com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw.ProductsRawImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class ProductsDao implements ProductsInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Products DataBase
    public String createProductDatabase() {
        return "create table if not exists " + TableName.PRODUCTS
                + " (P_ID int auto_increment primary key,P_NAME varchar(100) NOT NULL,P_DESCRIPTION varchar(2000),P_PRICE double NOT NULL,P_CATEGORY varchar(50) NOT NULL,P_GROUP VARCHAR(6) NOT NULL,P_FRAMESTYLE VARCHAR(50) NOT NULL,FRAMESIZE VARCHAR(45) NOT NULL,COLOR VARCHAR(45) NOT NULL,COMPANY_NAME VARCHAR(50) NOT NULL,BANNER_IMAGE VARCHAR(2000) NOT NULL,P_STOCK INT NOT NULL DEFAULT (0),TOTALSALES INT DEFAULT (0),CONSTRAINT FOREIGN KEY (P_CATEGORY) REFERENCES "
                + TableName.CATEGORY + " (CATEGORYNAME) ,FOREIGN KEY (P_FRAMESTYLE) REFERENCES " + TableName.FRAME_STYLE
                + " (FRAMENAME), FOREIGN KEY (COMPANY_NAME) REFERENCES " + TableName.COMPANY_NAME + " (COMPANY_NAME));";
    }

    // Reviews DataBase
    public String createReviewDatabase() {
        return "CREATE table if not exists " + TableName.REVIEWS
                + " (PR_ID INT auto_increment primary key,P_ID INT,C_ID INT ,RATING double NOT NULL default(0),FEEDBACK VARCHAR(1000),CONSTRAINT FOREIGN KEY (P_ID) REFERENCES "
                + TableName.PRODUCTS + "(P_ID) ON DELETE CASCADE,FOREIGN KEY (C_ID) REFERENCES " + TableName.LOGIN_TABLE
                + "(ID) ON DELETE CASCADE);";
    }

    // Products Images DataBase
    public String CreateProductImagesDatabase() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.PRODUCT_IMAGE
                + " (PI_ID INT AUTO_INCREMENT PRIMARY KEY,P_ID INT,IMAGE_URL VARCHAR(2000),CONSTRAINT FOREIGN KEY(P_ID) REFERENCES "
                + TableName.PRODUCTS + "(P_ID) ON DELETE CASCADE)";
    }

    // Sales DataBase
    public String createSalesDatabase() {
        return "CREATE TABLE IF NOT EXISTS " + TableName.PRODUCT_SALES
                + " (PS_ID INT AUTO_INCREMENT PRIMARY KEY,P_ID INT UNIQUE,OFF_AMOUNT double default(0.0),PERCENTAGE double default(0.0),S_DATE DATE NOT NULL,E_DATE DATE NOT NULL,CONSTRAINT FOREIGN KEY(P_ID) REFERENCES "
                + TableName.PRODUCTS + "(P_ID) ON DELETE CASCADE,CHECK(S_DATE<=E_DATE))";
    }

    //

    @Override
    public void createDataBase() {
        try {
            jdbcTemplate.update(createProductDatabase());
            jdbcTemplate.update(createReviewDatabase());
            jdbcTemplate.update(CreateProductImagesDatabase());
            jdbcTemplate.update(createSalesDatabase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Products> getAllProducts(int limit, int offset) {
        try {
            String query = "SELECT distinct P.P_ID,P.P_NAME,P.P_PRICE,P.BANNER_IMAGE,PR.RATING FROM "
                    + TableName.PRODUCTS
                    + " P LEFT JOIN (SELECT P_ID,AVG(RATING)'RATING' FROM " + TableName.REVIEWS
                    + " group by P_ID) PR ON P.P_ID=PR.P_ID LIMIT ? OFFSET ?";

            RowMapper<Products> productsRowMapper = new ProductsRawImple();
            return jdbcTemplate.query(query, productsRowMapper, limit, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Products> getTrendingProducts() {
        try {
            String query = "SELECT DISTINCT P.P_ID,P.P_NAME,P.P_PRICE,P.BANNER_IMAGE,PR.RATING FROM "
                    + TableName.PRODUCTS
                    + " P LEFT JOIN (SELECT P_ID,AVG(RATING) 'RATING' FROM " + TableName.REVIEWS
                    + " group by P_ID) PR ON P.P_ID = PR.P_ID WHERE ((PR.RATING>=3 AND (P.TOTALSALES>=(SELECT AVG(TOTALSALES) FROM "
                    + TableName.PRODUCTS + ") AND P.TOTALSALES>=10 ))) limit 5";

            RowMapper<Products> pRowMapper = new ProductsRawImple();
            return jdbcTemplate.query(query, pRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Products getProduct(long id) {

        try {
            String query = "select * from " + TableName.PRODUCTS + " P left join (SELECT P_ID,AVG(RATING)'RATING' FROM "
                    + TableName.REVIEWS + " GROUP BY(P_ID)) PR ON P.P_ID=PR.P_ID LEFT JOIN (SELECT * FROM "
                    + TableName.PRODUCT_SALES + " WHERE E_DATE>=current_date()) PS  ON P.P_ID = PS.P_ID WHERE P.P_ID=?";

            RowMapper<Products> pMapper = new ProductRawMapperImple();
            return jdbcTemplate.queryForObject(query, pMapper, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countProducts() {
        try {
            String query = "SELECT COUNT(P_ID) FROM " + TableName.PRODUCTS;
            return jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<FeedBack> getProductReviews(long p_id) {
        try {
            String query = "select distinct * from " + TableName.REVIEWS + " PR LEFT JOIN (SELECT ID,MAILID FROM "
                    + TableName.LOGIN_TABLE + ") L ON PR.C_ID = L.ID where PR.P_ID = ?;";
            RowMapper<FeedBack> feedbacks = new ProductFeedBackRawMapper();
            return jdbcTemplate.query(query, feedbacks, p_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ProductImage> getProductImage(long p_id) {
        try {
            String query = "SELECT * FROM " + TableName.PRODUCT_IMAGE + " WHERE P_ID = ?";
            RowMapper<ProductImage> productImages = new ProductImageRawMapper();
            return jdbcTemplate.query(query, productImages, p_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int saveFeedback(FeedBack feedback) {
        try {
            String query = "UPDATE " + TableName.REVIEWS + " SET RATING=?,FEEDBACK=? WHERE P_ID=? AND C_ID=?";
            int result = jdbcTemplate.update(query, feedback.getRating(), feedback.getFeedBack(), feedback.getP_id(),
                    feedback.getC_id());
            if (result == 0) {
                query = "INSERT INTO " + TableName.REVIEWS + " (P_ID,C_ID,RATING,FEEDBACK) VALUES(?,?,?,?)";
                return jdbcTemplate.update(query, feedback.getP_id(), feedback.getC_id(), feedback.getRating(),
                        feedback.getFeedBack());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    @Override
    public List<Products> searchProducts(String searchQuery, int offset) {
        try {
            String query = "select distinct * from " + TableName.PRODUCTS
                    + " P left join (select P_ID,AVG(RATING)'RATING' FROM "
                    + TableName.REVIEWS + " GROUP BY(P_ID))PR ON P.P_ID=PR.P_ID  WHERE P_NAME like '%" + searchQuery
                    + "%' or P_FRAMESTYLE regexp ('" + searchQuery + "') or COMPANY_NAME REGEXP ('" + searchQuery
                    + "') or P_CATEGORY regexp ('" + searchQuery + "') LIMIT 15 OFFSET ?";
            RowMapper<Products> productsRowMapper = new ProductsRawImple();
            return jdbcTemplate.query(query, productsRowMapper, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countSearchedProducts(String searchQuery) {
        try {
            String query = "select count(P_ID) from " + TableName.PRODUCTS + " WHERE P_NAME regexp '%" + searchQuery
                    + "%' or P_FRAMESTYLE regexp ('" + searchQuery + "') or COMPANY_NAME REGEXP ('" + searchQuery
                    + "') or P_CATEGORY regexp ('" + searchQuery + "')";
            return jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // common Function for category ,framestyle,company name
    public List<Data> getData(String query) {
        try {
            RowMapper<Data> Mapper = new RowMapper<Data>() {
                @Override
                public Data mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Data(rs.getLong(1), rs.getString(2));
                }
            };
            return jdbcTemplate.query(query, Mapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Data> getAllCategories() {
        String query = "SELECT * FROM " + TableName.CATEGORY;
        return getData(query);

    }

    @Override
    public List<Data> getAllCompanyName() {
        String query = "SELECT * FROM " + TableName.COMPANY_NAME;
        return getData(query);
    }

    @Override
    public List<Data> getAllFrameStyle() {
        String query = "SELECT * FROM " + TableName.FRAME_STYLE;
        return getData(query);
    }

    @Override
    public List<Products> filterProducts(String name, String category, String frameStyle, String companyname,
            String group, String framesize, double sprice, double eprice,
            int offset) {
        try {

            String priceQuery = "P_PRICE BETWEEN " + sprice + " AND " + eprice;
            if (eprice == 0) {
                priceQuery = "P_PRICE BETWEEN " + sprice + " AND (SELECT MAX(P_PRICE) FROM " + TableName.PRODUCTS + ")";
            }

            String query = "SELECT * FROM " + TableName.PRODUCTS + " P left join (select P_ID,AVG(RATING)'RATING' FROM "
                    + TableName.REVIEWS
                    + " GROUP BY(P_ID))PR ON P.P_ID=PR.P_ID WHERE P_CATEGORY regexp ('"
                    + category + "') and P_FRAMESTYLE regexp ('" + frameStyle
                    + "')  and COMPANY_NAME regexp ('" + companyname + "') and P_GROUP regexp ('"
                    + group + "') and FRAMESIZE regexp('" + framesize + "') and " + priceQuery
                    + " and (P_NAME like '%"
                    + name
                    + "%' or P_FRAMESTYLE regexp ('" + name + "') or COMPANY_NAME REGEXP ('" + name
                    + "') or P_CATEGORY regexp ('" + name + "') or P_GROUP regexp ('" + name + "')) LIMIT 15 OFFSET ?";

            RowMapper<Products> pRowMapper = new ProductsRawImple();
            return jdbcTemplate.query(query, pRowMapper, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countFilterProducts(String name, String category, String frameStyle, String companyname,
            String group, String framesize, double sprice, double eprice) {

        try {
            String priceQuery = "P_PRICE BETWEEN " + sprice + " AND " + eprice;
            if (eprice == 0) {
                priceQuery = "P_PRICE BETWEEN " + sprice + " AND (SELECT MAX(P_PRICE) FROM " + TableName.PRODUCTS + ")";
            }
            String query = "SELECT count(P.P_ID) FROM " + TableName.PRODUCTS
                    + " P left join (select P_ID,AVG(RATING)'RATING' FROM "
                    + TableName.REVIEWS
                    + " GROUP BY(P_ID))PR ON P.P_ID=PR.P_ID WHERE P_CATEGORY regexp ('"
                    + category + "') and P_FRAMESTYLE regexp ('" + frameStyle
                    + "')  and COMPANY_NAME regexp ('" + companyname + "') and P_GROUP regexp ('"
                    + group + "')  and FRAMESIZE regexp('" + framesize
                    + "') and " + priceQuery
                    + " and (P_NAME like '%" + name
                    + "%' or P_FRAMESTYLE regexp ('" + name + "') or COMPANY_NAME REGEXP ('" + name
                    + "') or P_CATEGORY regexp ('" + name + "') or P_GROUP regexp ('" + name + "'))";
            return jdbcTemplate.queryForObject(query, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Products getOrderedProduct(long p_id) {
        try {
            String query = "select P.P_ID,P_NAME,(P_PRICE - IF(PS.E_DATE>current_date(),0,PS.OFF_AMOUNT+(P_PRICE*(PS.PERCENTAGE/100))))'PRICE',BANNER_IMAGE,P_STOCK,P_CATEGORY from "
                    + TableName.PRODUCTS + " P LEFT JOIN (SELECT * FROM "
                    + TableName.PRODUCT_SALES + ") PS  ON P.P_ID = PS.P_ID WHERE P.P_ID=?";

            RowMapper<Products> pMapper = new RowMapper<Products>() {

                @Override
                public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Products products = new Products();
                    ProductDescription productDescription = new ProductDescription();
                    ProductSales productSales = new ProductSales();

                    products.setId(rs.getLong("P_ID"));
                    products.setP_name(rs.getString("P_NAME"));
                    products.setP_price(rs.getDouble("PRICE"));
                    products.setBannerImage(rs.getString("BANNER_IMAGE"));
                    products.setP_stock(rs.getInt("P_STOCK"));

                    productDescription.setP_category(rs.getString("P_CATEGORY"));

                    products.setProductDescription(productDescription);
                    products.setProductSales(productSales);

                    return products;
                }

            };
            return jdbcTemplate.queryForObject(query, pMapper, p_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateProductStockAndSales(long p_id, int qty) {
        try {
            String query = "SELECT P_STOCK,TOTALSALES FROM " + TableName.PRODUCTS + " where P_ID=?";

            RowMapper<Products> pMapper = new RowMapper<Products>() {

                @Override
                public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Products products = new Products();
                    products.setP_stock(rs.getInt("P_STOCK"));
                    products.setTotalSales(rs.getInt("TOTALSALES"));
                    return products;
                }

            };
            Products products = jdbcTemplate.queryForObject(query, pMapper, p_id);

            query = "UPDATE " + TableName.PRODUCTS + " SET P_STOCK=?,TOTALSALES=? WHERE P_ID=?";

            return jdbcTemplate.update(query, products.getP_stock() - qty, products.getTotalSales() + qty, p_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
