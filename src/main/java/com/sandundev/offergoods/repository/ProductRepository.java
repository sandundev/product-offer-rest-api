package com.sandundev.offergoods.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sandundev.offergoods.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	Collection<Product> findByDescriptionLike(String string);

}