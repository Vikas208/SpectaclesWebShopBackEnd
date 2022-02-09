package com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.SpectaclesWebShop.Bean.ProductImage;

import org.springframework.jdbc.core.RowMapper;

public class ProductImageRawMapper implements RowMapper<ProductImage> {

       @Override
       public ProductImage mapRow(ResultSet rs, int rowNum) throws SQLException {
              ProductImage productImage = new ProductImage();
              productImage.setId(rs.getLong("PI_ID"));
              productImage.setP_id(rs.getLong("P_ID"));
              productImage.setImages(rs.getString("IMAGE_URL"));
              return productImage;
       }

}
