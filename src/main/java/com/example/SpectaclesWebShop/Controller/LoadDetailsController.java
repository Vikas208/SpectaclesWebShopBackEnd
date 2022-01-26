package com.example.SpectaclesWebShop.Controller;

import java.util.HashMap;

import com.example.SpectaclesWebShop.Bean.ShopDetails;
import com.example.SpectaclesWebShop.Dao.LoginDao;
import com.example.SpectaclesWebShop.Dao.ShopDetailsDao;
import com.example.SpectaclesWebShop.Helper.JwtUtil;
import com.example.SpectaclesWebShop.ServerResponse.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoadDetailsController {

    @Autowired
    ShopDetailsDao shopDetailsDao;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    LoginDao loginDao;

    @GetMapping("/loadDetails")
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
    public ResponseEntity<?> isUserLogined(@CookieValue(name = "token", defaultValue = "null") String tString) {

        try {
            if (!tString.equals("null") && tString.startsWith("Bearer")) {

                String token = tString.substring(6);
                String mailId = jwtUtil.extractUsername(token);
                String userName = loginDao.getUserName(mailId);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("mailId", mailId);
                hashMap.put("userName", userName);
                hashMap.put("token", token);

                return new ResponseEntity<>(hashMap, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
