package com.example.SpectaclesWebShop;

import com.example.SpectaclesWebShop.Dao.CustomerProductsDao;
import com.example.SpectaclesWebShop.Dao.LoginDao;
import com.example.SpectaclesWebShop.Dao.OrderDao;
import com.example.SpectaclesWebShop.Dao.ProductsDao;
import com.example.SpectaclesWebShop.Dao.ShopDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SpectaclesWebShopApplication implements CommandLineRunner {
	@Autowired
	LoginDao loginDao;

	@Autowired
	ShopDetailsDao shopDetailsDao;

	@Autowired
	ProductsDao productsDao;

	@Autowired
	CustomerProductsDao customerProductsDao;

	@Autowired
	OrderDao orderDao;

	public static void main(String[] args) {
		SpringApplication.run(SpectaclesWebShopApplication.class, args);
	}


	@Override
	public void run(String... args) {
		loginDao.createDataBase();
		shopDetailsDao.createDatabase();
		productsDao.createDataBase();
		customerProductsDao.CreateDataBase();
		orderDao.CreateDataBases();
		System.out.println("App Started");
	}

}
