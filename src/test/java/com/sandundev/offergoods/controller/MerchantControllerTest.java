package com.sandundev.offergoods.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.sandundev.offergoods.model.MerchantAccount;
import com.sandundev.offergoods.model.MerchantAccount.MerchantAccountBuilder;
import com.sandundev.offergoods.model.Price;
import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.model.Product.ProductBuilder;
import com.sandundev.offergoods.model.constants.Category;
import com.sandundev.offergoods.service.MerchantService;

@RunWith(SpringRunner.class)
@WebMvcTest(MerchantController.class)
public class MerchantControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    MerchantService merchantService;
    
	@Test
	public void createMerchantAccount_whenPOSTMerchantAccount_willReturnMerchantAccountWithNewIdAndHttpStatus201() throws Exception {
		MerchantAccountBuilder accountBuilder = new MerchantAccount.MerchantAccountBuilder()
									.withName("Sandun Lewke Bandara").withAddress("10 City Road, London SW1 2SD").withPhoneNumber("02081765245");
		MerchantAccount account = accountBuilder.build();
		
		given(merchantService.createAccount(account)).willReturn(accountBuilder.withId(111L).build());
		
		MockHttpServletResponse response = mockMvc.perform(post("/merchants").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(account)))
				.andExpect(status().isCreated()).andReturn().getResponse();
		
		MerchantAccount responseAccount = new Gson().fromJson(response.getContentAsString(), MerchantAccount.class);
		
		assertThat(responseAccount.getName(), equalTo("Sandun Lewke Bandara"));
		assertThat(responseAccount.getAddress(), equalTo("10 City Road, London SW1 2SD"));
		assertThat(responseAccount.getPhoneNumber(), equalTo("02081765245"));
		assertThat(responseAccount.getId(), equalTo(111L));
	}

	@Test
	public void offerProduct_whenPassExistingMerchantIdAndValidProduct_shouldReturnNewProductWithHttpStatus201() throws Exception {
		MerchantAccount account = new MerchantAccount.MerchantAccountBuilder().withId(100L)
		.withName("Sandun Lewke Bandara").withAddress("10 City Road, London SW1 2SD")
		.withPhoneNumber("02081765245").build();

		ProductBuilder productBuilder = new Product.ProductBuilder().withName("Harry Potter Book Name").withDescription("Harry Potter Book Description")
		.withCategory(Category.BOOK).withPrice(new Price.PriceBuilder()
	    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("24.99")).build());
		
		Product product = productBuilder.build();
		given(merchantService.offerProduct(account.getId(), product)).willReturn(productBuilder.withId(201L).withMerchantAccount(account).build());
		
		MockHttpServletResponse response = mockMvc.perform(post("/merchants/{merchantId}/products",account.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(product)))
				.andExpect(status().isCreated()).andReturn().getResponse();
		
		Product savedProduct = new Gson().fromJson(response.getContentAsString(), Product.class);
		assertThat(savedProduct.getId(), equalTo(201L));
		assertThat(savedProduct.getCategory(), equalTo(Category.BOOK));
		assertThat(savedProduct.getPrice().getAmount(), equalTo(new BigDecimal("24.99")));
		assertThat(savedProduct.getPrice().getCurrency(), equalTo(Currency.getInstance(Locale.UK)));
	 
	}
}
