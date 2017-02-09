package com.sandundev.offergoods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public final class Price implements Serializable{

	private static final long serialVersionUID = -8834489085717067196L;
	
	private BigDecimal amount;
    private String currency;

    public Price(PriceBuilder priceBuilder) {
	this.amount = priceBuilder.getAmount();
	this.currency = priceBuilder.getCurrency().getCurrencyCode();
    }
    
    protected Price(){}
    
    public static class PriceBuilder{
	    private BigDecimal amount;
	    private Currency currency;

	    public PriceBuilder withAmount(BigDecimal amount) {
	        this.amount = amount;
	        return this;
	    }
	    
	    public Currency getCurrency() {
			return currency;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public PriceBuilder withCurrency(Currency currency) {
	        this.currency = currency;
	        return this;
	    }
	    
	    public Price build(){
	    	return new Price(this);
	    }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
	 return Currency.getInstance(currency);
    }
}
