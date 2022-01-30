package com.example.SpectaclesWebShop.RawMapperImplement.ShopDetailsRaw;

import com.example.SpectaclesWebShop.Bean.Carousel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarouselRawMapperImple implements RowMapper<Carousel> {
    @Override
    public Carousel mapRow(ResultSet rs, int rowNum) throws SQLException {
        Carousel carousel = new Carousel();
        carousel.setImages(rs.getString(1));
        return carousel;
    }
}
