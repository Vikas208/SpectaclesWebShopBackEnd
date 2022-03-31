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
            String getTotalOrders = "SELECT COUNT(ORDER_ID) FROM " + TableName.ORDER;
            String getTotalAmountOfProductSale = "SELECT SUM(TOTAL_AMOUNT) FROM " + TableName.ORDERPAYMENT;
            String getTotalProducts = "SELECT COUNT(P_ID) FROM " + TableName.PRODUCTS;
            String getTotalOutOfStockProducts = "SELECT COUNT(P_ID) FROM " + TableName.PRODUCTS + " WHERE P_STOCK=0";

            int totalSales = jdbcTemplate.queryForObject(getTotalSales, Integer.class);
            int totalUsers = jdbcTemplate.queryForObject(getTotalUsers, Integer.class);
            int totalOrders = jdbcTemplate.queryForObject(getTotalOrders, Integer.class);
            int totalRevenue = jdbcTemplate.queryForObject(getTotalAmountOfProductSale, Integer.class);
            int totalProducts = jdbcTemplate.queryForObject(getTotalProducts, Integer.class);
            int totalOutOfStockProducts = jdbcTemplate.queryForObject(getTotalOutOfStockProducts, Integer.class);

            HashMap<String, Object> obj = new HashMap<String, Object>();
            obj.put("totalSales", totalSales);
            obj.put("totalUsers", totalUsers);
            obj.put("totalOrders", totalOrders);
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
                    + TableName.ORDER + " GROUP BY MONTHNAME(ORDER_DATE),YEAR(ORDER_DATE)";
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

}
