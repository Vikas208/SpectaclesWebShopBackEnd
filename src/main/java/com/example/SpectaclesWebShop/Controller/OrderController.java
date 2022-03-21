package com.example.SpectaclesWebShop.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.SpectaclesWebShop.Bean.Order;
import com.example.SpectaclesWebShop.Bean.OrderAddress;
import com.example.SpectaclesWebShop.Bean.OrderPayment;
import com.example.SpectaclesWebShop.Bean.OrderedProducts;
import com.example.SpectaclesWebShop.Dao.OrderDao;
import com.example.SpectaclesWebShop.Info.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Order/")
public class OrderController {

       @Autowired
       OrderDao orderDao;

       @PostMapping("/createNewOrder")
       public ResponseEntity<?> CreateOrder(@RequestBody Order order) {
              try {
                     int result = orderDao.createNewOrder(order);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @PutMapping("/updateOrderDetails")
       public ResponseEntity<?> updateOrderDetails(@RequestBody Order order) {
              try {
                     int result = orderDao.updateOrderDetails(order);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @DeleteMapping("/cancelOrder")
       public ResponseEntity<?> cancelOrder(@RequestParam("order_id") int order_id) {
              try {
                     int result = orderDao.CancelOrder(order_id);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @PostMapping("/addOrderAddress")
       public ResponseEntity<?> addOrderAddress(@RequestBody OrderAddress orderAddress) {
              try {
                     int result = orderDao.addOrderAddress(orderAddress);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @PutMapping("/updateOrderAddress")
       public ResponseEntity<?> updateOrderAddress(@RequestBody OrderAddress orderAddress) {
              try {
                     int result = orderDao.updateOrderAddress(orderAddress);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @PostMapping("/addOrderedProducts")
       public ResponseEntity<?> addOrderedProducts(
                     @RequestBody ArrayList<OrderedProducts> orderedProducts) {
              try {
                     // System.out.println(orderedProducts.toString());
                     int result = orderDao.addOrderProducts(orderedProducts);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @PutMapping("/updateOrderedProducts")
       public ResponseEntity<?> updateOrderedProducts(@RequestBody OrderedProducts orderedProducts) {
              try {
                     int result = orderDao.updateOrderProducts(orderedProducts);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @DeleteMapping("/deleteOrderedProduct")
       public ResponseEntity<?> deleteOrderedProduct(@RequestParam("productId") int product_id) {
              try {
                     int result = orderDao.deleteOrderedProducts(product_id);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

       }

       @GetMapping("/getOldAddress")
       public ResponseEntity<?> getOldAddress(@RequestParam("userId") long c_id) {
              try {
                     OrderAddress orderAddress = orderDao.getOldAddress(c_id);
                     return ResponseEntity.ok(orderAddress);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

       }

       @GetMapping("/getCustomerCartOrderedProduct")
       public ResponseEntity<?> getCustomerCartOrderedProduct(@RequestParam("userId") long c_id) {
              try {
                     List<HashMap<String, Object>> producList = orderDao.getOrderedDetails(c_id);
                     return ResponseEntity.ok(producList);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

       }

       @GetMapping("/getShopNowProduct")
       public ResponseEntity<?> getShopNowProduct(@RequestParam("p_id") long p_id, @RequestParam("qty") long qty,
                     @RequestParam("glassType") String glassType) {
              try {
                     HashMap<String, Object> product = orderDao.getOrderedProduct(p_id, qty, glassType);
                     return ResponseEntity.ok(product);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @PostMapping("/addProductPayment")
       public ResponseEntity<?> addProductPayment(@RequestBody OrderPayment orderPayment) {
              try {
                     int result = orderDao.addOrderPaymentDetails(orderPayment);
                     return ResponseEntity.ok(result);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @PostMapping("/sendInvoice")
       public ResponseEntity<?> sendInvoice(@RequestParam("order") long order_id) {
              try {
                     boolean result = orderDao.sendInvoice(order_id);
                     return ResponseEntity.ok(result);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @GetMapping("/getCustomerOrders")
       public ResponseEntity<?> getCustomerOrders(@RequestParam("userId") long user_id) {
              try {
                     List<Order> orders = orderDao.getCustomerOrders(user_id);
                     return ResponseEntity.ok(orders);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

}
