package com.example.SpectaclesWebShop.Controller;

import java.util.HashMap;
import java.util.List;

import com.example.SpectaclesWebShop.Bean.*;
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

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUser() {
        try {
            List<Login> logins = loginDao.getAllUser();
            return ResponseEntity.ok(logins);
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

    @PutMapping("/updateShopContactDetails")
    public ResponseEntity<?> updateShopContactDetails(@RequestParam("contact") String contact,
            @RequestBody ShopDetails shopDetails) {
        try {
            int result = Code.ERROR_CODE;
            if (contact.equalsIgnoreCase("mail")) {
                result = shopDetailsDao.updateMailId(shopDetails);
            } else if (contact.equalsIgnoreCase("phone")) {
                result = shopDetailsDao.updatePhonumber(shopDetails);
            }
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
    public ResponseEntity<?> updateData(@RequestBody Data data,
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
            if (result == Code.DATAINTEGRATION) {
                return ResponseEntity.status(403)
                        .body(new ServerResponse("Some Product Are Use This Infomation So You Can't Modify It", false));
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
            if (result == Code.DATAINTEGRATION) {
                return ResponseEntity.status(403)
                        .body(new ServerResponse("Some Product Are Use This Infomation So You Can't Modify It", false));
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
    public ResponseEntity<?> deleteCarouselImage(@RequestParam("id") long id,@RequestParam("filePath") String filePath) {
        try {
            int result = shopDetailsDao.deleteCarouselImage(id,filePath);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PostMapping("/addData")
    public ResponseEntity<?> addData(@RequestBody Data data, @RequestParam("type") String type) {
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
            if (result == Code.DUPLICATE_KEY) {
                return ResponseEntity.status(403).body(new ServerResponse("Glass Type is Exists", false));
            }
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

    @PostMapping("/addServiceDetails")
    public ResponseEntity<?> addServiceDetails(@RequestBody Service service) {
        try {
            int result = shopDetailsDao.addServiceDetails(service);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PutMapping("/updateServiceDetails")
    public ResponseEntity<?> updateServiceDetails(@RequestBody Service service) {
        try {
            int result = shopDetailsDao.updateServiceDetails(service);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @DeleteMapping("/deleteServiceDetails")
    public ResponseEntity<?> deleteServiceDetails(@RequestParam("id") long id) {
        try {
            int result = shopDetailsDao.deleteServiceDetails(id);
            if (result != Code.ERROR_CODE) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @GetMapping("/getServiceDetails")
    public ResponseEntity<?> getServiceDetails() {
        try {
            List<Service> services = shopDetailsDao.getServiceDetails();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @GetMapping("/getTaxData")
    public ResponseEntity<?> getTaxData() {
        try {
            List<TaxData> taxData = shopDetailsDao.getTaxData();
            return ResponseEntity.ok(taxData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PutMapping("/updateTaxData")
    public ResponseEntity<?> updateTaxData(@RequestBody TaxData taxData) {
        try {
            int result = shopDetailsDao.updateTaxData(taxData);
            if (result != Code.ERROR_CODE)
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @DeleteMapping("/deleteTaxData")
    public ResponseEntity<?> deleteTaxData(@RequestParam("id") long id) {
        try {
            int result = shopDetailsDao.deleteTaxData(id);
            if (result != Code.ERROR_CODE)
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @PostMapping("/addTaxData")
    public ResponseEntity<?> addTaxData(@RequestBody TaxData data) {
        try {
            int result = shopDetailsDao.addTaxData(data);
            if (result != Code.ERROR_CODE)
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }
}
