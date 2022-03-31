package com.example.SpectaclesWebShop.Controller;

import java.util.HashMap;
import java.util.List;

import com.example.SpectaclesWebShop.Bean.Carousel;
import com.example.SpectaclesWebShop.Bean.GlassType;
import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.Bean.ShippingCharge;
import com.example.SpectaclesWebShop.Bean.ShopDetails;
import com.example.SpectaclesWebShop.Dao.LoginDao;
import com.example.SpectaclesWebShop.Dao.ShopDetailsDao;
import com.example.SpectaclesWebShop.Helper.JwtUtil;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.ServerResponse.ServerResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoadDetailsController {

    @Autowired
    ShopDetailsDao shopDetailsDao;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    LoginDao loginDao;

    @GetMapping("/load/loadDetails")
    public ResponseEntity<?> LoadDetails() {
        try {
            ShopDetails shopDetails = shopDetailsDao.getShopDetails();
            return ResponseEntity.ok(shopDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Something gone Wrong", false));
    }

    @GetMapping("/isUserLogined")
    public ResponseEntity<?> isUserLogined(@CookieValue(name = "token", defaultValue = "null") String tString,
            @RequestParam(value = "admin", required = false) boolean isAdmin) {

        try {
            if (!tString.equals("null") && tString.startsWith("Bearer")) {

                String token = tString.substring(6);
                String mailId = jwtUtil.extractUsername(token);

                Login login;
                if (isAdmin) {
                    login = loginDao.findUserByIdAdmin(mailId);

                } else {
                    login = loginDao.findByMailId(mailId);
                }
                login.setPassword(null);

                HashMap<String, Object> hashMap = new HashMap<>();

                if (login == null || login.getMailId() == null) {
                    token = null;
                }
                hashMap.put("userDetails", login);
                hashMap.put("token", token);

                return new ResponseEntity<>(hashMap, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/load/carousel")
    public ResponseEntity<?> getCarousel() {
        try {
            List<Carousel> carouselImages = shopDetailsDao.getCarouselImages();

            return ResponseEntity.ok(carouselImages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/shippingCharges")
    public ResponseEntity<?> getShippingCharges() {
        try {
            ShippingCharge shippingCharge = shopDetailsDao.getShippingCharge();
            return ResponseEntity.ok(shippingCharge);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/glassCharges")
    public ResponseEntity<?> getglassCharges() {
        try {
            List<GlassType> glassCharges = shopDetailsDao.getGlassPricing();
            return ResponseEntity.ok(glassCharges);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/updateShopDetails")
    public ResponseEntity<?> updateShopDetails(@RequestBody ShopDetails shopDetails) {
        try {
            int result = shopDetailsDao.updateShopDetails(shopDetails);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PutMapping("/updateGlassDetails")
    public ResponseEntity<?> updateGlassDetails(@RequestBody GlassType glassType) {

        try {
            int result = shopDetailsDao.updateGlassType(glassType);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PutMapping("/updateData")
    public ResponseEntity<?> updateData(@RequestBody HashMap<String, Object> data,
            @RequestParam(value = "type") String type) {
        try {
            int result = 0;
            switch (type.toLowerCase()) {
                case "category":
                    result = shopDetailsDao.updateCategory(data);
                    break;
                case "framestyle":
                    result = shopDetailsDao.updateFrameStyle(data);
                    break;
                case "companyname":
                    result = shopDetailsDao.updateCompanyName(data);
                    break;

            }
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @DeleteMapping("/deleteData")
    public ResponseEntity<?> deleteData(@RequestParam("id") long id, @RequestParam(value = "type") String type) {
        try {
            int result = 0;
            switch (type.toLowerCase()) {
                case "category":
                    result = shopDetailsDao.deleteCategory(id);
                    break;
                case "framestyle":
                    result = shopDetailsDao.deleteFrameStyle(id);
                    break;
                case "companyname":
                    result = shopDetailsDao.deleteCompanyName(id);
                    break;

            }
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @DeleteMapping("/deleteGlassDetails")
    public ResponseEntity<?> deleteGlassDetails(@RequestParam("id") long id) {
        try {
            int result = shopDetailsDao.deleteGlassType(id);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @DeleteMapping("deleteCarouselImage")
    public ResponseEntity<?> deleteCarouselImage(@RequestParam("id") long id) {
        try {
            int result = shopDetailsDao.deleteCarouselImage(id);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PostMapping("/addData")
    public ResponseEntity<?> addData(@RequestBody HashMap<String, Object> data, @RequestParam("type") String type) {
        try {
            int result = 0;
            switch (type.toLowerCase()) {
                case "category":
                    result = shopDetailsDao.addCategory(data);
                    break;
                case "framestyle":
                    result = shopDetailsDao.addFrameStyle(data);
                    break;
                case "companyname":
                    result = shopDetailsDao.addCompanyName(data);
                    break;

            }
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PostMapping("/addGlassDetails")
    public ResponseEntity<?> addGlassDetails(@RequestBody GlassType glassType) {

        try {
            int result = shopDetailsDao.addGlassType(glassType);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PostMapping("/addCarouselDetails")
    public ResponseEntity<?> addCarouselDetails(@RequestBody List<Carousel> carousel) {

        try {
            int result = shopDetailsDao.addCarouselImage(carousel);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }
}
