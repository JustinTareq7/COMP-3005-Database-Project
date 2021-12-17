create table bank_account(
	account_number varchar(30), 
	bank_number numeric(6,0), 
	branch_number numeric(6,0), 
	amount_deposited numeric(9,2), 
	balance numeric(9,2), 
	primary key(account_number)
); 

create table publisher(
	publisher_id serial, 
	company_name varchar(60),
	address varchar(60), 
	email_address varchar(60),
	website varchar(60), 
	account_number varchar(30), 
	primary key(publisher_id),
	foreign key (account_number) references bank_account
	
); 

create table contact(
	phone_number varchar(60),
	phone_type varchar(9),
	publisher_id serial,
	primary key (phone_number),
	foreign key (publisher_id) references publisher 
);

create table book(
	ISBN varchar(13),
	title varchar(40),
	genre varchar(20),
	year numeric(4,0),
	price float, 
	number_of_pages numeric(4,0),
	percentage  numeric(5,2),
	number_of_books numeric(6,0),
	publisher_id serial,
	primary key(ISBN),
	foreign key (publisher_id) references publisher 
);


create table author(
	author_id serial,
	first_name varchar(60),
	last_name varchar(60), 
	primary key(author_id)
); 

create table author_book(
	ISBN varchar(13),
	author_id integer,
	primary key(author_id,ISBN),
	foreign key (ISBN) references book,
	foreign key (author_id) references author
); 

create table shipping_info( 
	shipping_id serial,
	first_name varchar(60),
	last_name varchar(60), 
	shipping_date varchar(20), 
	street_address varchar(20), 
	city varchar(20), 
	postal_code varchar(6),
	country varchar(20),
	current_location varchar(20), 
	primary key (shipping_id)
); 

create table billing_info(
	billing_id serial,
	first_name varchar(60),
	last_name varchar(60), 
	city varchar(20), 
	postal_code varchar(6),
	country varchar(20),
	street_address varchar(20), 
	card_type varchar(20), 
	card_number varchar(16), 
	card_expiry_date varchar(20), 
	three_digit_code varchar(3),
	primary key (billing_id)
); 

create table store_owner(
	owner_id varchar(20),
	store_name varchar(50), 
           primary key (owner_id)
); 

create table user_account( 
	user_account_id varchar(20), 
	password varchar(20), 
	email_address varchar(60), 
	phone_number varchar(60),
	owner_id varchar(20),
	primary key(user_account_id),
	foreign key (owner_id) references store_owner
	
);

create table user_account_book(
	ISBN varchar(13),
	user_account_id varchar(20),
           	quantity numeric (5,0), 
	basket_id serial, 
	primary key(basket_id),
	foreign key (ISBN) references book,
	foreign key (user_account_id) references user_account 
);

create table stock_order(
	order_id serial,
	order_date varchar(20), 
	shipping_id integer,
	billing_id integer,
	user_account_id varchar(20), 
	primary key(order_id),
	foreign key (shipping_Id) references shipping_info,
	foreign key (billing_id) references billing_info,
	foreign key (user_account_id) references user_account
); 

create table order_items(
	ISBN varchar(13),
	order_id integer,
	quantity numeric(5,0), 
	item_id serial, 
	primary key(item_id), 
	foreign key (ISBN) references book, 
	foreign key (order_id) references stock_order
); 

create table owner_order(
    ISBN varchar(13),
	order_date varchar(20), 
	price numeric(3,0),
	number_of_books numeric(6,0),
	order_id serial,
	primary key (order_id) 

);

insert into bank_account values ('111111',1111,1111,10000,70000); 
insert into bank_account values ('222222',2222,2222,10000,100000);

insert into publisher values (DEFAULT,'Bloomsbury','1217 Flanders Road','bloomsburypublishing@gmail.com','https://www.bloomsbury.com/us/','111111');

insert into publisher values(DEFAULT,'Titans Books','1212 Titans Drive','titansbooks@gmail.com','https://titanbooks.com/','222222');


insert into contact values('613-726-7111','work',1); 
insert into contact values('613-766-6172','work',2); 

insert into book values('9781785651458','Gotham: Dawn of Darkness','Crime-Drama',2014,7.60,126,0.19,2); 
insert into book values('0747532699','Harry Potter and the Philosopher’s Stone','Fantasy',1997,26.42,160,0.12,1); 

insert into author values(DEFAULT,'Jason','Starr'); 
insert into author values(DEFAULT,'J.K','Rowling'); 


Insert into author_book values('9781785651458',1); 
insert into author_book values('0747532699',2); 

Insert into shipping_info values(DEFAULT,'Draco','Malfoy','July 17, 2021','1200 Monty Road','Atlantic City','K692D9','America','Warehouse District'); 

Insert into billing_info values(DEFAULT,'Draco','Malfoy','Atlantic City','K692D9','America','1200 Monty Road','Visa','5526251015892550','07/2024','240'); 

Insert into user_account values('user1','password','dracomalfoy@gmail.com','613-812-4212',null); 


insert into store_owner values('owner','Look Inna Book'); 

Insert into user_account values('admin','admin','bookstore_owner@gmail.com','613-219-6612','owner'); 

insert into user_account_book values('0747532699','user1'); 
insert into  stock_order values(DEFAULT,'2021/07/12',1,1,'user1'); 

CREATE OR REPLACE FUNCTION place_owner_order()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
 declare numOfBooks integer;
BEGIN
	IF NEW.number_of_books < 10 THEN
		
  
		  select sum(quantity) into numOfBooks
		  from order_items oi, stock_order so
		  where oi.order_id = so.order_id and
		  oi.isbn = NEW.isbn and
		  DATE_PART('month',TO_DATE(so.order_date, 'YYYY/MM/DD'))  = DATE_PART('month',CURRENT_DATE) -1 ;

		  insert into owner_order(ISBN,order_date,number_of_books,price)
		  values (NEW.isbn,CURRENT_DATE,numOfBooks,50);
	END IF;

	RETURN NEW;
END;
$$

CREATE TRIGGER tr_add_new_books AFTER UPDATE OF number_of_books ON book
for each row
EXECUTE PROCEDURE place_owner_order();
