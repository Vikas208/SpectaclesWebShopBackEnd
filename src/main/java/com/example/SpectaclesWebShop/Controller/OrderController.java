package com.example.SpectaclesWebShop.Controller;

import java.util.HashMap;
import java.util.List;

import com.example.SpectaclesWebShop.Bean.CustomersProductsDetails;
import com.example.SpectaclesWebShop.Bean.Order;
import com.example.SpectaclesWebShop.Bean.OrderAddress;
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
       public ResponseEntity<?> addOrderedProducts(@RequestBody CustomersProductsDetails customersProductsDetails) {
              try {
                     int result = orderDao.addOrderProducts(customersProductsDetails);
                     if (result != Code.ERROR_CODE) {
                            return ResponseEntity.ok(result);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }

       @PutMapping("/updateOrderedProducts")
       public ResponseEntity<?> updateOrderedProducts(@RequestBody CustomersProductsDetails customersProductsDetails) {
              try {
                     int result = orderDao.updateOrderProducts(customersProductsDetails);
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

}
