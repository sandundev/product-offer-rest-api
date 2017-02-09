package com.sandundev.offergoods.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sandundev.offergoods.model.MerchantAccount;

@Repository
public interface MerchantAccountRepository extends CrudRepository<MerchantAccount, Long> {

}