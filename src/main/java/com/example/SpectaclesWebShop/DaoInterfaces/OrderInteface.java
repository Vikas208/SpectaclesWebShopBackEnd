package com.example.SpectaclesWebShop.DaoInterfaces;

import com.example.SpectaclesWebShop.Bean.Order;
import com.example.SpectaclesWebShop.Bean.OrderAddress;
import com.example.SpectaclesWebShop.Bean.OrderPayment;
import com.example.SpectaclesWebShop.Bean.OrderedProducts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface OrderInteface {

       public void CreateDataBases();

       public int createNewOrder(Order order);

       public int updateOrderDetails(Order order);

       public int updateOrderService(Order order);

       public int addOrderAddress(OrderAddress orderAddress);

       public int updateOrderAddress(OrderAddress orderAddress);

       public int addOrderProducts(ArrayList<OrderedProducts> orderedProducts);

       public int updateOrderProducts(OrderedProducts orderedProducts);

       public int deleteOrderedProducts(long product_id);

       public int addOrderPaymentDetails(OrderPayment orderPayment);

       public int updatePaymentStatus(long payment_id, String status);

       public int CancelOrder(long order_id);

       public List<Order> getCustomerOrders(long c_id);

       public List<Order> getCustomerCanceledOrders(long c_id);

       public OrderAddress getOldAddress(long c_id);

       public List<HashMap<String, Object>> getOrderedDetails(long c_id);

       public HashMap<String, Object> getOrderedProduct(long p_id, long qty, String glassType);

       public boolean sendInvoice(long order_id);

       public boolean sendCancelOrder(long order_id, String cancellationReason);

       public boolean sendDeliveredOrder(long order_id);

       // Check Shop Now Product
       public int CheckOrderedProductData(long p_id, int qty, String glassType, boolean onlyframe);

       // Placed Orders
       public List<Order> getAllPlacedOrders();

       // Shipped Orders
       public List<Order> getAllShippedOrders();

       // Canceled Orders
       public List<Order> getAllCanceledOrders();

       // Delivered Orders
       public List<Order> getAllDeliveredOrders();

       public int deleteOrder(long id);

}
