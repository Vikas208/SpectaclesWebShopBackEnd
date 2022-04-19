package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.*;

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

    int deleteCarouselImage(long id,String path);

    int addGlassType(GlassType glassType);

    int deleteGlassType(long id);

    int updateGlassType(GlassType glassType);

    int addCategory(Data category);

    int updateCategory(Data category);

    int deleteCategory(long id);

    int addFrameStyle(Data frameStyle);

    int updateFrameStyle(Data frameStyle);

    int deleteFrameStyle(long id);

    int addCompanyName(Data companyName);

    int updateCompanyName(Data companyName);

    int deleteCompanyName(long id);

    int addServiceDetails(Service service);

    int updateServiceDetails(Service service);

    int deleteServiceDetails(long id);

    List<Service> getServiceDetails();

    List<TaxData> getTaxData();

    int updateTaxData(TaxData data);

    int deleteTaxData(long id);

    int addTaxData(TaxData data);



}
