package com.example.SpectaclesWebShop.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.Service.EmailService;
import com.example.SpectaclesWebShop.ServerResponse.ServerResponse;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.Dao.LoginDao;
import com.example.SpectaclesWebShop.Helper.JwtUtil;
import com.example.SpectaclesWebShop.Service.CustomeUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    EmailService emailservice;

    @Autowired
    com.example.SpectaclesWebShop.Service.otpService otpService;

    private Cookie createCookie(String val) {
        String CookieString = "Bearer" + val;
        Cookie cookie = new Cookie("token", CookieString);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        return cookie;
    }

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

                Login login = loginDao.findByMailId(l.getMailId());
                login.setPassword(null);
                // set Cookie
                response.addCookie(createCookie(token));

                return ResponseEntity.ok()
                        .body(new ServerResponse(token, login, "Register Successfully", true));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Login login,
            @RequestParam(value = "admin", required = false) boolean isAdmin, HttpServletResponse response) {
        try {

            int res = authenticate(login.getMailId(), login.getPassword());
            if (res == Code.USER_NOT_EXIST) {
                return ResponseEntity.status(401).body(new ServerResponse("Bad Credentials", false));
            } else if (res == Code.SUCCESS) {
                Login login1;
                if (isAdmin) {
                    login1 = loginDao.findUserByIdAdmin(login.getMailId());

                } else {
                    login1 = loginDao.findByMailId(login.getMailId());
                }
                login1.setPassword(null);
                UserDetails userDetails = this.customeUserDetailService.loadUserByUsername(login.getMailId());
                String token = this.jwtUtil.generateToken(userDetails);
                // set Cookie
                response.addCookie(createCookie(token));

                return ResponseEntity.ok()
                        .body(new ServerResponse(token, login1, "Login Successfully", true));
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
    public ResponseEntity<?> sendMail(@RequestParam("mail") String mail) {
        try {
            // generate opt
            int otp = otpService.GenerateOtp(mail);

            if (emailservice.sendMail(mail, otp)) {
                return ResponseEntity.ok(new ServerResponse("OTP Sent to your email id", true));
            }
            return ResponseEntity.ok(new ServerResponse("Something is wrong", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Mail Not Sent", false));
    }

    // Validate opt api
    @GetMapping("/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestParam("mail") String mail, @RequestParam("otp") String opt) {
        try {
            int serverOpt = otpService.getOtp(mail);
            if (serverOpt == Integer.parseInt(opt)) {
                otpService.clearOTP(mail);
                return ResponseEntity.ok(true);
            }
            return ResponseEntity.ok(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new ServerResponse("Internal Server Error", false));
    }

    // forgot password opt api
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("mail") String mail) {

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
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                String key = authentication.getName();
                otpService.clearOTP(key);
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }
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