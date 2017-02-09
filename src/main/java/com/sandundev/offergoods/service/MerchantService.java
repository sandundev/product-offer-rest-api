package com.sandundev.offergoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandundev.offergoods.exception.MerchantAccountNotFoundException;
import com.sandundev.offergoods.model.MerchantAccount;
import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.repository.MerchantAccountRepository;
import com.sandundev.offergoods.repository.ProductRepository;

@Service
public class MerchantService {

	private MerchantAccountRepository merchantAccountRepository;
	private ProductRepository productRepository;

	@Autowired
	public MerchantService(MerchantAccountRepository merchantAccountRepository, ProductRepository productRepository){
		this.merchantAccountRepository = merchantAccountRepository;
		this.productRepository = productRepository;
	}
	
	public MerchantAccount createAccount(MerchantAccount account) {
		return merchantAccountRepository.save(account);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public Product offerProduct(Long merchantId, Product product) throws MerchantAccountNotFoundException{
		MerchantAccount account = merchantAccountRepository.findOne(merchantId);
		if(account == null){
			throw new MerchantAccountNotFoundException("Merchant account is not found for Id ["+merchantId+"]");
		}
		return productRepository.save(new Product(product, account));
	}


}
