package com.sandundev.offergoods.controller;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sandundev.offergoods.exception.ApiError;
import com.sandundev.offergoods.exception.ProductNotFoundException;
import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.service.ProductService;

@RestController
public class ProductController {
	private static final Logger LOGGER = Logger.getLogger(ProductController.class);
	 
    private ProductService productService;
    
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Product>> getOffers() {
		Collection<Product> allProducts = productService.findAllProducts();
		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getOfferByProductId(@PathVariable("productId") Long productId) {
		Product product = productService.findById(productId);
		if (product == null) {
			throw new ProductNotFoundException("Offered product by ID ["+ productId + "] is not found!");
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products/find-by-description", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Product>> getOffersByDescriptionLike(@RequestParam("descriptionLike") String descriptionLike) {
		 Collection<Product> matchedProducts = productService.findByDescriptionLike(descriptionLike);
		 return new ResponseEntity<>(matchedProducts, HttpStatus.OK);
	}
	
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ApiError> handleProductNotFoundException(ProductNotFoundException exception, WebRequest request) {
		LOGGER.error(exception.getMessage());
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
		LOGGER.error(exception.getMessage());
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
