package com.sandundev.offergoods.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sandundev.offergoods.exception.MerchantAccountNotFoundException;
import com.sandundev.offergoods.model.MerchantAccount;
import com.sandundev.offergoods.model.MerchantAccount.MerchantAccountBuilder;
import com.sandundev.offergoods.model.Price;
import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.model.Product.ProductBuilder;
import com.sandundev.offergoods.model.constants.Category;
import com.sandundev.offergoods.repository.MerchantAccountRepository;
import com.sandundev.offergoods.repository.ProductRepository;


@RunWith(MockitoJUnitRunner.class)
public class MerchantServiceTest {
    
	@Mock
    private ProductRepository productRepository;
	@Mock
    private MerchantAccountRepository merchantAccountRepository;
	
    private MerchantService merchantService;
    
    @Before
    public void setup() {
    	merchantService = new MerchantService(merchantAccountRepository, productRepository);
    }
    
	@Test
	public void createAccount_whenPassMerchantAccount_shouldReturnNewAccountWithId() {
		MerchantAccountBuilder accountBuilder = new MerchantAccount.MerchantAccountBuilder()
		.withName("Sandun Lewke Bandara").withAddress("10 City Road, London SW1 2SD")
		.withPhoneNumber("02081765245");
		MerchantAccount account = accountBuilder.build();

		when(merchantAccountRepository.save(account)).thenReturn(accountBuilder.withId(101L).build());
		
		MerchantAccount savedAccount = merchantService.createAccount(account);

		assertThat(savedAccount.getId(), equalTo(101L));
		assertThat(savedAccount.getName(), equalTo("Sandun Lewke Bandara"));
		assertThat(savedAccount.getAddress(), equalTo("10 City Road, London SW1 2SD"));
		assertThat(savedAccount.getPhoneNumber(), equalTo("02081765245"));
	}
    
	@Test
	public void offerProduct_whenPassExistingMerchantIdAndValidProduct_shouldReturnNewProductWithMerchantId() {
		MerchantAccount account = new MerchantAccount.MerchantAccountBuilder().withId(100L)
		.withName("Sandun Lewke Bandara").withAddress("10 City Road, London SW1 2SD")
		.withPhoneNumber("02081765245").build();

		ProductBuilder productBuilder = new Product.ProductBuilder().withDescription("Harry Potter Book 1")
		.withCategory(Category.BOOK).withPrice(new Price.PriceBuilder()
	    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("24.99")).build());
		
		Product product = productBuilder.build();
		
		when(merchantAccountRepository.findOne(100L)).thenReturn(account);
		when(productRepository.save(new Product(product, account)))
			.thenReturn(productBuilder.withMerchantAccount(account).withId(200L).build());

		Product savedProduct = merchantService.offerProduct(100L, product);
		
		assertThat(savedProduct.getMerchantAccount().getId(), equalTo(100L));
		assertThat(savedProduct.getMerchantAccount().getName(), equalTo("Sandun Lewke Bandara"));
	}
	
	@Test(expected = MerchantAccountNotFoundException.class)
	public void offerProduct_whenPassNonExisitsMerchantId_throwsMerchantAccountNotFoundException(){
		MerchantAccount account = new MerchantAccount.MerchantAccountBuilder().withId(100L)
		.withName("Sandun Lewke Bandara").withAddress("10 City Road, London SW1 2SD")
		.withPhoneNumber("02081765245").build();

		ProductBuilder productBuilder = new Product.ProductBuilder().withDescription("Harry Potter Book 1")
		.withCategory(Category.BOOK).withPrice(new Price.PriceBuilder()
	    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("24.99")).build());
		
		Product product = productBuilder.build();
		
		when(merchantAccountRepository.findOne(100L)).thenReturn(account);
		when(productRepository.save(new Product(product, account)))
			.thenReturn(productBuilder.withMerchantAccount(account).withId(200L).build());

		merchantService.offerProduct(404L, product);
		
	}

}
