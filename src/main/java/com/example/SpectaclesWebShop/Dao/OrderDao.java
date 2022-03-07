package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.CustomersProductsDetails;
import com.example.SpectaclesWebShop.Bean.Order;
import com.example.SpectaclesWebShop.Bean.OrderAddress;
import com.example.SpectaclesWebShop.Bean.OrderPayment;
import com.example.SpectaclesWebShop.Bean.Products;
import com.example.SpectaclesWebShop.DaoInterfaces.OrderInteface;

import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.Info.TableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderDao implements OrderInteface {

       @Autowired
       JdbcTemplate jdbcTemplate;

       public String CreateOrderTable() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.ORDER
                            + " (ORDER_ID INT AUTO_INCREMENT PRIMARY KEY,C_ID INT,ORDER_DATE DATETIME,SERVICE_ID INT,SHIPPING_CHARGES DOUBLE,ORDER_STATUS VARCHAR(50) DEFAULT('PLACED'),CONSTRAINT FOREIGN KEY(C_ID) REFERENCES "
                            + TableName.LOGIN_TABLE + " (ID),FOREIGN KEY(SERVICE_ID) REFERENCES "
                            + TableName.ORDERSERVICE + " (ID))";
       }

       public String CreateOrderAddressTable() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.ORDERADDRESS
                            + " (ID INT AUTO_INCREMENT PRIMARY KEY,ORDER_ID INT,ADDRESS1 VARCHAR(500) NOT NULL,ADDRESS2 VARCHAR(500),PHONENUMBER VARCHAR(10),CITY VARCHAR(50) NOT NULL DEFAULT('AHMEDABAD'),STATE VARCHAR(50) NOT NULL DEFAULT('GUJARAT'),PINCODE VARCHAR(6) NOT NULL,CONSTRAINT FOREIGN KEY (ORDER_ID) REFERENCES "
                            + TableName.ORDER + " (ORDER_ID))";
       }

       public String CreateOrderedProductTable() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.ORDEREDPRODUCTS
                            + " (ID INT AUTO_INCREMENT PRIMARY KEY,ORDER_ID INT NOT NULL,P_ID INT NOT NULL,ONLYFRAME BOOLEAN DEFAULT(FALSE),QTY INT DEFAULT(0),GLASSTYPE VARCHAR(10),LEFT_EYE_NO DOUBLE DEFAULT(0),RIGHT_EYE_NO DOUBLE DEFAULT(0),CONSTRAINT FOREIGN KEY(ORDER_ID)  REFERENCES "
                            + TableName.ORDER + " (ORDER_ID) ON DELETE CASCADE,FOREIGN KEY (P_ID) REFERENCES "
                            + TableName.PRODUCTS
                            + " (P_ID),CHECK(QTY!=0))";
       }

       public String CreateOrderPaymentTabel() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.ORDERPAYMENT
                            + " (ID INT AUTO_INCREMENT PRIMARY KEY,ORDER_ID INT,PAYMENT_TYPE VARCHAR(100) NOT NULL,TRANSACTION_ID VARCHAR(2000),PAYMENT_STATUS BOOLEAN DEFAULT(FALSE),CONSTRAINT FOREIGN KEY(ORDER_ID) REFERENCES "
                            + TableName.ORDER + " (ORDER_ID) ON DELETE CASCADE)";
       }

       @Override
       public void CreateDataBases() {
              try {

                     jdbcTemplate.update(CreateOrderTable());
                     jdbcTemplate.update(CreateOrderAddressTable());
                     jdbcTemplate.update(CreateOrderedProductTable());
                     jdbcTemplate.update(CreateOrderPaymentTabel());
              } catch (Exception e) {
                     e.printStackTrace();
              }
       }

       @Override
       public int createNewOrder(Order order) {
              try {
                     String query = "insert into " + TableName.ORDER + " (C_ID,ORDER_DATE) VALUES (?,?)";
                     jdbcTemplate.update(query, order.getC_id(), order.getLocalDateTime());
                     query = "select ORDER_ID FROM " + TableName.ORDER + " where c_id = ? and ORDER_DATE=?";
                     return jdbcTemplate.queryForObject(query, Integer.class, order.getC_id(),
                                   order.getLocalDateTime());
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;

       }

       @Override
       public int updateOrderDetails(Order order) {
              try {
                     String query = "update " + TableName.ORDER
                                   + " set SHIPPING_CHARGES=? ORDER_STATUS=? SERVICE_ID=? where ORDER_ID=?";
                     return jdbcTemplate.update(query, order.getShipping_charges(), order.getOrder_status(),
                                   order.getService_id(), order.getOrder_id());
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int addOrderAddress(OrderAddress orderAddress) {
              try {
                     String query = "insert into " + TableName.ORDERADDRESS
                                   + " (ORDER_ID,ADDRESS1,ADDRESS2,PHONENUMBER,CITY,STATE,PINCODE) VALUES(?,?,?,?,?,?,?)";

                     return jdbcTemplate.update(query, orderAddress.getOrder_id(), orderAddress.getAddress1(),
                                   orderAddress.getAddress2(), orderAddress.getPhonenumber(), orderAddress.getCity(),
                                   orderAddress.getState(), orderAddress.getPincode());

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int updateOrderAddress(OrderAddress orderAddress) {
              try {
                     String query = "update " + TableName.ORDERADDRESS
                                   + " set ADDRESS1=?,ADDRESS2=?,PHONENUMBER=?,CITY=?,STATE=?,PINCODE=? where ID=?";
                     return jdbcTemplate.update(query, orderAddress.getAddress1(), orderAddress.getAddress2(),
                                   orderAddress.getPhonenumber(), orderAddress.getCity(), orderAddress.getState(),
                                   orderAddress.getPincode(), orderAddress.getId());

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int addOrderProducts(CustomersProductsDetails customersProductsDetails) {
              try {
                     String query = "insert into " + TableName.ORDEREDPRODUCTS
                                   + " (ORDER_ID,P_ID,ONLYFRAME,QTY,GLASSTYPE,LEFT_EYE_NO,RIGHT_EYE_NO) VALUES(?,?,?,?,?,?,?)";
                     return jdbcTemplate.update(query, customersProductsDetails.getcommon_id(),
                                   customersProductsDetails.getP_id(), customersProductsDetails.isOnlyframe(),
                                   customersProductsDetails.getQty(), customersProductsDetails.getGlassType(),
                                   customersProductsDetails.getLeft_eye_no(),
                                   customersProductsDetails.getRight_eye_no());
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int updateOrderProducts(CustomersProductsDetails customersProductsDetails) {
              try {
                     String query = "update " + TableName.ORDEREDPRODUCTS
                                   + " set ONLYFRAME=?,QTY=?,GLASSTYPE=?,LEFT_EYE_NO=?,RIGHT_EYE_NO=? WHERE ID=?";
                     return jdbcTemplate.update(query, customersProductsDetails.isOnlyframe(),
                                   customersProductsDetails.getQty(), customersProductsDetails.getGlassType(),
                                   customersProductsDetails.getLeft_eye_no(),
                                   customersProductsDetails.getRight_eye_no(), customersProductsDetails.getId());
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int deleteOrderedProducts(long product_id) {
              try {
                     String query = "delete from " + TableName.ORDEREDPRODUCTS + " where ID=?";
                     return jdbcTemplate.update(query, product_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int addOrderPaymentDetails(OrderPayment orderPayment) {
              try {
                     String query = "insert into " + TableName.ORDERPAYMENT + " (ORDER_ID) values(?,?)";
                     return jdbcTemplate.update(query, orderPayment.getOrder_id(), orderPayment.getPayment_type());
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int updatePaymentStatus(long payment_id, String status) {
              try {
                     String query = "update " + TableName.ORDERPAYMENT + " set PAYMENT_STATUS=? WHERE ID=?";
                     return jdbcTemplate.update(query, status, payment_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int CancelOrder(long order_id) {
              try {
                     String query = "delete from " + TableName.ORDER + " where ORDER_ID=?";
                     return jdbcTemplate.update(query, order_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return 0;
       }

       @Override
       public List<Order> getCustomerOrders(long c_id) {
              try {
                     String query = "SELECT * FROM " + TableName.ORDER + " WHERE C_ID = ?";
                     RowMapper<Order> mapper = new RowMapper<Order>() {
                            @Override
                            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   Order order = new Order();
                                   order.setOrder_id(rs.getLong("ORDER_ID"));
                                   order.setC_id(rs.getLong("C_ID"));
                                   order.setOrder_status(rs.getString("ORDER_STATUS"));
                                   order.setLocalDateTime(rs.getObject("ORDER_DATE", LocalDateTime.class));
                                   order.setShipping_charges(rs.getDouble("SHIPPING_CHARGES"));

                                   return order;
                            }
                     };

                     List<Order> orders = jdbcTemplate.query(query, mapper, c_id);

                     return orders;
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;
       }

       @Override
       public OrderAddress getOldAddress(long c_id) {
              try {
                     String query = "select OA.ID,OA.ADDRESS1,OA.ADDRESS2,OA.CITY,OA.STATE,OA.PHONENUMBER,OA.PINCODE FROM "
                                   + TableName.ORDER + " CO LEFT JOIN " + TableName.ORDERADDRESS
                                   + " OA ON CO.ORDER_ID = OA.ORDER_ID WHERE CO.C_ID=? ORDER BY unix_timestamp(CO.ORDER_DATE) DESC limit 1";

                     RowMapper<OrderAddress> oMapper = new RowMapper<OrderAddress>() {

                            @Override
                            public OrderAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   OrderAddress orderAddress = new OrderAddress();
                                   orderAddress.setId(rs.getLong("ID"));
                                   orderAddress.setAddress1(rs.getString("ADDRESS1"));
                                   orderAddress.setAddress2(rs.getString("ADDRESS2"));
                                   orderAddress.setCity(rs.getString("CITY"));
                                   orderAddress.setState(rs.getString("STATE"));
                                   orderAddress.setPhonenumber(rs.getString("PHONENUMBER"));
                                   orderAddress.setPincode(rs.getString("PINCODE"));

                                   return orderAddress;
                            }

                     };
                     return jdbcTemplate.queryForObject(query, oMapper, c_id);

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return new OrderAddress();
       }

       @Override
       public List<HashMap<String, Object>> getOrderedDetails(long c_id) {
              try {
                     String query = "select CC.C_ID,P.P_ID,P.P_NAME,(CC.QTY * ((P.P_PRICE - IF(PS.E_DATE < CURRENT_DATE(),0,PS.OFF_AMOUNT) - (P.P_PRICE * IF(PS.E_DATE < CURRENT_DATE(),0,PS.PERCENTAGE) / 100) )+ IF(isnull(GP.GLASS_NAME),0,GP.PRICE)+ (P.P_PRICE * (IF(isnull(TD.GST),0,TD.GST)/100))+ (P.P_PRICE * IF(ISNULL(TD.OTHER_TAX),0,TD.OTHER_TAX)/100)))'PRICE' , (P.P_PRICE * (IF(ISNULL(TD.GST),0,TD.GST)/100))'GST',(P.P_PRICE * (IF(ISNULL(TD.OTHER_TAX),0,TD.OTHER_TAX)/100))'OTHER_TAX' from "
                                   + TableName.CUSTOMER_CART + " CC LEFT JOIN " + TableName.PRODUCTS
                                   + " P ON CC.P_ID=P.P_ID LEFT JOIN " + TableName.PRODUCT_SALES
                                   + " PS ON CC.P_ID = PS.P_ID LEFT JOIN " + TableName.GLASSPRICE
                                   + " GP ON GP.GLASS_NAME = CC.GLASSTYPE LEFT JOIN " + TableName.TAX_DATABASE
                                   + " TD ON TD.CATEGORY_NAME = P.P_CATEGORY WHERE CC.C_ID = ?";
                     RowMapper<HashMap<String, Object>> mapper = new RowMapper<HashMap<String, Object>>() {

                            @Override
                            public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

                                   HashMap<String, Object> map = new HashMap<>();
                                   map.put("ProductId", rs.getLong("P_ID"));
                                   map.put("ProductName", rs.getString("P_NAME"));
                                   map.put("TotalPrice", rs.getDouble("PRICE"));
                                   map.put("GST", rs.getDouble("GST"));
                                   map.put("OTHERTAX", rs.getDouble("OTHER_TAX"));
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
