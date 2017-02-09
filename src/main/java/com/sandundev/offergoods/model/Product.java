package com.sandundev.offergoods.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sandundev.offergoods.model.constants.Category;

@Entity
@JsonInclude(Include.NON_NULL)
public final class Product implements Serializable  {

	private static final long serialVersionUID = -472951656491L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty(message = "name should not be empty")
	private String name;
	@Size(min=5, max=500,message = "description should contains min of 5 to 500 charactors.")
	private String description;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "category should not be empty")
	private Category category;
	@NotNull(message = "Price should not be empty")
	@Embedded
	private Price price;
	
	@JsonIgnore
    @ManyToOne
	private MerchantAccount merchantAccount;
	
	private Product(ProductBuilder builder){
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.price = builder.price;
		this.category = builder.category;
		this.merchantAccount = builder.merchantAccount;
	}
	
	public Product(Product product, MerchantAccount merchantAccount){//Copy constructor
		this.id = product.id;
		this.name = product.name;
		this.description = product.description;
		this.price = product.price;
		this.category = product.category;
		this.merchantAccount = merchantAccount;
	}
	
	protected Product(){}
	
	public static class ProductBuilder {
		private Long id;
		private String name;
		private String description;
		private Price price;
		private Category category;
		public MerchantAccount merchantAccount;
		
		public ProductBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ProductBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		public ProductBuilder withCategory(Category category) {
			this.category = category;
			return this;
		}
		public ProductBuilder withPrice(Price price) {
			this.price = price;
			return this;
		}
		public ProductBuilder withId(long id) {
			this.id = id;
			return this;
		}

		public ProductBuilder withMerchantAccount(MerchantAccount account) {
			this.merchantAccount = account;
			return this;
		}
		
		public Product build(){
			return new Product(this);
		}
		
		public Category getCategory() {
			return category;
		}
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
	Product other = (Product) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}

	public Price getPrice() {
		return price;
	}

	public MerchantAccount getMerchantAccount() {
		return merchantAccount;
	}

}
