package com.example.SpectaclesWebShop.DaoInterfaces;

import java.util.HashMap;
import java.util.List;

import com.example.SpectaclesWebShop.Bean.CustomersProductsDetails;

public interface CustomerProductsInterface {
       public void CreateDataBase();

       public int SaveProductToCart(CustomersProductsDetails customersProductsDetails);

       public int SaveProductToWishList(CustomersProductsDetails customersProductsDetails);

       public List<CustomersProductsDetails> getCustomerCart(long c_id, int offset);

       public List<CustomersProductsDetails> getCustomerWishList(long c_id, int offset);

       public int UpdateCartDetails(CustomersProductsDetails customersProductsDetails);

       public int DeleteCartProduct(long cc_id);

       public int DeletewWishListProduct(long cw_id);

       public int countTotalProductsInCart(long c_id);

       public int countTotalProductsInWishList(long c_id);

       public List<HashMap<String, Object>> getBillingInformation(long c_id);

       public int CheckAllProductData(long c_id);
}
// c_id glassType onlyFrame p_id p_price saleDetails
