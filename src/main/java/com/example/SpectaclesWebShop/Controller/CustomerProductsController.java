package com.example.SpectaclesWebShop.Controller;

import java.util.HashMap;
import java.util.List;

import com.example.SpectaclesWebShop.Bean.CustomersProductsDetails;
import com.example.SpectaclesWebShop.Dao.CustomerProductsDao;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.ServerResponse.ServerResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Customer/")
public class CustomerProductsController {

       @Autowired
       CustomerProductsDao customerProductsDao;

       @PostMapping("/saveToCart")
       public ResponseEntity<?> saveToCart(@RequestBody CustomersProductsDetails customersProductsDetails) {
              try {
                     int result = customerProductsDao.SaveProductToCart(customersProductsDetails);
                     if (result != Code.ERROR_CODE) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("message", "Done");
                            return ResponseEntity.ok(map);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error",
                            false));
       }

       @PostMapping("/saveToWishList")
       public ResponseEntity<?> saveToWishList(@RequestBody CustomersProductsDetails customersProductsDetails) {
              try {
                     int result = customerProductsDao.SaveProductToWishList(customersProductsDetails);
                     HashMap<String, String> map = new HashMap<String, String>();
                     if (result != Code.ERROR_CODE) {
                            map.put("message", "Done");
                            return ResponseEntity.ok(map);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @PutMapping("/updateCartProduct")
       public ResponseEntity<?> updateCartProduct(@RequestBody CustomersProductsDetails customersProductsDetails) {
              try {
                     int result = customerProductsDao.UpdateCartDetails(customersProductsDetails);
                     HashMap<String, Object> map = new HashMap<String, Object>();
                     if (result == Code.INVALIDDATA) {
                            map.put("message", "Available Qty Not Available");
                            map.put("success", false);
                            return ResponseEntity.ok(map);
                     }
                     if (result != Code.ERROR_CODE) {
                            map.put("message", "Done");
                            map.put("success", true);
                            return ResponseEntity.ok(map);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @DeleteMapping("/deleteCartProduct")
       public ResponseEntity<?> deleteCartProduct(@RequestParam("cartProduct") long cc_id) {
              try {
                     int result = customerProductsDao.DeleteCartProduct(cc_id);
                     if (result != Code.ERROR_CODE) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("message", "Done");
                            return ResponseEntity.ok(map);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @DeleteMapping("/deleteWishProduct")
       public ResponseEntity<?> deleteWishListProduct(@RequestParam("wishListProduct") long cw_id) {
              try {
                     int result = customerProductsDao.DeletewWishListProduct(cw_id);
                     if (result != Code.ERROR_CODE) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("message", "Done");
                            return ResponseEntity.ok(map);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @GetMapping("/getCustomerCart")
       public ResponseEntity<?> GetCustomerCart(@RequestParam("userId") long c_id,

                     @RequestParam("offset") int offset) {
              try {
                     List<CustomersProductsDetails> products = customerProductsDao.getCustomerCart(c_id, offset);
                     return ResponseEntity.ok(products);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @GetMapping("/getCustomerWishList")
       public ResponseEntity<?> GetCustomerWishList(@RequestParam("userId") long c_id,
                     @RequestParam("offset") int offset) {
              try {
                     List<CustomersProductsDetails> products = customerProductsDao.getCustomerWishList(c_id, offset);
                     return ResponseEntity.ok(products);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @GetMapping("/countTotalProduct")
       public ResponseEntity<?> CountTotalProducts(@RequestParam("userId") long userId) {
              try {
                     int legnth = customerProductsDao.countTotalProductsInCart(userId);
                     if (legnth != Code.ERROR_CODE) {
                            return ResponseEntity.ok(legnth);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @GetMapping("/countTotalWishListProduct")
       public ResponseEntity<?> CountTotalWishListProduct(@RequestParam("userId") long userId) {
              try {
                     int legnth = customerProductsDao.countTotalProductsInCart(userId);
                     if (legnth != Code.ERROR_CODE) {
                            return ResponseEntity.ok(legnth);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @GetMapping("/getBillingPricing")
       public ResponseEntity<?> getBillingPricing(@RequestParam("userId") long userId) {
              try {
                     List<HashMap<String, Object>> pricing = customerProductsDao.getBillingInformation(userId);
                     // HashMap<String, Object> map = new HashMap<String, Object>();
                     // map.put("Pricing", pricing);
                     return ResponseEntity.ok(pricing);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
       }

       @GetMapping("/validateProductData")
       public ResponseEntity<?> validateProductData(@RequestParam("userId") long userId) {
              try {
                     int isValid = customerProductsDao.CheckAllProductData(userId);
                     HashMap<String, Object> map = new HashMap<String, Object>();

                     if (isValid == Code.INVALIDDATA) {
                            map.put("message",
                                          "Incomplete Information Provided Or May Qty Of Some Product exclude Stock Please Check All Product Information");
                            map.put("success", false);
                            return ResponseEntity.ok(map);
                     } else if (isValid != Code.ERROR_CODE) {
                            map.put("success", true);
                            return ResponseEntity.ok(map);
                     }
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));

       }

}
