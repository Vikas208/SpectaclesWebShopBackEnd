package com.example.SpectaclesWebShop.Controller;

import com.example.SpectaclesWebShop.Bean.Products;
import com.example.SpectaclesWebShop.Dao.ProductsDao;
import com.example.SpectaclesWebShop.ServerResponse.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    ProductsDao productsDao;

    @GetMapping("/fetchProducts")
    public ResponseEntity<?> fetchProducts(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        try {
            List<Products> products = productsDao.getAllProducts(limit, offset);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/TredingProducts")
    public ResponseEntity<?> TredingProducts() {
        try {
            List<Products> products = productsDao.getTrendingProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

}
