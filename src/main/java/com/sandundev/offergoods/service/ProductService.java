package com.sandundev.offergoods.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandundev.offergoods.model.Product;
import com.sandundev.offergoods.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
    	this.productRepository = productRepository;
    }

    public Collection<Product> findAllProducts() {
		Iterator<Product> it = productRepository.findAll().iterator();
		Collection<Product> products = new ArrayList<>();
		while (it.hasNext()) {
			products.add(it.next());
		}
		return products;
    }

    public Product findById(Long productId) {
    	return productRepository.findOne(productId);
    }

	public Collection<Product> findByDescriptionLike(String descriptionLike) {
		return productRepository.findByDescriptionLike("%"+descriptionLike+"%");
	}

}
