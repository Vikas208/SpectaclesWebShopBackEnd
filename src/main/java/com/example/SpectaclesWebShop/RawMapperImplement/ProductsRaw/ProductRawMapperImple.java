package com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw;

import com.example.SpectaclesWebShop.Bean.ProductDescription;
import com.example.SpectaclesWebShop.Bean.ProductSales;
import com.example.SpectaclesWebShop.Bean.Products;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRawMapperImple implements RowMapper<Products> {
    @Override
    public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
        Products products = new Products();
        ProductDescription productDescription = new ProductDescription();
        ProductSales productSales = new ProductSales();

        products.setId(rs.getLong("P_ID"));
        products.setP_name(rs.getString("P_NAME"));
        products.setP_price(rs.getDouble("P_PRICE"));
        products.setBannerImage(rs.getString("BANNER_IMAGE"));
        products.setP_stock(rs.getInt("P_STOCK"));
        products.setTotalSales(rs.getInt("TOTALSALES"));
        products.setRating(rs.getDouble("RATING"));

        productDescription.setCompany_name(rs.getString("COMPANY_NAME"));
        productDescription.setP_description(rs.getString("P_DESCRIPTION"));
        productDescription.setP_category(rs.getString("P_CATEGORY"));
        productDescription.setP_group(rs.getString("P_GROUP"));
        productDescription.setP_frameStyle(rs.getString("P_FRAMESTYLE"));
        productDescription.setP_frameSize(rs.getString("FRAMESIZE"));
        productDescription.setColor(rs.getString("COLOR"));

        productSales.setP_id(rs.getLong("P_ID"));
        productSales.setPs_id(rs.getLong("PS_ID"));
        productSales.setSaleOff(rs.getDouble("OFF_AMOUNT"));
        productSales.setSalePercentage(rs.getDouble("PERCENTAGE"));
        productSales.setSale_start(rs.getDate("S_DATE"));
        productSales.setSale_end(rs.getDate("E_DATE"));

        products.setProductDescription(productDescription);
        products.setProductSales(productSales);
        return products;
    }
}
