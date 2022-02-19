package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.Carousel;
import com.example.SpectaclesWebShop.Bean.GlassPrice;
import com.example.SpectaclesWebShop.Bean.ShopDetails;
import com.example.SpectaclesWebShop.Bean.TaxDetails;

import java.util.List;

public interface ShopDetailsInterface {
    int createDatabase();
    ShopDetails getShopDetails();
    List<Carousel> getCarouselImages();
    List<GlassPrice> getGlassDetails();
    List<TaxDetails> getTaxDetails();

}
