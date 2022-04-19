package com.example.SpectaclesWebShop.Controller;

import com.example.SpectaclesWebShop.Bean.Data;
import com.example.SpectaclesWebShop.Bean.FeedBack;
import com.example.SpectaclesWebShop.Bean.ProductImage;
import com.example.SpectaclesWebShop.Bean.ProductSales;
import com.example.SpectaclesWebShop.Bean.Products;
import com.example.SpectaclesWebShop.Dao.ProductsDao;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.ServerResponse.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    ProductsDao productsDao;

    @GetMapping("/fetch/getData")
    public ResponseEntity<?> fetchCategories() {
        try {
            List<Data> categories = productsDao.getAllCategories();
            List<Data> frameStyles = productsDao.getAllFrameStyle();
            List<Data> companyNames = productsDao.getAllCompanyName();

            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("categories", categories);
            hashMap.put("frameStyles", frameStyles);
            hashMap.put("companyNames", companyNames);

            if (hashMap != null) {
                return ResponseEntity.ok(hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/fetchProducts")
    public ResponseEntity<?> fetchProducts(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        try {
            List<Products> products = productsDao.getAllProducts(limit, offset);
            if (products != null) {
                return ResponseEntity.ok(products);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/TredingProducts")
    public ResponseEntity<?> TredingProducts() {
        try {
            List<Products> products = productsDao.getTrendingProducts();
            if (products != null) {
                return ResponseEntity.ok(products);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/countProducts")
    public ResponseEntity<?> CountAllProducts() {
        try {

            int length = productsDao.countProducts();
            return ResponseEntity.ok(length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/getProductReviews")
    public ResponseEntity<?> GetProductReviews(@RequestParam("productId") long p_id) {
        try {

            List<FeedBack> feedBacks = productsDao.getProductReviews(p_id);
            if (feedBacks != null) {
                return ResponseEntity.ok(feedBacks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @DeleteMapping("/fetch/DeleteProductReviews")
    public ResponseEntity<?> DeleteProductReviews(@RequestParam("id") long id,@RequestParam("reason") String reason) {
        try {

            int res = productsDao.deleteProductReviews(id,reason);
            if (res != Code.ERROR_CODE) {
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/getProductImages")
    public ResponseEntity<?> GetProductImages(@RequestParam("productId") long p_id) {
        try {
            List<ProductImage> productImages = productsDao.getProductImage(p_id);
            if (productImages != null) {
                return ResponseEntity.ok(productImages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/getProduct")
    public ResponseEntity<?> GetProduct(@RequestParam("productId") long p_id,
            @RequestParam(value = "admin", required = false) boolean isAdmin) {
        try {
            Products product;
            if (isAdmin) {
                product = productsDao.getEditProductDetails(p_id);
            } else {
                product = productsDao.getProduct(p_id);
            }
            if (product != null) {
                return ResponseEntity.ok(product);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/searchProduct")
    public ResponseEntity<?> SearchProduct(@RequestParam("product") String searchQuery,
            @RequestParam("offset") int offset) {
        try {
            List<Products> productsList = productsDao.searchProducts(searchQuery, offset);
            if (productsList != null) {
                return ResponseEntity.ok(productsList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/countTotalSearchProduct")
    public ResponseEntity<?> CountTotalSearchProduct(@RequestParam("product") String searchQuery) {
        try {
            int length = productsDao.countSearchedProducts(searchQuery);
            return ResponseEntity.ok(length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/filterproducts")
    public ResponseEntity<?> filterProducts(@RequestParam("product") String name,
            @RequestParam(value = "category", defaultValue = "^") String category,
            @RequestParam(value = "frameStyle", defaultValue = "^") String frameStyle,
            @RequestParam(value = "companyName", defaultValue = "^") String companyname,
            @RequestParam(value = "group", defaultValue = "^") String group,
            @RequestParam(value = "framesize", defaultValue = "^") String framesize,
            @RequestParam(value = "startingprice") double startingprice,
            @RequestParam(value = "endingprice") double endingprice,
            @RequestParam("offset") int offset) {
        try {
            // System.out.println(category + " " + frameStyle + " " + companyname + " " +
            // group);
            // System.out.println("filter" + category);
            List<Products> products = productsDao.filterProducts(name, category, frameStyle, companyname, group,
                    framesize, startingprice, endingprice,
                    offset);
            if (products != null) {
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/fetch/countFilterProducts")
    public ResponseEntity<?> countFilterProducts(@RequestParam("product") String name,
            @RequestParam(value = "category") String category,
            @RequestParam(value = "frameStyle") String frameStyle,
            @RequestParam(value = "companyName") String companyname,
            @RequestParam(value = "group") String group,
            @RequestParam(value = "framesize") String framesize,
            @RequestParam(value = "startingprice") double startingprice,
            @RequestParam(value = "endingprice") double endingprice) {
        try {
            // System.out.println("count" + category);
            int length = productsDao.countFilterProducts(name, category, frameStyle, companyname, group, framesize,
                    startingprice, endingprice);
            return ResponseEntity.ok(length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @PostMapping("/saveFeedback")
    public ResponseEntity<?> saveFeedback(@RequestBody FeedBack feedBack) {
        try {
            if (productsDao.saveFeedback(feedBack) == 1) {
                return ResponseEntity.ok(new ServerResponse("Saved", true));
            }
            return ResponseEntity.ok(new ServerResponse("Something is Wrong", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @GetMapping("/getOrderedProductDetails")
    public ResponseEntity<?> getOrderedProductDetails(@RequestParam("p_id") long p_id) {
        try {
            Products products = productsDao.getOrderedProduct(p_id);
            if (products != null) {

                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));

    }

    @GetMapping("/getProducts")
    public ResponseEntity<?> getProducts(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        try {
            List<Products> productsList = productsDao.getProductsData(limit, offset);
            return ResponseEntity.ok(productsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam("p_id") int p_id) {
        try {
            int row = productsDao.deleteProduct(p_id);
            if (row != Code.ERROR_CODE)
                return ResponseEntity.ok(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<?> getProducts(@RequestBody Products products) {
        try {

            int row = productsDao.updateProductDetails(products);
            if (row != Code.ERROR_CODE)
                return ResponseEntity.ok(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PutMapping("/updateProductSale")
    public ResponseEntity<?> updateProductSale(@RequestBody ProductSales sales) {
        try {
            int row = productsDao.updateProductSale(sales);
            if (row != Code.ERROR_CODE)
                return ResponseEntity.ok(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(@RequestBody Products products) {
        try {
            int row = productsDao.saveProduct(products);
            if (row != Code.ERROR_CODE)
                return ResponseEntity.ok(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @DeleteMapping("/deleteProductImage")
    public ResponseEntity<?> deleteProductImage(@RequestParam("id") long id,@RequestParam("filePath") String filePath) {
        try {
            int row = productsDao.deleteProductCarouselImage(id,filePath);
            if (row != Code.ERROR_CODE)
                return ResponseEntity.ok(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }


    @PostMapping("/addProductCarousel")
    public ResponseEntity<?> addProductCarousel(@RequestBody List<ProductImage> productImage) {
        try {
            int row = productsDao.addProductCarousel(productImage);
            if (row != Code.ERROR_CODE)
                return ResponseEntity.ok(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }
}

