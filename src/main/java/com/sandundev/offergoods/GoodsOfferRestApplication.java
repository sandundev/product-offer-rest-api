package com.sandundev.offergoods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
/**
 * 
 * 
 * @author Sandun Lewke Bandara
 */

@SpringBootApplication
@EnableAutoConfiguration
//@ComponentScan("com.sandundev.offergoods")
//@EntityScan("com.sandundev.offergoods.model")
//@EnableJpaRepositories("com.sandundev.offergoods.repository")
@PropertySource("classpath:application.properties")
public class GoodsOfferRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodsOfferRestApplication.class, args);
	}
}
