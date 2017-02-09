
INSERT INTO merchant_account(id,name,address,phone_number)
values(1,'sandun lewke bandara','11 Highfield Avenue,London, NW9 0PX','07120150121');
INSERT INTO merchant_account(id,name,address,phone_number)
values(2,'sandun lewke bandara','12 Highfield Avenue,London, NW1 0PB','07120150122');
INSERT INTO merchant_account(id,name,address,phone_number)
values(3,'sandun lewke bandara','13 Highfield Avenue,London, NW2 0PD','07120150123');
INSERT INTO merchant_account(id,name,address,phone_number)
values(4,'sandun lewke bandara','14 Highfield Avenue,London, NW3 0PQ','07120150124');


INSERT INTO product(id,merchant_account_id,category,amount,currency,name,description) 
			values(1,1,'BOOK','24.99','GBP','Harry Potter Book','Harry Potter and the Chamber of Secrets is the second novel in the Harry Potter series, written by J. K. Rowling.');
INSERT INTO product(id,merchant_account_id,category,amount,currency,name,description) 
			values(2,1,'BOOK','79.99','GBP','Holy Harry Potter Book 2','Description of Harry Potter Book 2 - Harry Potter and the Chamber of Secrets is the second novel in the Harry Potter series, written by J. K. Rowling.');
INSERT INTO product(id,merchant_account_id,category,amount,currency,name,description) 
			values(3,1,'GAME_CONSOLE','299.99','EUR','PS 3','Play Staton 3 by Sony');
INSERT INTO product(id,merchant_account_id,category,amount,currency,name,description) 
			values(4,2,'GAME_CONSOLE','99.99','EUR','Nintendo DS 2','Nintendo DS 2 by Super Mario');

