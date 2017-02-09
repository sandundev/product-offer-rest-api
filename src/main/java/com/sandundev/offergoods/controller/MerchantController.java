package com.sandundev.offergoods.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sandundev.offergoods.exception.ApiError;
import com.sandundev.offergoods.exception.ProductNotFoundException;
import com.sandundev.offergoods.model.MerchantAccount;
import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.service.MerchantService;

@RestController
public class MerchantController {
	private static final Logger LOGGER = Logger.getLogger(MerchantController.class);
	 
	private MerchantService merchantService;
	
	@Autowired
	public MerchantController(MerchantService merchantService){
		this.merchantService = merchantService;
	}
	
	@RequestMapping(value = "/merchants", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MerchantAccount> createAccount(@RequestBody MerchantAccount account, HttpServletResponse response) {
		MerchantAccount  savedAccount = merchantService.createAccount(account);
		return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/merchants/{merchantId}/products", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> createOffer(@PathVariable("merchantId") @NotNull Long merchantId,  @RequestBody @Valid Product product, HttpServletResponse response) {
		Product savedProduct = merchantService.offerProduct(merchantId, product);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
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
