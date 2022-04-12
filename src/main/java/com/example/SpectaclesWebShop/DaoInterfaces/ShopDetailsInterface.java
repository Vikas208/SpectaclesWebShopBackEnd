package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.Carousel;
import com.example.SpectaclesWebShop.Bean.GlassType;
import com.example.SpectaclesWebShop.Bean.Service;
import com.example.SpectaclesWebShop.Bean.ShippingCharge;
import com.example.SpectaclesWebShop.Bean.ShopDetails;

import java.util.HashMap;
import java.util.List;

public interface ShopDetailsInterface {
    int createDatabase();

    ShopDetails getShopDetails();

    List<Carousel> getCarouselImages();

    ShippingCharge getShippingCharge();

    List<GlassType> getGlassPricing();

    int updateShopDetails(ShopDetails shopDetails);

    int updatePhonumber(ShopDetails shopDetails);

    int updateMailId(ShopDetails shopDetails);

    int addCarouselImage(List<Carousel> carousels);

    int deleteCarouselImage(long id);

    int addGlassType(GlassType glassType);

    int deleteGlassType(long id);

    int updateGlassType(GlassType glassType);

    int addCategory(HashMap<String, Object> category);

    int updateCategory(HashMap<String, Object> category);

    int deleteCategory(long id);

    int addFrameStyle(HashMap<String, Object> frameStyle);

    int updateFrameStyle(HashMap<String, Object> frameStyle);

    int deleteFrameStyle(long id);

    int addCompanyName(HashMap<String, Object> companyName);

    int updateCompanyName(HashMap<String, Object> companyName);

    int deleteCompanyName(long id);

    int addServiceDetails(Service service);

    int updateServiceDetails(Service service);

    int deleteServiceDetails(long id);

    List<Service> getServiceDetials();

}
