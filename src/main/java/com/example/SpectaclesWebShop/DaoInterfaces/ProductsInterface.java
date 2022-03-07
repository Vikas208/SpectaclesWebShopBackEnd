package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.Data;
import com.example.SpectaclesWebShop.Bean.FeedBack;

import com.example.SpectaclesWebShop.Bean.ProductImage;
import com.example.SpectaclesWebShop.Bean.Products;

import java.util.List;

public interface ProductsInterface {
        public void createDataBase();

        public List<Products> getAllProducts(int limit, int offset);

        public List<Products> getTrendingProducts();

        public Products getProduct(long id);

        public int countProducts();

        public List<FeedBack> getProductReviews(long p_id);

        public List<ProductImage> getProductImage(long p_id);

        public int saveFeedback(FeedBack feedBack);

        public List<Products> searchProducts(String searchQuery, int offset);

        public int countSearchedProducts(String searchQuery);

        public List<Data> getAllCategories();

        public List<Data> getAllFrameStyle();

        public List<Data> getAllCompanyName();

        public List<Products> filterProducts(String name, String category, String frameStyle, String companyname,
                        String group, String framesize, double sprice, double eprice,
                        int offset);

        public int countFilterProducts(String name, String category, String frameStyle, String companyname,
                        String group,
                        String framesize, double sprice, double eprice);

        public Products getOrderedProduct(long p_id);

}
