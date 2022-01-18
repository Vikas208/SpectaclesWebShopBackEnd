package com.example.SpectaclesWebShop;

import com.example.SpectaclesWebShop.Dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpectaclesWebShopApplication implements CommandLineRunner {
	@Autowired
	LoginDao loginDao;

	public static void main(String[] args) {
		SpringApplication.run(SpectaclesWebShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(loginDao.createDataBase());
	}
}
