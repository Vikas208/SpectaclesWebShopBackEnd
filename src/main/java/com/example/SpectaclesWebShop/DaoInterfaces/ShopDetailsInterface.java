package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.Carousel;
import com.example.SpectaclesWebShop.Bean.GlassType;
import com.example.SpectaclesWebShop.Bean.ShippingCharge;
import com.example.SpectaclesWebShop.Bean.ShopDetails;

import java.util.List;

public interface ShopDetailsInterface {
    int createDatabase();

    ShopDetails getShopDetails();

    List<Carousel> getCarouselImages();

    ShippingCharge getShippingCharge();

    List<GlassType> getGlassPricing();

}
