package com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw;

import com.example.SpectaclesWebShop.Bean.Products;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsRawMapperImple implements RowMapper<Products> {
    @Override
    public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
        Products products = new Products();
        products.setId(rs.getInt("P_ID"));
        products.setP_name(rs.getString("P_NAME"));
        products.setP_description(rs.getString("P_DESCRIPTION"));
        products.setP_price(rs.getDouble("P_PRICE"));
        products.setP_category(rs.getString("P_CATEGORY"));
        products.setP_group(rs.getString("P_GROUP"));
        products.setP_frameStyle(rs.getString("P_FRAMESTYLE"));
        products.setP_bannerImage(rs.getString("BANNER_IMAGE"));
        products.setP_stock(rs.getInt("P_STOCK"));
        products.setTotalSales(rs.getInt("TOTALSALES"));
        products.setRating(rs.getDouble("RATING"));
        return products;
    }
}
