package br.com.i2e.magento.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import br.com.i2e.magento.adapter.listener.ResponseListener;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) 
public class Application {

	 @Autowired
	private ApplicationContext applicationContext;	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner testContext() {
		return a -> {
			
			System.out.println( ">>>>>>>>>>>: "+ applicationContext.getBean(ResponseListener.class));
		};
	}
}
