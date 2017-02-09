package com.sandundev.offergoods.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sandundev.offergoods.model.Price;
import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.model.constants.Category;
import com.sandundev.offergoods.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
	
    @Mock
    private ProductRepository productRepository;
    private ProductService productService;
    
    @Before
    public void setup() {
	 productService = new ProductService(productRepository);
    }
    
    @Test
    public void findById_whenProductFound_shouldReturnProduct() {
	Product product = new Product.ProductBuilder()
		.withId(100L).withDescription("Old Book 1").withCategory(Category.BOOK)
	    	.withPrice(new Price.PriceBuilder()
	    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("100")).build()).build();

	when(productRepository.findOne(product.getId())).thenReturn(product);
	
	Product foundProduct = productService.findById(product.getId());
	assertThat(foundProduct.getId(), equalTo(100L));
	assertThat(foundProduct.getDescription(), equalTo("Old Book 1"));
	assertThat(foundProduct.getCategory(), equalTo(Category.BOOK));
	assertThat(foundProduct.getPrice().getAmount(), equalTo(new BigDecimal("100")));
	assertThat(foundProduct.getPrice().getCurrency(), equalTo(Currency.getInstance(Locale.UK)));
    }
    
    @Test
    public void findById_whenProductNotFound_shouldReturnNull() {
	Product product = new Product.ProductBuilder()
		.withId(100L).withDescription("Old Book 1").withCategory(Category.BOOK)
	    	.withPrice(new Price.PriceBuilder()
	    		.withCurrency(Currency.getInstance(Locale.UK)).withAmount(new BigDecimal("100")).build()).build();

	when(productRepository.findOne(product.getId())).thenReturn(null);

	assertThat(productService.findById(product.getId()), nullValue());
	 
    }
    
    @Test
    public void findAllProducts_when3ProductsAvailable_returns3OfferedProducts() throws Exception{
 
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

	when(productRepository.findAll()).thenReturn(products);
 
	Collection<Product> foundProducts = productService.findAllProducts();

	assertThat(foundProducts.size(), equalTo((3)));
	assertThat(foundProducts.stream().map(Product::getDescription).collect(Collectors.toSet()), hasItems("Dyson DC40 3","Harry Potter Book 1","PS 4"));
	assertThat(foundProducts.stream().map(Product::getId).collect(Collectors.toSet()), hasItems(1L,	2L,	3L));
	 
	    
    }
}
