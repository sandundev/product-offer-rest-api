package com.sandundev.offergoods.controller;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;
import java.util.stream.Collectors;

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
import com.google.gson.reflect.TypeToken;
import com.sandundev.offergoods.exception.ApiError;
import com.sandundev.offergoods.model.Price;
import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.model.constants.Category;
import com.sandundev.offergoods.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void getOffers_when3ProductsAvailable_returnShouldContainAvailableProducts() throws Exception{
 
	Collection<Product> products = Arrays.asList(
		new Product.ProductBuilder().withId(1L).withDescription("Harry Potter Book 1")
			.withCategory(Category.BOOK).withPrice(new Price.PriceBuilder()
		    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("24.99")).build()).build(),

		new Product.ProductBuilder().withId(2L).withDescription("PS 4")
			.withCategory(Category.GAME_CONSOLE).withPrice(new Price.PriceBuilder()
		    		.withCurrency(Currency.getInstance(Locale.US)).withAmount(new BigDecimal("199")).build()).build(),

		new Product.ProductBuilder().withId(3L).withDescription("Dyson DC40 3")
			.withCategory(Category.HOME_ELECTRONICS).withPrice(new Price.PriceBuilder()
		    		.withCurrency(Currency.getInstance(Locale.FRANCE)).withAmount(new BigDecimal("300")).build()).build());

	given(productService.findAllProducts()).willReturn(products);

	MockHttpServletResponse response = mockMvc.perform(get("/products").contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn().getResponse();
	Collection<Product> responseProducts = new Gson().fromJson(response.getContentAsString(), new TypeToken<Collection<Product>>() {}.getType());

	assertThat(responseProducts.stream().map(Product::getDescription).collect(Collectors.toSet()), hasItems("Dyson DC40 3","Harry Potter Book 1","PS 4"));
	assertThat(responseProducts.stream().map(Product::getId).collect(Collectors.toSet()), hasItems(1L,	2L,	3L));
	    
    }

    @Test
    public void getOffers_when3ProductsAvailable_returns3OfferedProducts() throws Exception{
 
	Collection<Product> products = Arrays.asList(
		new Product.ProductBuilder().withId(1L).withDescription("Harry Potter Book 1")
			.withCategory(Category.BOOK).withPrice(new Price.PriceBuilder()
		    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("24.99")).build()).build(),

		new Product.ProductBuilder().withId(2L).withDescription("PS 4")
			.withCategory(Category.GAME_CONSOLE).withPrice(new Price.PriceBuilder()
		    		.withCurrency(Currency.getInstance(Locale.US)).withAmount(new BigDecimal("199")).build()).build(),

		new Product.ProductBuilder().withId(3L).withDescription("Dyson DC40 3")
			.withCategory(Category.HOME_ELECTRONICS).withPrice(new Price.PriceBuilder()
		    		.withCurrency(Currency.getInstance(Locale.FRANCE)).withAmount(new BigDecimal("300")).build()).build());

	given(productService.findAllProducts()).willReturn(products);

	MockHttpServletResponse response = mockMvc.perform(get("/products").contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn().getResponse();
	Collection<Product> responseProducts = new Gson().fromJson(response.getContentAsString(), new TypeToken<Collection<Product>>() {}.getType());
	
	assertThat(responseProducts.size(), equalTo((3)));
	    
    }
    
    @Test
    public void getOffers_whenNoOffers_returnsEmptyList() throws Exception{
 
	given(productService.findAllProducts()).willReturn(Collections.<Product>emptyList());

	MockHttpServletResponse response = mockMvc.perform(get("/products").contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn().getResponse();

	Collection<Product> responseProducts = new Gson().fromJson(response.getContentAsString(), new TypeToken<Collection<Product>>() {}.getType());
	
	assertThat(responseProducts.size(), equalTo((0)));
	    
    }

    @Test
    public void getOfferByProductId_whenOfferByProductIdFound_returnsProductFoundWithStatus200() throws Exception{
 
    	Product product = new Product.ProductBuilder().withId(111L).withDescription("PS 4")
		.withCategory(Category.GAME_CONSOLE).withPrice(new Price.PriceBuilder()
	    		.withCurrency(Currency.getInstance(Locale.US)).withAmount(new BigDecimal("199")).build()).build();

    	given(productService.findById(111L)).willReturn(product);

	MockHttpServletResponse response = mockMvc.perform(get("/products/{productId}" , 111L).contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn().getResponse();
	Product returnedProduct = new Gson().fromJson(response.getContentAsString(), Product.class);


	assertThat(returnedProduct.getId(), equalTo(111L));
	assertThat(returnedProduct.getCategory(), equalTo(Category.GAME_CONSOLE));
	assertThat(returnedProduct.getPrice().getAmount(), equalTo(new BigDecimal("199")));
	assertThat(returnedProduct.getPrice().getCurrency(), equalTo(Currency.getInstance(Locale.US)));
    }

    @Test
    public void getOfferByProductId_whenNoOfferByProductIdFound_returnsApiErrorWithHttp404Status() throws Exception{
 
	given(productService.findById(404L)).willReturn(null);

	MockHttpServletResponse response = mockMvc.perform(get("/products/{productId}" , 404L).contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound()).andReturn().getResponse();
	ApiError apiError = new Gson().fromJson(response.getContentAsString(), ApiError.class);
	
	assertThat(apiError.getMessage(), equalTo("Offered product by ID [404] is not found!"));
    }

    @Test
	public void getOffersByDescriptionLike_whenProductMatchingDescriptionLikeFound_returnsMatchedProducts()
			throws Exception {
    	Collection<Product> sampleProducts = Arrays.asList(
    			new Product.ProductBuilder().withId(1L).withDescription("JK Rowlings Harry Potter 2017")
    				.withCategory(Category.BOOK).withPrice(new Price.PriceBuilder()
    			    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("24.99")).build()).build(),

    			new Product.ProductBuilder().withId(3L).withDescription("Harry Potter Book1")
    				.withCategory(Category.HOME_ELECTRONICS).withPrice(new Price.PriceBuilder()
    			    		.withCurrency(Currency.getInstance(Locale.FRANCE)).withAmount(new BigDecimal("300")).build()).build());

		given(productService.findByDescriptionLike("Harry Potter"))
				.willReturn(sampleProducts);

		MockHttpServletResponse response = mockMvc.perform(get("/products/find-by-description?descriptionLike={descriptionLike}", "Harry Potter")
								.contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse();
		Collection<Product> responseProducts = new Gson().fromJson(response.getContentAsString(), new TypeToken<Collection<Product>>() {}.getType());

		assertThat(responseProducts.size(), equalTo((2)));
		assertThat(responseProducts.iterator().next().getDescription(), containsString(("Harry Potter")));
	}


    @Test
	public void getOffersByDescriptionLike_whenNoOfferByProductDescriptionLikeFound_returnsApiErrorWithHttp404Status()
			throws Exception {
    	Collection<Product> sampleProducts = Arrays.asList(
    			new Product.ProductBuilder().withId(1L).withDescription("JK Rowlings Harry Potter 2017")
    				.withCategory(Category.BOOK).withPrice(new Price.PriceBuilder()
    			    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("24.99")).build()).build(),

    			new Product.ProductBuilder().withId(3L).withDescription("Harry Potter Book1")
    				.withCategory(Category.HOME_ELECTRONICS).withPrice(new Price.PriceBuilder()
    			    		.withCurrency(Currency.getInstance(Locale.FRANCE)).withAmount(new BigDecimal("300")).build()).build());

		given(productService.findByDescriptionLike("Harry Potter"))
				.willReturn(sampleProducts);

		// NOT in DB
		given(productService.findByDescriptionLike("the description is not in the DB"))
				.willReturn(Collections.<Product> emptyList());

		MockHttpServletResponse response = mockMvc.perform(get("/products/find-by-description?descriptionLike={descriptionLike}", "the description is not in the DB")
								.contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse();
		Collection<Product> responseProducts = new Gson().fromJson(response.getContentAsString(), new TypeToken<Collection<Product>>() {}.getType());
		
		assertThat(responseProducts.size(), equalTo((0)));
	}
    
}
