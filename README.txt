COMP-3005-Database-Project

Author: Justin Tareq

Student ID: 101114161

                            Important Things To Consider
                            
1. When choosing what functions to use as an administrator or as a regular user, use integer values. 
As a regular user, you have access to the fuctions get_books, get_book_by_title,                     get_booK_by_author, get_book_by_ISBN, get_book_by_genre, add_to_basket, checkout,                       track_order, and log_out from options 1 to 9.  As the administrator, you have access to the options add_book, remove_book, create_publisher,remove_publisher, sales_expenditure_report, sales_by_genre, and sales_by_author from options 1-7. 

2. As the administrator, when entering values, be sure that they are up to a certain length. 
Be aware of these things: ISBN varchar(13), title varchar(40), genre varchar(20),year numeric(4,0),
price float, number_of_pages numeric(4,0), percentage  numeric(5,2), number_of_books numeric(6,0),
phone_number varchar(60), and phone_type varchar(9). For percentage, it would be wise to enter
values such as 0.52 as in 52% or 0.97 as in 97%. For phone_type, it can be either home, cellphone,
or work. 

3. As the user, when entering values, be sure that they are up to a certain length. Be aware of these things: ISBN varchar(13), title varchar(40), genre varchar(20), first_name varchar(60),last_name varchar(60), shipping_date varchar(20), street_address varchar(20), city varchar(20), postal_code varchar(6),country varchar(20),card_type varchar(20), card_number varchar(16), card_expiry_date varchar(20), three_digit_code varchar(3), and order_date varchar(20). You'll know when you have
to enter these values. Also, first_name varchar(60) and last_name varchar(60) are when you're entering
the information regarding the author as well as the person who is being billed an order and the person
who is getting a shipped product. 

                            Instructions
                            
1. First login as either a regular user or as an administrator. 

2. If you decide to login as a regular user: 
        1. Enter 'user1' as your username and 'password' as your password. 
        2. As a regular user, you have access to the fuctions get_books, get_book_by_title,                     get_booK_by_author, get_book_by_ISBN, get_book_by_genre, add_to_basket, checkout,                       track_order, and log_out from options 1 to 9. 
        3. As a precaution to step 2, the user must only type choices 1-9. So they must read the 
        menu very cautiously. 
        4. If certain information has no existence, the program will not crash, but it will inform 
        you that the information has no existence. 
        5. To logout, enter option 9. That will lead you to exit the program and for your basket 
        to be empty. 
        
3. If you decide to login as the owner of the store: 
        1. Enter 'admin' as your username and 'admin' as your password.
        2. As the administrator, you have access to the options add_book, remove_book, create_publisher,
        remove_publisher, sales_expenditure_report, sales_by_genre, and sales_by_author from options 1-         7.
        3. As a precaution to step 2, the user must only type choices 1-9. So they must read the 
        menu very cautiously. 
        4. If certain information cannot be entered, the program will not crash, but it will inform 
        you that the information cannot be entered and not accept it into the database. 
        5. To logout, enter option 8. That will lead you to exit the loop 
        
        
        
        
        
        
        
        
        
        
        
