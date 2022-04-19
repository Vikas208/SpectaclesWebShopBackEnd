package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.*;

import java.util.List;

public interface ProductsInterface {
        public void createDataBase();

        public List<Products> getAllProducts(int limit, int offset);

        public List<Products> getTrendingProducts();

        public Products getProduct(long id);

        public int countProducts();

        public List<FeedBack> getProductReviews(long p_id);

        public int deleteProductReviews(long id,String reason);

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

        public int updateProductStockAndSales(long p_id, int qty);

        public List<Products> getProductsData(int limit, int offset);

        public int saveProduct(Products product);

        public int updateProductDetails(Products products);

        public int deleteProduct(int p_id);

        public int updateProductSale(ProductSales sales);

        public int deleteProductCarouselImage(long id,String filePath);

        // admin
        public Products getEditProductDetails(long id);

        int addProductCarousel(List<ProductImage> productImages);
}
