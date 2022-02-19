package com.example.SpectaclesWebShop.RawMapperImplement.CustomerProductsRaw;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.SpectaclesWebShop.Bean.CustomersProductsDetails;
import com.example.SpectaclesWebShop.Bean.ProductDescription;
import com.example.SpectaclesWebShop.Bean.ProductSales;
import com.example.SpectaclesWebShop.Bean.Products;

import org.springframework.jdbc.core.RowMapper;

public class CustomerProductRawImplement implements RowMapper<CustomersProductsDetails> {

       @Override
       public CustomersProductsDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

              CustomersProductsDetails customersProductsDetails = new CustomersProductsDetails();
              Products products = new Products();
              ProductDescription productDescription = new ProductDescription();
              ProductSales productSales = new ProductSales();

              customersProductsDetails.setId(rs.getLong("CC_ID"));
              customersProductsDetails.setC_id(rs.getLong("C_ID"));
              customersProductsDetails.setP_id(rs.getLong("P_ID"));
              customersProductsDetails.setQty(rs.getInt("QTY"));
              customersProductsDetails.setOnlyframe(rs.getBoolean("ONLYFRAME"));
              customersProductsDetails.setLeft_eye_no(rs.getDouble("LEFT_EYE_NO"));
              customersProductsDetails.setRight_eye_no(rs.getDouble("RIGHT_EYE_NO"));
              customersProductsDetails.setGlassType(rs.getString("GLASSTYPE"));

              products.setId(rs.getLong("P_ID"));
              products.setP_name(rs.getString("P_NAME"));
              products.setP_price(rs.getDouble("P_PRICE"));
              products.setBannerImage(rs.getString("BANNER_IMAGE"));
              productDescription.setP_category(rs.getString("P_CATEGORY"));

              productSales.setSaleOff(rs.getDouble("OFF_AMOUNT"));
              productSales.setSalePercentage(rs.getDouble("PERCENTAGE"));

              products.setProductDescription(productDescription);

              products.setProductSales(productSales);
              products.setP_stock(rs.getInt("P_STOCK"));
              customersProductsDetails.setProducts(products);
              return customersProductsDetails;
       }

}
