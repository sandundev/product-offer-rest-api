drop table product if exists;
 
CREATE TABLE merchant_account (
	id bigint identity primary key,
	name varchar(500),
	address varchar(500),
	phone_number varchar(20)
    );

    
CREATE TABLE product (
	id bigint identity primary key,
	merchant_account_id bigint,
	name varchar(255),
	description varchar(255),
	category varchar(50),
	amount decimal(8,2),
	currency varchar(50)
    );
                    

    ALTER TABLE product ALTER COLUMN amount SET DEFAULT 0.0;