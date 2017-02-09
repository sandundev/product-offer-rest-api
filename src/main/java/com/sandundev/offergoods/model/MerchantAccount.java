package com.sandundev.offergoods.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class MerchantAccount implements Serializable{

	private static final long serialVersionUID = -45633233179L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty(message = "name should not be empty")
	private String name;
	@NotEmpty(message = "address should not be empty")
	private String address;
	@NotEmpty(message = "phone number should not be empty")
	private String phoneNumber;
    
	@OneToMany(mappedBy = "merchantAccount")
    private Set<Product> products = new HashSet<>();
    
	private MerchantAccount(MerchantAccountBuilder merchantAccountBuilder) {
		this.id = merchantAccountBuilder.id;
		this.name = merchantAccountBuilder.name;
		this.address = merchantAccountBuilder.address;
		this.phoneNumber = merchantAccountBuilder.phoneNumber;
	}	
	public MerchantAccount() {}
	
	public static class MerchantAccountBuilder {
		private Long id;
		private String name;
		private String address;
		private String phoneNumber;
		public MerchantAccountBuilder withId(Long id) {
			this.id = id;
			return this;
		}
		public MerchantAccountBuilder withName(String name) {
			this.name = name;
			return this;
		}
		public MerchantAccountBuilder withAddress(String address) {
			this.address = address;
			return this;
		}
		public MerchantAccountBuilder withPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		
		public MerchantAccount build(){
			return new MerchantAccount(this);
		}
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MerchantAccount other = (MerchantAccount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
