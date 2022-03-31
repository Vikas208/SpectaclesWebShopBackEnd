package com.example.SpectaclesWebShop.Controller;

import java.util.HashMap;

import com.example.SpectaclesWebShop.Bean.Order;
import com.example.SpectaclesWebShop.Dao.DashBoardDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashBoard {
       @Autowired
       DashBoardDao dashBoardDao;

       @GetMapping("/getDashBoardDetails")
       public ResponseEntity<?> getDashBoardDetails() {
              try {
                     HashMap<String, Object> basicData = dashBoardDao.DashBoardData();
                     HashMap<String, Object> chartData = dashBoardDao.DashBoradChartsData();

                     HashMap<String, Object> data = new HashMap<String, Object>();
                     data.put("basicData", basicData);
                     data.put("chartData", chartData);

                     return ResponseEntity.ok(data);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return (ResponseEntity<?>) ResponseEntity.internalServerError();
       }
}
