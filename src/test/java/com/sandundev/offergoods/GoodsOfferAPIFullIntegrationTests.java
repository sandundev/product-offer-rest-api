package com.sandundev.offergoods;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.greaterThan;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandundev.offergoods.GoodsOfferRestApplication;
import com.sandundev.offergoods.model.MerchantAccount;
import com.sandundev.offergoods.model.Price;
import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.model.MerchantAccount.MerchantAccountBuilder;
import com.sandundev.offergoods.model.Product.ProductBuilder;
import com.sandundev.offergoods.model.constants.Category;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsOfferRestApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test-application.properties")
public class GoodsOfferAPIFullIntegrationTests {
	
    @Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	public void createMerchantAccount(){
		MerchantAccountBuilder accountBuilder = new MerchantAccount.MerchantAccountBuilder()
		.withName("Sandun Lewke Bandara").withAddress("10 City Road, London SW1 2SD").withPhoneNumber("02081765245");
		MerchantAccount account = accountBuilder.build();

		ResponseEntity<MerchantAccount> response = restTemplate.postForEntity("/merchants", new HttpEntity<MerchantAccount>(account), MerchantAccount.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(response.getBody().getId(), isA(Long.class));
	}

	@Test
	public void makeAnOffer(){
		ProductBuilder productBuilder = new Product.ProductBuilder().withName("Sony Smart TV")
    	.withDescription("Sony Smart TV Model - 1200").withCategory(Category.HOME_ELECTRONICS)
    	.withPrice(new Price.PriceBuilder()
    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("799.99")).build());
		
		ResponseEntity<Product> response = restTemplate.postForEntity("/merchants/{merchantId}/products/", new HttpEntity<Product>(productBuilder.build()), Product.class, 1L);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(response.getBody().getDescription(), equalTo("Sony Smart TV Model - 1200"));
	}
	
	@Test
	public void getOfferByProductId(){
	   ResponseEntity<Product> product = this.restTemplate.getForEntity("/products/{productId}", Product.class, 1);
		assertThat(product.getStatusCode(), equalTo(HttpStatus.OK));		
		assertThat(product.getBody().getId(), isA(Long.class));
	}	
	
	@Test
	public void getOffersByDescriptionLike(){
	   ResponseEntity<String> responseAsString = this.restTemplate.getForEntity("/products/find-by-description?descriptionLike={descriptionLike}", String.class, "Harry");
		assertThat(responseAsString.getStatusCode(), equalTo(HttpStatus.OK));		
		
		Collection<Product> responseProducts = new Gson().fromJson(responseAsString.getBody(), new TypeToken<Collection<Product>>() {}.getType());
		
		assertThat(responseProducts.size(), greaterThan(1));
	}
}
