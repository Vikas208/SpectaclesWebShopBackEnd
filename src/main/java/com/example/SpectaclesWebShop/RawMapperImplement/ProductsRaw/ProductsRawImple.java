package com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.SpectaclesWebShop.Bean.Products;

import org.springframework.jdbc.core.RowMapper;

public class ProductsRawImple implements RowMapper<Products> {

       @Override
       public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
              Products products = new Products();

              products.setId(rs.getLong("P_ID"));
              products.setP_name(rs.getString("P_NAME"));
              products.setP_price(rs.getDouble("P_PRICE"));
              products.setBannerImage(rs.getString("BANNER_IMAGE"));
              products.setRating(rs.getDouble("RATING"));
              return products;
       }

}
