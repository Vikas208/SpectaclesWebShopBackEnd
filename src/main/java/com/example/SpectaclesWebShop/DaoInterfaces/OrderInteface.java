package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.CustomersProductsDetails;
import com.example.SpectaclesWebShop.Bean.Order;
import com.example.SpectaclesWebShop.Bean.OrderAddress;
import com.example.SpectaclesWebShop.Bean.OrderPayment;

import java.util.HashMap;
import java.util.List;

public interface OrderInteface {
       public void CreateDataBases();

       public int createNewOrder(Order order);

       public int updateOrderDetails(Order order);

       public int addOrderAddress(OrderAddress orderAddress);

       public int updateOrderAddress(OrderAddress orderAddress);

       public int addOrderProducts(CustomersProductsDetails customersProductsDetails);

       public int updateOrderProducts(CustomersProductsDetails customersProductsDetails);

       public int deleteOrderedProducts(long product_id);

       public int addOrderPaymentDetails(OrderPayment orderPayment);

       public int updatePaymentStatus(long payment_id, String status);

       public int CancelOrder(long order_id);

       public List<Order> getCustomerOrders(long c_id);

       public OrderAddress getOldAddress(long c_id);

       public List<HashMap<String, Object>> getOrderedDetails(long c_id);

       public HashMap<String, Object> getOrderedProduct(long p_id, long qty, String glassType);
}
