package com.example.SpectaclesWebShop.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.Helper.EmailService;
import com.example.SpectaclesWebShop.ServerResponse.OtpResponse;
import com.example.SpectaclesWebShop.ServerResponse.ServerResponse;
import com.example.SpectaclesWebShop.CodeName.Code;
import com.example.SpectaclesWebShop.Dao.LoginDao;
import com.example.SpectaclesWebShop.Helper.JwtUtil;
import com.example.SpectaclesWebShop.Service.CustomeUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserAuthController {
    @Autowired
    LoginDao loginDao;

    // authenticate user
    @Autowired
    AuthenticationManager authenticationManager;
    // load user
    @Autowired
    private CustomeUserDetailService customeUserDetailService;
    // generate token
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmailService emailService;

    // Register an User
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Login l, HttpServletResponse response) {
        try {
            int res = loginDao.SaveData(l);
            if (res == Code.DUPLICATE_KEY) {
                return ResponseEntity.ok(new ServerResponse("Account is Registered Already", false));
            } else if (res == 1) {
                UserDetails userDetails = this.customeUserDetailService.loadUserByUsername(l.getMailId());
                String token = this.jwtUtil.generateToken(userDetails);

                // set cookies
                String tString = "Bearer" + token;
                Cookie cookie = new Cookie("token", tString);
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);

                return ResponseEntity.ok()
                        .body(new ServerResponse(token, "Register Successfully", true));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Login login, HttpServletResponse response) {
        try {

            // System.out.println(login.getMailId() + " " + login.getPassword());
            int res = authenticate(login.getMailId(), login.getPassword());
            // System.out.println(res);
            if (res == Code.USER_NOT_EXIST) {
                return ResponseEntity.status(401).body(new ServerResponse("Bad Credentials", false));
            } else if (res == Code.SUCCESS) {
                String userName = loginDao.getUserName(login.getMailId());
                UserDetails userDetails = this.customeUserDetailService.loadUserByUsername(login.getMailId());
                String token = this.jwtUtil.generateToken(userDetails);
                // set Cookies
                String tString = "Bearer" + token;
                Cookie cookie = new Cookie("token", tString);
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);

                return ResponseEntity.ok()
                        .body(new ServerResponse(token, userName, "Login Successfully", true));
            }
        } catch (Exception e) {
            System.out.println("Login Controller " + e.toString());
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    // Authentication Function Check User is Valid or not
    int authenticate(String mailId, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mailId, password));
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            e.printStackTrace();
            return Code.USER_NOT_EXIST;
        } catch (Exception e) {
            e.printStackTrace();
            return Code.ERROR_CODE;
        }
        return Code.SUCCESS;
    }

    // sent otp api
    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMail(@RequestParam String mail) {
        try {
            OtpResponse otpResponse = emailService.sendMail(mail);
            if (otpResponse.isSuccess()) {
                return ResponseEntity.ok(otpResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Mail Not Sent", false));
    }

    // forgot password opt api
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam String mail) {

        try {
            Login login = loginDao.findByMailId(mail);
            if (login == null || login.getMailId() == null) {
                return ResponseEntity.status(401).body(new ServerResponse("Bad Credentials", false));
            }
            return sendMail(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Mail Not Sent", false));
    }

    // reset password api
    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody Login login) {
        try {
            Login l = loginDao.findByMailId(login.getMailId());
            if (l == null || l.getMailId() == null) {
                return ResponseEntity.status(401).body(new ServerResponse("Bad Credentials", false));
            }
            int result = loginDao.UpdatePassword(login);
            System.out.println(result);
            if (result == 1)
                return ResponseEntity.ok(new ServerResponse("Password Update", true));
            else
                return ResponseEntity.status(403)
                        .body(new ServerResponse("Something is wrong", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("token", "");
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}