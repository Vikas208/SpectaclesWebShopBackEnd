package com.example.SpectaclesWebShop;

import com.example.SpectaclesWebShop.Dao.LoginDao;
import com.example.SpectaclesWebShop.Dao.ProductsDao;
import com.example.SpectaclesWebShop.Dao.ShopDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpectaclesWebShopApplication implements CommandLineRunner {
	@Autowired
	LoginDao loginDao;

	@Autowired
	ShopDetailsDao shopDetailsDao;

	@Autowired
	ProductsDao productsDao;
	public static void main(String[] args) {
		SpringApplication.run(SpectaclesWebShopApplication.class, args);
	}

	@Override
	public void run(String... args) {
		loginDao.createDataBase();
		shopDetailsDao.createDatabase();
		productsDao.createDataBase();
		System.out.println("App Started");
	}
}
