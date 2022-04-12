package com.example.SpectaclesWebShop.Dao;

import com.example.SpectaclesWebShop.DaoInterfaces.DashBoardInterface;
import com.example.SpectaclesWebShop.Info.TableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

@Repository
public class DashBoardDao implements DashBoardInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductsDao productsDao;

    @Override
    public HashMap<String, Object> DashBoardData() {
        try {
            String getTotalSales = "SELECT SUM(TOTALSALES) FROM " + TableName.PRODUCTS;
            String getTotalUsers = "SELECT COUNT(MAILID) FROM  " + TableName.LOGIN_TABLE;
            String getTotalPlacedOrders = "SELECT COUNT(ORDER_ID) FROM " + TableName.ORDER +" WHERE ORDER_STATUS='PLACED'";
            String getTotalShippedOrders = "SELECT COUNT(ORDER_ID) FROM " + TableName.ORDER +" WHERE ORDER_STATUS='SHIPPED'";
            String getTotalDeliveredOrders = "SELECT COUNT(ORDER_ID) FROM " + TableName.ORDER +" WHERE ORDER_STATUS='DELIVERED'";
            String getTotalCancelOrders = "SELECT COUNT(ORDER_ID) FROM " + TableName.ORDER +" WHERE ORDER_STATUS='CANCELED'";

            String getTotalAmountOfProductSale = "SELECT SUM(TOTAL_AMOUNT) FROM " + TableName.ORDERPAYMENT +" OP LEFT JOIN (SELECT ORDER_ID,ORDER_STATUS FROM "+TableName.ORDER+" ) O ON O.ORDER_ID=OP.ORDER_ID WHERE ORDER_STATUS!='CANCELED'";
            String getTotalProducts = "SELECT COUNT(P_ID) FROM " + TableName.PRODUCTS;
            String getTotalOutOfStockProducts = "SELECT COUNT(P_ID) FROM " + TableName.PRODUCTS + " WHERE P_STOCK=0";

            Object totalSales = jdbcTemplate.queryForObject(getTotalSales,Integer.class);
            Object totalUsers = jdbcTemplate.queryForObject(getTotalUsers, Integer.class);
            Object totalPlacedOrders = jdbcTemplate.queryForObject(getTotalPlacedOrders, Integer.class);
            Object totalShippedOrders = jdbcTemplate.queryForObject(getTotalShippedOrders, Integer.class);
            Object totalDeliveredOrders = jdbcTemplate.queryForObject(getTotalDeliveredOrders, Integer.class);
            Object totalCanceledOrders = jdbcTemplate.queryForObject(getTotalCancelOrders, Integer.class);
            Object totalRevenue = jdbcTemplate.queryForObject(getTotalAmountOfProductSale, Integer.class);
            Object totalProducts = jdbcTemplate.queryForObject(getTotalProducts, Integer.class);
            Object totalOutOfStockProducts = jdbcTemplate.queryForObject(getTotalOutOfStockProducts, Integer.class);

            HashMap<String, Object> obj = new HashMap<String, Object>();
            obj.put("totalSales", totalSales);
            obj.put("totalUsers", totalUsers);
            obj.put("totalPlacedOrders", totalPlacedOrders);
            obj.put("totalShippedOrders", totalShippedOrders);
            obj.put("totalDeliveredOrders", totalDeliveredOrders);
            obj.put("totalCanceledOrders", totalCanceledOrders);
            obj.put("totalRevenue", totalRevenue);
            obj.put("totalProducts", totalProducts);
            obj.put("totalOutOfStockProducts", totalOutOfStockProducts);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<String, Object> DashBoradChartsData() {
        try {
            String orderChart = "SELECT MONTHNAME(ORDER_DATE)'month',YEAR(ORDER_DATE)'year',COUNT(ORDER_ID)'orders' FROM "
                    + TableName.ORDER + " WHERE ORDER_STATUS!='CANCELED' GROUP BY MONTHNAME(ORDER_DATE),YEAR(ORDER_DATE)";
            RowMapper<HashMap<String, Object>> obj = new RowMapper<HashMap<String, Object>>() {

                @Override
                public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    HashMap<String, Object> obj = new HashMap<String, Object>();
                    obj.put("month", rs.getString("month"));
                    obj.put("year", rs.getString("year"));
                    obj.put("orders", rs.getInt("orders"));
                    return obj;
                }

            };
            List<HashMap<String, Object>> orderData = jdbcTemplate.query(orderChart, obj);

            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("OrderData", orderData);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<String, Object> getNotification()  {
        try {
            String fetchOrder = "SELECT COUNT(*) FROM "+TableName.ORDER +" WHERE DATE(ORDER_DATE)=current_date() AND ORDER_STATUS='PLACED'";
            String fetchNewUsers = "SELECT COUNT(*) FROM "+TableName.LOGIN_TABLE+" WHERE DATE(JOINTIME)=current_date()";
            String fetchOutOfStockProducts = "SELECT COUNT(*) FROM "+TableName.PRODUCTS+" WHERE P_STOCK=0";
            String fetchNewReviews = "SELECT COUNT(*) FROM "+TableName.REVIEWS+" WHERE DATE(REVIEW_TIME)=current_date()";

            String[] queries = new String[]{fetchOrder,fetchNewReviews,fetchNewUsers,fetchOutOfStockProducts};
            int[] res = new int[queries.length];
            for(int i=0;i<queries.length;++i){
                res[i] =  jdbcTemplate.queryForObject(queries[i],Integer.class);
            }

            HashMap<String,Object> notifications = new HashMap<String,Object>();
            notifications.put("Orders",res[0]);
            notifications.put("reviews",res[1]);
            notifications.put("users",res[2]);
            notifications.put("outOfStock",res[3]);

            return notifications;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
