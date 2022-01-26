package com.example.SpectaclesWebShop.RawMapperImplement.ShopDetailsRaw;

import com.example.SpectaclesWebShop.Bean.Address;
import com.example.SpectaclesWebShop.Bean.ShopDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShopDetailsRawMapperImple implements RowMapper<ShopDetails> {
    @Override
    public ShopDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShopDetails shopDetails = new ShopDetails();
        Address address = new Address();

        shopDetails.setShopName(rs.getString(2));

        address.setAddress(rs.getString(3));
        address.setCity(rs.getString(4));
        address.setPinCode(rs.getString(5));

        shopDetails.setPhoneNumber(rs.getString(6));
        shopDetails.setMailId(rs.getString(7));

        shopDetails.setAddress(address);
        return shopDetails;
    }
}
