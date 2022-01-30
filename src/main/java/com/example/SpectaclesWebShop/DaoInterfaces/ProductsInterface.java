package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.Products;

import java.util.List;

public interface ProductsInterface {
    public void createDataBase();

    public List<Products> getAllProducts(int limit, int offset);

    public List<Products> getTrendingProducts();

    public Products getProduct();
}
