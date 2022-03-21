package com.example.SpectaclesWebShop.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.example.SpectaclesWebShop.Bean.CustomersProductsDetails;
import com.example.SpectaclesWebShop.Bean.Products;
import com.example.SpectaclesWebShop.DaoInterfaces.CustomerProductsInterface;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.Info.TableName;
import com.example.SpectaclesWebShop.RawMapperImplement.CustomerProductsRaw.CustomerProductRawImplement;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerProductsDao implements CustomerProductsInterface {

       @Autowired
       JdbcTemplate jdbcTemplate;

       public String createCartDataBase() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.CUSTOMER_CART
                            + " (CC_ID INT AUTO_INCREMENT PRIMARY KEY,C_ID INT NOT NULL,P_ID INT NOT NULL,QTY INT DEFAULT(1),ONLYFRAME BOOLEAN NOT NULL DEFAULT(FALSE),GLASSTYPE VARCHAR(10),LEFT_EYE_NO DOUBLE DEFAULT(0),RIGHT_EYE_NO DOUBLE DEFAULT(0),CONSTRAINT FOREIGN KEY(P_ID) REFERENCES "
                            + TableName.PRODUCTS + "(P_ID) ON DELETE CASCADE,FOREIGN KEY(C_ID) REFERENCES "
                            + TableName.LOGIN_TABLE + "(ID) ON DELETE CASCADE,CHECK(QTY!=0))";
       }

       public String createWishListDataBase() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.CUSTOMER_WISHLIST
                            + " (CW_ID INT AUTO_INCREMENT PRIMARY KEY,C_ID INT NOT NULL,P_ID INT NOT NULL,CONSTRAINT FOREIGN KEY (C_ID) REFERENCES "
                            + TableName.LOGIN_TABLE + "(ID) ON DELETE CASCADE,FOREIGN KEY (P_ID) REFERENCES "
                            + TableName.PRODUCTS + " (P_ID) ON DELETE CASCADE)";
       }

       @Override
       public void CreateDataBase() {
              try {
                     jdbcTemplate.update(createCartDataBase());
                     jdbcTemplate.update(createWishListDataBase());
              } catch (Exception e) {
                     e.printStackTrace();
              }
       }

       @Override
       public int SaveProductToCart(CustomersProductsDetails customersProductsDetails) {
              try {
                     String query = "INSERT INTO " + TableName.CUSTOMER_CART
                                   + " (C_ID,P_ID) VALUES (?,?)";

                     return jdbcTemplate.update(query, customersProductsDetails.getcommon_id(),
                                   customersProductsDetails.getP_id());
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int SaveProductToWishList(CustomersProductsDetails customersProductsDetails) {
              try {
                     String query = "INSERT INTO " + TableName.CUSTOMER_WISHLIST
                                   + " (C_ID,P_ID) SELECT * FROM (SELECT ? AS C_ID ,? AS P_ID) AS TEMP WHERE NOT EXISTS (SELECT C_ID,P_ID FROM "
                                   + TableName.CUSTOMER_WISHLIST + " WHERE C_ID=? AND P_ID=?) LIMIT 1";
                     return jdbcTemplate.update(query, customersProductsDetails.getcommon_id(),
                                   customersProductsDetails.getP_id(), customersProductsDetails.getcommon_id(),
                                   customersProductsDetails.getP_id());
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public List<CustomersProductsDetails> getCustomerCart(long c_id, int offset) {
              try {
                     String query = "select * from " + TableName.CUSTOMER_CART
                                   + " CC LEFT JOIN (select P.P_ID,P_NAME,BANNER_IMAGE,P_PRICE,P_CATEGORY,P_STOCK,PS.OFF_AMOUNT,PS.PERCENTAGE FROM "
                                   + TableName.PRODUCTS + " P LEFT JOIN (SELECT P_ID,OFF_AMOUNT,PERCENTAGE FROM "
                                   + TableName.PRODUCT_SALES
                                   + " WHERE E_DATE>=current_date()) PS ON P.P_ID=PS.P_ID) P ON CC.P_ID = P.P_ID WHERE C_ID = ? limit 5 offset ?";

                     RowMapper<CustomersProductsDetails> cMapper = new CustomerProductRawImplement();
                     return jdbcTemplate.query(query, cMapper, c_id, offset);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;
       }

       @Override
       public List<CustomersProductsDetails> getCustomerWishList(long c_id, int offset) {
              try {
                     String query = "select CW.CW_ID,CW.C_ID,CW.P_ID,P.P_ID,P.P_NAME,P.BANNER_IMAGE,P.P_PRICE,P.P_CATEGORY,PR.RATING from "
                                   + TableName.CUSTOMER_WISHLIST + " CW LEFT JOIN " + TableName.PRODUCTS
                                   + " P LEFT JOIN (SELECT P_ID,AVG(RATING) 'RATING' FROM " + TableName.REVIEWS
                                   + " group by P_ID) PR ON PR.P_ID = P.P_ID ON CW.P_ID = P.P_ID WHERE C_ID = ? LIMIT 15 OFFSET ?";
                     RowMapper<CustomersProductsDetails> cMapper = new RowMapper<CustomersProductsDetails>() {

                            @Override
                            public CustomersProductsDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   CustomersProductsDetails customersProductsDetails = new CustomersProductsDetails();
                                   Products products = new Products();
                                   customersProductsDetails.setId(rs.getLong("CW_ID"));
                                   customersProductsDetails.setcommon_id(rs.getLong("C_ID"));
                                   customersProductsDetails.setP_id(rs.getLong("P_ID"));

                                   products.setId(rs.getLong("P_ID"));
                                   products.setP_name(rs.getString("P_NAME"));
                                   products.setP_price(rs.getDouble("P_PRICE"));
                                   products.setBannerImage(rs.getString("BANNER_IMAGE"));
                                   products.setRating(rs.getDouble("RATING"));

                                   customersProductsDetails.setProducts(products);
                                   return customersProductsDetails;
                            }

                     };
                     return jdbcTemplate.query(query, cMapper, c_id, offset);

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;

       }

       @Override
       public int DeleteCartProduct(long cc_id) {
              try {
                     String query = "DELETE FROM " + TableName.CUSTOMER_CART + " WHERE CC_ID=?";
                     return jdbcTemplate.update(query, cc_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int DeletewWishListProduct(long cw_id) {
              try {
                     String query = "DELETE FROM " + TableName.CUSTOMER_WISHLIST + " WHERE CW_ID=?";
                     return jdbcTemplate.update(query, cw_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int UpdateCartDetails(CustomersProductsDetails customersProductsDetails) {
              try {
                     String query = "UPDATE " + TableName.CUSTOMER_CART
                                   + " SET QTY=?,GLASSTYPE=?,ONLYFRAME=?,LEFT_EYE_NO=?,RIGHT_EYE_NO=? where CC_ID=?";
                     return jdbcTemplate.update(query, customersProductsDetails.getQty(),
                                   customersProductsDetails.getGlassType(),
                                   customersProductsDetails.isOnlyframe(),
                                   customersProductsDetails.getLeft_eye_no(),
                                   customersProductsDetails.getRight_eye_no(), customersProductsDetails.getId());
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int countTotalProductsInCart(long c_id) {
              try {
                     String query = "SELECT COUNT(CC_ID) FROM " + TableName.CUSTOMER_CART + " WHERE C_ID=?";
                     return jdbcTemplate.queryForObject(query, Integer.class, c_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int countTotalProductsInWishList(long c_id) {
              try {
                     String query = "SELECT COUNT(CW_ID) FROM " + TableName.CUSTOMER_WISHLIST + " WHERE C_ID=?";
                     return jdbcTemplate.queryForObject(query, Integer.class, c_id);

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public List<HashMap<String, Object>> getBillingInformation(long c_id) {
              try {
                     String query = "select CC.C_ID,sum(CC.QTY * ((P.P_PRICE - IF(PS.E_DATE < CURRENT_DATE(),0,PS.OFF_AMOUNT) - (P.P_PRICE * IF(PS.E_DATE < CURRENT_DATE(),0,PS.PERCENTAGE) / 100) )+ IF(isnull(GP.GLASS_NAME),0,GP.PRICE)+ (P.P_PRICE * (IF(isnull(TD.GST),0,TD.GST)/100))+ (P.P_PRICE * IF(ISNULL(TD.OTHER_TAX),0,TD.OTHER_TAX)/100)))'PRICE' , sum(P.P_PRICE * (IF(ISNULL(TD.GST),0,TD.GST)/100))'GST',sum(P.P_PRICE * (IF(ISNULL(TD.OTHER_TAX),0,TD.OTHER_TAX)/100))'OTHER_TAX',sum(IF(isnull(GP.GLASS_NAME),0,GP.PRICE))'GLASSPRICE' from "
                                   + TableName.CUSTOMER_CART + " CC LEFT JOIN " + TableName.PRODUCTS
                                   + " P ON CC.P_ID=P.P_ID LEFT JOIN " + TableName.PRODUCT_SALES
                                   + " PS ON CC.P_ID = PS.P_ID LEFT JOIN " + TableName.GLASSPRICE
                                   + " GP ON GP.GLASS_NAME = CC.GLASSTYPE LEFT JOIN " + TableName.TAX_DATABASE
                                   + " TD ON TD.CATEGORY_NAME = P.P_CATEGORY WHERE CC.C_ID = ? group by CC.C_ID";

                     RowMapper<HashMap<String, Object>> mapper = new RowMapper<HashMap<String, Object>>() {

                            @Override
                            public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

                                   HashMap<String, Object> map = new HashMap<String, Object>();

                                   map.put("c_id", rs.getLong("C_ID"));
                                   map.put("totalprice", rs.getDouble("PRICE"));
                                   map.put("gst", rs.getDouble("GST"));
                                   map.put("othertax", rs.getDouble("OTHER_TAX"));
                                   map.put("glassPrice", rs.getDouble("GLASSPRICE"));

                                   return map;
                            }
                     };

                     return jdbcTemplate.query(query, mapper, c_id);

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;
       }

}
