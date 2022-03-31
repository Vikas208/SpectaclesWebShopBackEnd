package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.Bean.GlassType;
import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.Bean.Order;
import com.example.SpectaclesWebShop.Bean.OrderAddress;
import com.example.SpectaclesWebShop.Bean.OrderPayment;
import com.example.SpectaclesWebShop.Bean.OrderedProducts;
import com.example.SpectaclesWebShop.Bean.ProductDescription;
import com.example.SpectaclesWebShop.Bean.Products;
import com.example.SpectaclesWebShop.DaoInterfaces.OrderInteface;

import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.Info.TableName;
import com.example.SpectaclesWebShop.Service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderDao implements OrderInteface {

       @Autowired
       JdbcTemplate jdbcTemplate;

       @Autowired
       ProductsDao productsDao;

       @Autowired
       LoginDao loginDao;

       @Autowired
       EmailService emailService;

       @Autowired
       ShopDetailsDao shopDetailsDao;

       public String CreateOrderTable() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.ORDER
                            + " (ORDER_ID INT AUTO_INCREMENT PRIMARY KEY,C_ID INT,ORDER_DATE DATETIME,SERVICE_ID INT,SHIPPING_CHARGES DOUBLE,ORDER_STATUS VARCHAR(50) DEFAULT('PLACED'),CONSTRAINT FOREIGN KEY(C_ID) REFERENCES "
                            + TableName.LOGIN_TABLE + " (ID),FOREIGN KEY(SERVICE_ID) REFERENCES "
                            + TableName.ORDERSERVICE + " (ID))";
       }

       public String CreateOrderAddressTable() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.ORDERADDRESS
                            + " (ID INT AUTO_INCREMENT PRIMARY KEY,ORDER_ID INT,ADDRESS1 VARCHAR(500) NOT NULL,ADDRESS2 VARCHAR(500),PHONENUMBER VARCHAR(10),CITY VARCHAR(50) NOT NULL DEFAULT('AHMEDABAD'),STATE VARCHAR(50) NOT NULL DEFAULT('GUJARAT'),PINCODE VARCHAR(6) NOT NULL,CONSTRAINT FOREIGN KEY (ORDER_ID) REFERENCES "
                            + TableName.ORDER + " (ORDER_ID) ON DELETE CASCADE)";
       }

       public String CreateOrderedProductTable() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.ORDEREDPRODUCTS
                            + " (ID INT AUTO_INCREMENT PRIMARY KEY,ORDER_ID INT NOT NULL,P_ID INT NOT NULL,ONLYFRAME BOOLEAN DEFAULT(FALSE),QTY INT DEFAULT(1),GLASSTYPE VARCHAR(10),LEFT_EYE_NO DOUBLE DEFAULT(0),RIGHT_EYE_NO DOUBLE DEFAULT(0),TOTAL_PRICE DOUBLE NOT NULL,GST DOUBLE DEFAULT(0),OTHERTAX DOUBLE DEFAULT(0),SALE DOUBLE DEFAULT(0),GLASSPRICE DOUBLE DEFAULT(0),CONSTRAINT FOREIGN KEY(ORDER_ID)  REFERENCES "
                            + TableName.ORDER + " (ORDER_ID) ON DELETE CASCADE,FOREIGN KEY (P_ID) REFERENCES "
                            + TableName.PRODUCTS
                            + " (P_ID),CHECK(QTY!=0))";
       }

       public String CreateOrderPaymentTabel() {
              return "CREATE TABLE IF NOT EXISTS " + TableName.ORDERPAYMENT
                            + " (ID INT AUTO_INCREMENT PRIMARY KEY,ORDER_ID INT,PAYMENT_TYPE VARCHAR(100) NOT NULL,TOTAL_AMOUNT DOUBLE NOT NULL,TRANSACTION_ID VARCHAR(2000),PAYMENT_STATUS BOOLEAN DEFAULT(FALSE),CONSTRAINT FOREIGN KEY(ORDER_ID) REFERENCES "
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
                     Date date = new Date();
                     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     String currentDateTime = format.format(date);
                     System.out.println(order.getC_id());
                     System.out.println(currentDateTime);
                     String query = "insert into " + TableName.ORDER + " (C_ID,ORDER_DATE) VALUES (?,?)";
                     jdbcTemplate.update(query, order.getC_id(), currentDateTime);
                     query = "select ORDER_ID FROM " + TableName.ORDER + " where c_id = ? and ORDER_DATE=?";
                     return jdbcTemplate.queryForObject(query, Integer.class, order.getC_id(), currentDateTime);
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
       public int addOrderProducts(ArrayList<OrderedProducts> orderedProducts) {
              try {
                     String query = "insert into " + TableName.ORDEREDPRODUCTS
                                   + " (ORDER_ID,P_ID,ONLYFRAME,QTY,GLASSTYPE,LEFT_EYE_NO,RIGHT_EYE_NO,TOTAL_PRICE,GST,OTHERTAX,SALE,GLASSPRICE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                     int result = 0;

                     for (int i = 0; i < orderedProducts.size(); ++i) {

                            OrderedProducts oProducts = orderedProducts.get(i);
                            productsDao.updateProductStockAndSales(oProducts.getP_id(), oProducts.getQty());

                            result += jdbcTemplate.update(query, oProducts.getOrder_id(), oProducts.getP_id(),
                                          oProducts.isOnlyframe(), oProducts.getQty(), oProducts.getGlassType(),
                                          oProducts.getLeft_eye_no(),
                                          oProducts.getRight_eye_no(), oProducts.getTotalPrice(), oProducts.getGst(),
                                          oProducts.getOtherTax(), oProducts.getSale(), oProducts.getGlassPrice());
                     }
                     return result;

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

       @Override
       public int updateOrderProducts(OrderedProducts orderedProducts) {
              try {
                     String query = "update " + TableName.ORDEREDPRODUCTS
                                   + " set ONLYFRAME=?,QTY=?,GLASSTYPE=?,LEFT_EYE_NO=?,RIGHT_EYE_NO=? WHERE ID=?";
                     return jdbcTemplate.update(query, orderedProducts.isOnlyframe(),
                                   orderedProducts.getQty(), orderedProducts.getGlassType(),
                                   orderedProducts.getLeft_eye_no(),
                                   orderedProducts.getRight_eye_no(), orderedProducts.getId());
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
                     String query = "insert into " + TableName.ORDERPAYMENT
                                   + " (ORDER_ID,PAYMENT_TYPE,TOTAL_AMOUNT) values(?,?,?)";
                     return jdbcTemplate.update(query, orderPayment.getOrder_id(), orderPayment.getPayment_type(),
                                   orderPayment.getTotal_amount());
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
                     // Reset Stock and TotalSales
                     String query2 = "select QTY,P_ID FROM " + TableName.ORDEREDPRODUCTS + " WHERE ORDER_ID=?";
                     RowMapper<OrderedProducts> oMapper = new RowMapper<OrderedProducts>() {
                            @Override
                            public OrderedProducts mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   OrderedProducts orderedProducts = new OrderedProducts();
                                   orderedProducts.setQty(rs.getInt("QTY"));
                                   orderedProducts.setP_id(rs.getLong("P_ID"));
                                   return orderedProducts;
                            }
                     };
                     List<OrderedProducts> orderedProducts = jdbcTemplate.query(query2, oMapper, order_id);
                     for (int i = 0; i < orderedProducts.size(); ++i) {
                            productsDao.updateProductStockAndSales(orderedProducts.get(i).getP_id(),
                                          -orderedProducts.get(i).getQty());
                     }
                     return jdbcTemplate.update(query, order_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return 0;
       }

       @Override
       public List<Order> getCustomerOrders(long c_id) {
              try {
                     String query = "SELECT * FROM " + TableName.ORDER + " WHERE C_ID = ? ORDER BY  ORDER_DATE DESC";
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

                     for (int i = 0; i < orders.size(); ++i) {
                            long order_id = orders.get(i).getOrder_id();
                            OrderAddress orderAddress = getOrderAddress(order_id);
                            List<OrderedProducts> orderedProducts = getOrderedProducts(order_id);
                            OrderPayment orderPayment = getOrderPayment(order_id);
                            orders.get(i).setOrderAddress(orderAddress);
                            orders.get(i).setOrderedProducts(orderedProducts);
                            orders.get(i).setOrderPayment(orderPayment);
                     }
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

       // fetch products from cart
       @Override
       public List<HashMap<String, Object>> getOrderedDetails(long c_id) {
              try {
                     String query = "select CC.C_ID,P.P_ID,P.P_NAME,P.P_PRICE,CC.QTY,CC.ONLYFRAME,CC.LEFT_EYE_NO,CC.RIGHT_EYE_NO,CC.GLASSTYPE,(CC.QTY * ((P.P_PRICE - IF(PS.E_DATE <CURRENT_DATE(),0,PS.OFF_AMOUNT) - (P.P_PRICE * IF(PS.E_DATE < CURRENT_DATE(),0,PS.PERCENTAGE/100)) )+ IF(isnull(GP.GLASS_NAME),0,GP.PRICE)+ (P.P_PRICE * (IF(isnull(TD.GST),0,TD.GST)/100))+ (P.P_PRICE * IF(ISNULL(TD.OTHER_TAX),0,TD.OTHER_TAX)/100)))'PRICE' , (  CC.QTY * P.P_PRICE * (IF(ISNULL(TD.GST),0,TD.GST)/100))'GST',(CC.QTY*P.P_PRICE * (IF(ISNULL(TD.OTHER_TAX),0,TD.OTHER_TAX)/100))'OTHER_TAX',IF(PS.E_DATE<current_date(),0,PS.OFF_AMOUNT+(P_PRICE*(PS.PERCENTAGE/100)))'SALE',IF(isnull(GP.GLASS_NAME),0,GP.PRICE)'GLASSPRICE' from "
                                   + TableName.CUSTOMER_CART + " CC LEFT JOIN " + TableName.PRODUCTS
                                   + " P ON CC.P_ID=P.P_ID LEFT JOIN " + TableName.PRODUCT_SALES
                                   + " PS ON CC.P_ID = PS.P_ID LEFT JOIN " + TableName.GLASSPRICE
                                   + " GP ON GP.GLASS_NAME = CC.GLASSTYPE LEFT JOIN " + TableName.TAX_DATABASE
                                   + " TD ON TD.CATEGORY_NAME = P.P_CATEGORY WHERE CC.C_ID = ?";
                     RowMapper<HashMap<String, Object>> mapper = new RowMapper<HashMap<String, Object>>() {

                            @Override
                            public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

                                   HashMap<String, Object> map = new HashMap<>();
                                   map.put("p_id", rs.getLong("P_ID"));
                                   map.put("p_name", rs.getString("P_NAME"));
                                   map.put("p_price", rs.getDouble("P_PRICE"));
                                   map.put("totalPrice", rs.getDouble("PRICE"));
                                   map.put("gst", rs.getDouble("GST"));
                                   map.put("otherTax", rs.getDouble("OTHER_TAX"));
                                   map.put("qty", rs.getInt("QTY"));
                                   map.put("onlyframe", rs.getBoolean("ONLYFRAME"));
                                   map.put("left_eye_no", rs.getDouble("LEFT_EYE_NO"));
                                   map.put("right_eye_no", rs.getDouble("RIGHT_EYE_NO"));
                                   map.put("glassType", rs.getString("GLASSTYPE"));
                                   map.put("sale", rs.getString("SALE"));
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

       // fetch products from products for show now only one product
       @Override
       public HashMap<String, Object> getOrderedProduct(long p_id, long qty, String glassType) {
              try {
                     String query = "select P.P_ID,P_NAME,BANNER_IMAGE,P_CATEGORY,P_STOCK,P_PRICE,(?*(P_PRICE - IF(PS.E_DATE<current_date(),0,PS.OFF_AMOUNT+(P_PRICE*(PS.PERCENTAGE/100))) + if(isnull(GLASS_NAME),0,GP.PRICE) + IF(isnull(TD.CATEGORY_NAME),0,((P_PRICE*(TD.GST/100))+(P_PRICE*(TD.OTHER_TAX/100))))))'PRICE', (?*P.P_PRICE * (IF(ISNULL(TD.GST),0,TD.GST)/100))'GST',(?*P.P_PRICE * (IF(ISNULL(TD.OTHER_TAX),0,TD.OTHER_TAX)/100))'OTHER_TAX',IF(PS.E_DATE<current_date(),0,PS.OFF_AMOUNT+(P_PRICE*(PS.PERCENTAGE/100)))'SALE',if(isnull(GLASS_NAME),0,GP.PRICE) 'GLASSPRICE' FROM "
                                   + TableName.PRODUCTS + " P LEFT JOIN " + TableName.PRODUCT_SALES
                                   + " PS ON P.P_ID=PS.P_ID LEFT JOIN " + TableName.GLASSPRICE
                                   + " GP ON GP.GLASS_NAME=? LEFT JOIN " + TableName.TAX_DATABASE
                                   + " TD ON TD.CATEGORY_NAME = P.P_CATEGORY WHERE P.P_ID=?";
                     RowMapper<HashMap<String, Object>> mapper = new RowMapper<HashMap<String, Object>>() {

                            @Override
                            public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

                                   HashMap<String, Object> map = new HashMap<>();
                                   map.put("p_id", rs.getLong("P_ID"));
                                   map.put("p_name", rs.getString("P_NAME"));
                                   map.put("p_price", rs.getDouble("P_PRICE"));
                                   map.put("totalPrice", rs.getDouble("PRICE"));
                                   map.put("gst", rs.getDouble("GST"));
                                   map.put("otherTax", rs.getDouble("OTHER_TAX"));
                                   map.put("sale", rs.getDouble("SALE"));
                                   map.put("glassPrice", rs.getDouble("GLASSPRICE"));
                                   map.put("qty", qty);

                                   return map;
                            }

                     };

                     return jdbcTemplate.queryForObject(query, mapper, qty, qty, qty,
                                   glassType, p_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;
       }

       public OrderAddress getOrderAddress(long order_id) {

              try {
                     String fetchAddress = "SELECT * FROM " + TableName.ORDER + " O LEFT JOIN " + TableName.ORDERADDRESS
                                   + " OA ON O.order_id=OA.order_id where O.order_id=?";
                     RowMapper<OrderAddress> address = new RowMapper<OrderAddress>() {

                            @Override
                            public OrderAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   OrderAddress orderAddress = new OrderAddress();
                                   orderAddress.setAddress1(rs.getString("ADDRESS1"));
                                   orderAddress.setAddress2(rs.getString("ADDRESS2"));
                                   orderAddress.setCity(rs.getString("CITY"));
                                   orderAddress.setState(rs.getString("STATE"));
                                   orderAddress.setPhonenumber(rs.getString("PHONENUMBER"));
                                   orderAddress.setPincode(rs.getString("PINCODE"));

                                   return orderAddress;
                            }

                     };
                     return jdbcTemplate.queryForObject(fetchAddress, address, order_id);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;
       }

       public List<OrderedProducts> getOrderedProducts(long order_id) {
              try {

                     String fetchProducts = "SELECT * FROM " + TableName.ORDER + " O LEFT JOIN "
                                   + TableName.ORDEREDPRODUCTS
                                   + " OP ON O.order_id=OP.order_id LEFT JOIN (SELECT P_ID,P_NAME,P_CATEGORY,P_FRAMESTYLE,FRAMESIZE,P_GROUP,COLOR,COMPANY_NAME,P_PRICE FROM PRODUCTS)P ON P.P_ID=OP.P_ID where O.order_id=?";

                     RowMapper<OrderedProducts> products = new RowMapper<OrderedProducts>() {

                            @Override
                            public OrderedProducts mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   OrderedProducts orderedProducts = new OrderedProducts();
                                   Products products = new Products();
                                   ProductDescription productDescription = new ProductDescription();

                                   orderedProducts.setOnlyframe(rs.getBoolean("ONLYFRAME"));
                                   orderedProducts.setQty(rs.getInt("QTY"));
                                   orderedProducts.setGlassType(rs.getString("GLASSTYPE"));
                                   orderedProducts.setLeft_eye_no(rs.getString("LEFT_EYE_NO"));
                                   orderedProducts.setRight_eye_no(rs.getString("RIGHT_EYE_NO"));
                                   orderedProducts.setTotalPrice(rs.getDouble("TOTAL_PRICE"));
                                   orderedProducts.setGst(rs.getDouble("GST"));
                                   orderedProducts.setOtherTax(rs.getDouble("OTHERTAX"));
                                   orderedProducts.setSale(rs.getDouble("SALE"));
                                   orderedProducts.setGlassPrice(rs.getDouble("GLASSPRICE"));

                                   products.setP_name(rs.getString("P_NAME"));
                                   products.setP_price(rs.getDouble("P_PRICE"));
                                   productDescription.setCompany_name(rs.getString("COMPANY_NAME"));
                                   productDescription.setP_category(rs.getString("P_CATEGORY"));
                                   productDescription.setP_group(rs.getString("P_GROUP"));
                                   productDescription.setP_frameStyle(rs.getString("P_FRAMESTYLE"));
                                   productDescription.setP_frameSize(rs.getString("FRAMESIZE"));
                                   productDescription.setColor(rs.getString("COLOR"));

                                   products.setProductDescription(productDescription);

                                   orderedProducts.setProducts(products);
                                   return orderedProducts;
                            }

                     };
                     return jdbcTemplate.query(fetchProducts, products, order_id);

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;
       }

       public OrderPayment getOrderPayment(long order_id) {
              try {
                     String fetchPayment = "SELECT * FROM " + TableName.ORDER + " O LEFT JOIN " + TableName.ORDERPAYMENT
                                   + " OP ON O.order_id=OP.order_id where O.order_id=?";
                     RowMapper<OrderPayment> payment = new RowMapper<OrderPayment>() {

                            @Override
                            public OrderPayment mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   OrderPayment orderPayment = new OrderPayment();
                                   orderPayment.setPayment_type(rs.getString("PAYMENT_TYPE"));
                                   orderPayment.setTotal_amount(rs.getDouble("TOTAL_AMOUNT"));
                                   orderPayment.setTransactionid(rs.getString("TRANSACTION_ID"));
                                   orderPayment.setPayment_status(rs.getBoolean("PAYMENT_STATUS"));
                                   return orderPayment;
                            }

                     };
                     return jdbcTemplate.queryForObject(fetchPayment, payment, order_id);

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;
       }

       public Order getOrder(long order_id) {
              try {
                     String fetchOrder = "SELECT * FROM " + TableName.ORDER + " where order_id=?";
                     RowMapper<Order> orderMapper = new RowMapper<Order>() {
                            @Override
                            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   Order order = new Order();

                                   order.setOrder_id(rs.getLong("ORDER_ID"));
                                   order.setC_id(rs.getLong("C_ID"));
                                   order.setLocalDateTime(rs.getObject("ORDER_DATE", LocalDateTime.class));
                                   order.setShipping_charges(rs.getDouble("SHIPPING_CHARGES"));
                                   order.setOrder_status(rs.getString("ORDER_STATUS"));
                                   return order;

                            }
                     };
                     return jdbcTemplate.queryForObject(fetchOrder, orderMapper, order_id);

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return null;
       }

       public boolean sendInvoice(long order_id) {
              try {
                     OrderAddress orderAddress = getOrderAddress(order_id);

                     OrderPayment orderPayment = getOrderPayment(order_id);

                     List<OrderedProducts> orderedProducts = getOrderedProducts(order_id);

                     Order order = getOrder(order_id);

                     Login login = loginDao.findById(order.getC_id());

                     return emailService.sendInvoiceMail(order, orderAddress, orderPayment, orderedProducts, login);

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return false;
       }

       @Override
       public int CheckOrderedProductData(long p_id, int qty, String glassType, boolean onlyframe) {
              try {
                     String query = "SELECT P_STOCK,P_CATEGORY FROM " + TableName.PRODUCTS + " WHERE P_ID=?";
                     RowMapper<HashMap<String, Object>> productMapper = new RowMapper<HashMap<String, Object>>() {

                            @Override
                            public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                                   HashMap<String, Object> obj = new HashMap<String, Object>();
                                   obj.put("category", rs.getString("P_CATEGORY"));
                                   obj.put("stock", rs.getInt("P_STOCK"));
                                   return obj;
                            }
                     };
                     HashMap<String, Object> product = jdbcTemplate.queryForObject(query, productMapper, p_id);
                     List<GlassType> glassTypes = shopDetailsDao.getGlassPricing();

                     int stock = (int) product.get("stock");

                     String category = (String) product.get("category");

                     if (qty > stock) {
                            return Code.INVALIDDATA;
                     } else if (onlyframe == false && !category.toLowerCase().equalsIgnoreCase("lens")
                                   && !category.toLowerCase().equalsIgnoreCase("sun glass")) {
                            boolean found = false;
                            for (GlassType type : glassTypes) {

                                   if (type.getGlass_name().toLowerCase().equalsIgnoreCase(glassType)) {
                                          found = true;
                                   }
                            }
                            if (!found) {
                                   return Code.INVALIDDATA;
                            }
                     }

                     return Code.SUCCESS;

              } catch (Exception e) {
                     e.printStackTrace();
              }
              return Code.ERROR_CODE;
       }

}
