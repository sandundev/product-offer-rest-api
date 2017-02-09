package com.sandundev.offergoods.config;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
public class ApplicationConfig {


	 private static final Logger logger = Logger.getLogger(ApplicationConfig.class);
	 
	// H2 in-memory db setup @ https://github.com/paulc4/microservices-demo/blob/master/src/main/java/io/pivotal/microservices/accounts/AccountsConfiguration.java
	
	 @Bean
	public DataSource dataSource() {
		logger.info("dataSource() invoked");

		// Create an in-memory H2 relational database containing some demo
		// accounts.
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schema.sql")
				.addScript("classpath:testdb/data.sql").build();

		logger.info("dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM product");
		logger.info("Table has " + results.size() + " records");

		// Populate with random balances
		Random rand = new Random();

		for (Map<String, Object> item : results) {
			String name = (String) item.get("name");
			BigDecimal balance = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
			jdbcTemplate.update("UPDATE product SET amount = ? WHERE name = ?", balance, name);
		}

		return dataSource;
	}

//	    @Bean
//	    public MethodValidationPostProcessor methodValidationPostProcessor() {
//	         
//	        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
//	        processor.setValidator(validator());
//	        return processor;
//	    }
//	     
//	    @Bean
//	    public Validator validator() {
//	        return new LocalValidatorFactoryBean();
//	    } 
}
