package com.example.SpectaclesWebShop.Controller;

import javax.ws.rs.core.Request;

import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.CodeName.Code;
import com.example.SpectaclesWebShop.Dao.LoginDao;
import com.example.SpectaclesWebShop.ServerResponse.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userAccount")
public class UserController {
    @Autowired
    LoginDao loginDao;
    @Autowired
    UserAuthController userAuthController;

    @PostMapping("/validUser")
    public ResponseEntity<?> validateUser(@RequestBody Login login) {
        try {
            int res = userAuthController.authenticate(login.getMailId(), login.getPassword());

            if (res == Code.USER_NOT_EXIST) {
                return ResponseEntity.status(401).body(new ServerResponse("Bad Credentials", false));
            } else if (res == Code.SUCCESS) {
                return ResponseEntity.ok(new ServerResponse("User Is Valid", true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    @PutMapping("/changeUserName")
    public ResponseEntity<?> changeUserName(@RequestBody Login login) {
        try {

            System.out.println(login.getMailId() + " " + login.getName());
            int result = loginDao.ChangeName(login);
            if (result != Code.ERROR_CODE) {
                if (result == 0) {
                    return ResponseEntity.status(401).body(new ServerResponse("Something gone Wrong", false));
                }
                return ResponseEntity.ok(new ServerResponse("Name Updated", true));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

}
