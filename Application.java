

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.lang.*;

public class Test {
   
	    private static String postgres_user_id = "postgres";
        private static String  postgres_password = "Doakes78";
        private static Connection connection; 
        private static Scanner sc = new Scanner(System.in);
        private static String user_account_id = ""; 
	
        
	    public static void main(String[] args){
	    	
	    	connection = getConnection();
	    	
	    	int choice = 0;
	    	
	    	String user_id = ""; 
	    	
	    	System.out.println();
	    		    	
	    	user_account_id = login(); 
	    	
            if(user_account_id.equals("admin")) {
            	            	
            		get_admin_choice(); 
               
            	
            }else{
            	
                get_user_choice(); 
            }            
                    

	    }
	    
	    public static Connection getConnection(){
	    	
	    	Connection connection = null;
	    	
			try {
				
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", postgres_user_id,
						postgres_password);
				
			} catch (SQLException e) {
				
				System.out.println("Error: "+ e.getMessage());
			}
	    	
	    	
	    	return connection;
	    }
	    

	public static void remove_book(){
	    
	    try{
	    	
	    	String book_title = ""; 
	    	
	    	ResultSet result_set;
	    		        
	        System.out.print("Enter the title of the book you want out of the store: ");
	            
	        book_title = sc.nextLine();
	        
	        Statement statement = connection.createStatement();
	
	        String title_query = "select title,ISBN from book where title = \'" + book_title +"\'"; 
	        
	        result_set = statement.executeQuery(title_query);
	        
	        String ISBN = ""; 
	        		            	            
		   if(result_set.next() == true) {
			   
			   System.out.println("Book being removed...."); 
		       
			   ISBN = result_set.getString("ISBN"); 
		       
		       String author_book_removal = "delete from author_book where ISBN = \'" + ISBN +"\'"; 
		       		   
		       String book_removal = "delete from book where title = \'" + book_title +"\'"; 
		       
		       statement.executeUpdate(author_book_removal); 
	           
	           statement.executeUpdate(book_removal); 
	           
	   	       System.out.println("Book is removed"); 

		    
		   }
	        
	        
	    }catch (Exception error) {
	
		   System.out.println("Exception : " + error.getMessage());
		}
	    
	}

	public static String create_bank_account(){
	    
	    String account_number = ""; 
	    
	    System.out.println();
	    
	    System.out.println("Entering Publisher Bank Account...");
	    
	    System.out.println(); 
	
	   try{
	       
	       
	       System.out.println("Enter the account number of the publisher: "); 
	       
	       account_number = sc.nextLine(); 
	         
	              
	      System.out.println("Enter bank number : " );
	      
	      int bank_number = sc.nextInt(); 
	      
	      System.out.println("Enter branch number : " );
	
	      int branch_number  = sc.nextInt(); 
	              
	            
	      String insertion_query = "insert into bank_account (account_number,bank_number,branch_number)"+
	              " values (?,?,?)";
	
	      PreparedStatement prepared_statement = connection.prepareStatement(insertion_query);
	   	       
	      prepared_statement.setString(1, account_number);
	   	       
	      prepared_statement.setInt(2, bank_number);
	   	       
	      prepared_statement.setInt(3, branch_number);
	   	       
	   	       
	      prepared_statement.execute();
	   
	       
	   }catch (Exception error) {
	
		            System.out.println("Exception : " + error.getMessage());
		            
		}
		
		return account_number;
	}


	public static void create_contact(int publisher_id){
		
		System.out.println();
		
		System.out.println("Introducing Publisher Contact...");
		
		System.out.println(); 
		
		String phone_number = ""; 
	    
	    try{
	        
	        System.out.println("Enter the phone number of the publisher: "); 
	        
	        phone_number = sc.nextLine(); 
	                
	        String phone_check_query = "select phone_number from contact where phone_number = \'" + phone_number + "= \'";
	        
	        ResultSet result_set;
	        
	        Statement statement = connection.createStatement();
	
	        
	        result_set = statement.executeQuery(phone_check_query);
	
	           if(result_set.next() == true) {
	               
	               System.out.println("The phone number already exists....");
	               
	
	           }else{
	               
	               System.out.println("Enter phone type : " );
	               
	               String phone_type = sc.nextLine(); 
	               
	               System.out.println("PHONE NUMBER: " + phone_number); 
	               
	               String insertion_query = "insert into contact (phone_number,phone_type,publisher_id)"+
	               " values (?,?,?)";
	               
	               System.out.println("2) PHONE NUMBER: " + phone_number); 
	 
	               PreparedStatement prepared_statement = connection.prepareStatement(insertion_query);
	    	     
	               prepared_statement.setString(1, phone_number);
	    	     
	               prepared_statement.setString(2, phone_type);
	    	     
	               prepared_statement.setInt(3, publisher_id);
	    	       
	    	       prepared_statement.executeUpdate();
	       }
	       
	    }catch (Exception error) {
	
		            System.out.println("Exception : " + error.getMessage());
		}
		
	}

public static int create_publisher(){
	
	System.out.println();
	
	System.out.println("Introducing publisher...");
	
	System.out.println(); 
	
	int publisherID = 1;
	
    try{
                
        System.out.println("Enter the name of publishing company: "); 
        
       String  company_name = sc.nextLine();
        
       System.out.println("Enter Address: "); 
       
       String  address = sc.nextLine();
       
       System.out.println("Enter Email Address: "); 
       
       String  eaddress = sc.nextLine();
       
       System.out.println("Enter Web Site URL: "); 
       
       String  website = sc.nextLine();
  
        String insertion_query = "insert into publisher (company_name,address,email_address,website,account_number)"+
               " values (?,?,?,?,?)";
               
        String account_number = create_bank_account(); 
               
        PreparedStatement prepared_statement = connection.prepareStatement(insertion_query,Statement.RETURN_GENERATED_KEYS);
    	     
         prepared_statement.setString(1, company_name);
    	      
         prepared_statement.setString(2, address);
    	      
         prepared_statement.setString(3, eaddress);
    	      
         prepared_statement.setString(4, website);
    	      
         prepared_statement.setString(5,account_number); 

    	      
         prepared_statement.executeUpdate();
    	       
    	 
         ResultSet rs = prepared_statement.getGeneratedKeys();
    	       
         if(rs.next()){
    	    	   
    	    	   publisherID = rs.getInt(1);
    	       
         }
    	       
    	       
         sc.nextLine(); 
    	       
    	       
         create_contact(publisherID); 
     
        
	        
	    }catch (Exception error) {
	
		            System.out.println("Exception : " + error.getMessage());
		}
		
	     return publisherID; 
	
		
	}

	    
	    public static void print_admin_menu(){
	    	
	    	System.out.println("\n1-Add Books");
	    	System.out.println("2-Remove Books");
	    	System.out.println("3-Add Publishers");
	    	System.out.println("4-Remove Publishers");
	    	System.out.println("5-Print Sales vs Expenses Report");
	    	System.out.println("6-Print Sales per Genre Report");
	    	System.out.println("7-Print Sales per Author Report");
	    	System.out.println("8-Log out");
	    }
	    
	    public static void print_user_menu(){
	    	
	    	System.out.println("\n1-Browse Books");
	    	System.out.println("2-Search by Book title");
	    	System.out.println("3-Search by Book author");
	    	System.out.println("4-Search by Book ISBN");
	    	System.out.println("5-Search by Book Genre");
	    	System.out.println("6-Add items to Basket");
	    	System.out.println("7-Checkout");
	    	System.out.println("8-Track order");
	    	System.out.println("9-Log out");
	    	
	    	
	    }

		
		public static void remove_publisher(){
			    	
			    	try{
			    	    
		    		 	String company_name = ""; 
		    		 	
		    		 	System.out.print("Enter name of publisher: ");
		            
		    		 	company_name = sc.nextLine();
				        
			            Statement statement = connection.createStatement();
			            
			            ResultSet result_set;
			            
			            String publishing_company = "select company_name, publisher_id, account_number from publisher where company_name = \'" + company_name + "\'";
			            	            
			            result_set = statement
								.executeQuery(publishing_company);
			            	            
			            if(result_set.next() == false) {
			            	
			            	System.out.println("This store currently does not accept books from " + company_name); 
								            	
			            }else {
			            		            	
			            	Integer publisher_id = result_set.getInt("publisher_id");
			            	
			            	Integer account_number = result_set.getInt("account_number"); 
			            		            	
		                    String contact_removal = "delete from contact where publisher_id = \'" + publisher_id +"\'"; 
			            	
		                    String account_removal = "delete from bank_account where account_number = \'" + account_number +"\'"; 
		                                        
		                    String book_removal = "delete from book where publisher_id = \'" + publisher_id +"\'"; 
		                    
		                    String publisher_removal = "delete from publisher where publisher_id = \'" + publisher_id +"\'";
		                    
		                    
		                    
		                    
		                    statement.executeUpdate(contact_removal); 
		                    
		                    statement.executeUpdate(book_removal); 
		                                  
		                    statement.executeUpdate(publisher_removal);
		                    
		                    statement.executeUpdate(account_removal);
		                    
		                    System.out.println("Publisher has been removed"); 
		                    
		                  
			            }
			
					
					
		    	 }catch (Exception error) {
		
			            System.out.println("Exception : " + error.getMessage());
			     }
			    	
		}

	
	public static void sales_by_genre(){
		
				System.out.println();
				
				System.out.println("Sales Per Genre Report..............");
				
				System.out.println(); 
		    	
		    	try{
		    	    
		            Statement statement = connection.createStatement();
		            
		            ResultSet result_set;
		            
		            String sales_query = "select sum(quantity)*price,sum(quantity), price,book.genre from book,order_items where book.ISBN = order_items.ISBN group by book.price,book.genre"; 
		            	            
		            result_set = statement.executeQuery(sales_query);
		            	        
		            if(result_set.next() == false) {
		            	
	                    System.out.println("There were no sales made"); 
	                    		            	
		            }else {
		            	
		                do {
		                	
		                    
		                    System.out.println("Genre : " + result_set.getString("genre")); 
		                    
		                    System.out.println("Price : " + result_set.getFloat("price")); 
		                    
		                    System.out.println("Books Ordered : " + result_set.getInt("sum")); 
		                    
		            		System.out.printf("Sales: $ %.2f", result_set.getFloat("?column?"));
	
		                    	
		                    System.out.println(); 
		                    
		                    
		                    System.out.println(); 
		                    
		            		
		            	
		            	}while(result_set.next());
									            	
		            }
						
	    	 }catch (Exception error) {
	
		            System.out.println("Exception : " + error.getMessage());
		     }
	}
		    	
	    	
	
	public static void sales_by_author(){
		
		System.out.println();
		
		System.out.println("Sales Per Author Report..............");
		
		System.out.println(); 
		
			try{
			    
		        Statement statement = connection.createStatement();
		        
		        ResultSet result_set;
		        
		        String sales_query = "select first_name,last_name,author_book.author_id,sum(quantity)*price from book,order_items,author_book,author where book.ISBN = order_items.ISBN and book.ISBN = author_book.ISBN and author_book.ISBN = order_items.ISBN and author_book.author_id = author.author_id group by book.price,author.first_name,author.last_name,author_book.author_id";
		    	        
		        result_set = statement.executeQuery(sales_query);
		        	            
		        if(result_set.next() == false) {
		        	
		            System.out.println("There were no sales made"); 
		            
		        	
		        }else {
		        	
		            do {
		            	
		            	System.out.println(); 
		                
		                System.out.println("Author Name : " + result_set.getString("first_name") + " " + result_set.getString("last_name")); 
		                
		                System.out.println("Author ID : " + result_set.getInt("author_id"));
		                
		                System.out.printf("Sales: $ %.2f", result_set.getFloat("?column?"));
		
		                System.out.println(); 
		                
		
		        	}while(result_set.next());
								        	
		        }
		
			
		 }catch (Exception error) {
		
		        System.out.println("Exception : " + error.getMessage());
		 }
	
	
	}

	public static void sales_expenditures_report(){
		
		System.out.println();
		
		System.out.println("Sales and Expenditures Report..............");
		
		System.out.println(); 
		
			try{
			    
		        Statement statement = connection.createStatement();
		        
		        ResultSet result_set;
		        
		        String the_query = "select sum(quantity),book.price,book.title,book.percentage from book,order_items where book.ISBN = order_items.ISBN group by book.title,book.price,book.percentage"; 
			            
		        result_set = statement.executeQuery(the_query);
		        	            
		        if(result_set.next() == false) {
		        	
		            System.out.println("There were no sales made"); 
		            		        	
		        }else {
		        	
		        	double total_earned = 0.0; 
		        	
		        	double total_expenditure = 0.0; 
		        	
		        	double total_profit = 0.0; 
		        	
		        	
		            do {
		            	
		            	System.out.println(); 
		            	
		            	System.out.println("--------------" + result_set.getString("title") + "-----------"); 
		            	
		            	Integer quantity = result_set.getInt("sum"); 
		            	
		            	float price = result_set.getFloat("price"); 
		            	
		            	float percentage = result_set.getFloat("percentage");
		            	
		            	float money_earned = quantity * price; 
		            	
		            	total_earned += money_earned; 
		            	
		            	float expenditure = quantity * price * percentage; 
		            	
		            	total_expenditure += expenditure; 
		            	
		            	float profit = money_earned - expenditure; 
		            	
		            	total_profit += profit; 
		            	
	
		            	System.out.printf("Money Earned: $ %.2f\n", money_earned);
		            	
		            	System.out.printf(" Expenditure: $ %.2f",expenditure); 
		            	
		            	System.out.printf(" Profit: $ %.2f",profit); 
		            	
		                System.out.println(); 
		
		        	}while(result_set.next());
		            
		            System.out.println(); 
		            
		            System.out.println("------------Full Store Report--------------------");
		            
		            System.out.printf("Total Earned : $ %.2f",total_earned); 
		            
		            System.out.printf(" Total Expenditure : $ %.2f",total_expenditure); 
		            
		            System.out.printf(" Total Profit : $ %.2f\n",total_profit); 
		           
		            
						
		        }
		
			
		 }catch (Exception error) {
		
		        System.out.println("Exception : " + error.getMessage());
		 }
	
	}


	public static Object[] book_maker(Integer publisher_id){
	    
	    Object[] book = new Object[9]; 
	        
	    System.out.println("Insert book ISBN ");
	    
	    String ISBN = sc.nextLine();
	            
	    System.out.println("Insert book genre ");
	    
	    String genre = sc.nextLine();
	    
	    System.out.println("Insert book year ");
	    
	    Integer year = sc.nextInt();
	    
	    System.out.println("Enter the price of the book "); 
	    
	    Float price = sc.nextFloat(); 
	    
	    System.out.println("Insert the number of pages of a book ");
	    
	    Integer number_of_pages = sc.nextInt();
	    
	    System.out.println("Insert book percentage ");
	    
	    Float percentage = sc.nextFloat();
	    
	    System.out.println("Insert the number of books: ");
	    
	    Float number_of_books = sc.nextFloat();
	    
	    book[0] = ISBN; 
	        
	    book[2] = genre; 
	    
	    book[3] = year; 
	    
	    book[4] = price; 
	    
	    book[5] = number_of_pages; 
	    
	    book[6] = percentage; 
	    
	    book[7] = number_of_books; 
	    
	    book[8] = publisher_id;  
	    
	    return book;
	 }

	public static void add_book(){
	    
	    try{
	        
	        int publisher_id = 0; 
	        
	        int author_id = 0; 
	                
	        Statement statement = connection.createStatement();
		            
		    ResultSet result_set;
	
	        String book_title = ""; 
	    		 	
	    	System.out.print("Enter the title of the book you want in the store: ");
	    	
	    	book_title = sc.nextLine();
	        
	        String title_query = "select title from book where title = \'" + book_title +"\'"; 
	        
	        result_set = statement.executeQuery(title_query);
		            	            
		   if(result_set.next() == true) {
		            	
		       System.out.println("The book already exists"); 
							            	
		   }else{
		       
		       System.out.println("Enter the publishing firm for the book..."); 
		       
		       String company_name = sc.nextLine();
		       
		       String company_query = "select publisher_id from publisher where company_name = \'" + company_name +"\'"; 
	        
	           result_set = statement.executeQuery(company_query);
	
	           if(result_set.next() == true) {
	               
	               publisher_id = result_set.getInt("publisher_id"); 
	               
	           }else{
	               
	               System.out.println("Please Add/Create Publisher first");
	           }
	           
	           System.out.println("Enter the first name of the book author: "); 
		       
		       String author_first_name = sc.nextLine();
		       
	           System.out.println("Enter the last name of the book author: "); 
	
		       String author_last_name = sc.nextLine();
		       
		       String author_query = "select author_id from author where first_name = \'" + author_first_name + "\' and last_name = \'" + author_last_name + "\'"; 
	          
	           result_set = statement.executeQuery(author_query);
	           
	           if(result_set.next() == true) {
	               
	               author_id = result_set.getInt("author_id"); 
	               
	           }
	           
	           String book_query = "insert into book (ISBN,title,genre,year,price,number_of_pages,percentage,number_of_books,publisher_id)"
		       + " values (?,?,?,?,?,?,?,?,?)";
	           
	           Object[] book = book_maker(publisher_id);
	           String ISBN  = (String) book[0];
	 
	           PreparedStatement prepared_statement = connection.prepareStatement(book_query);
	            
		       prepared_statement.setObject(1, book[0]);
		       prepared_statement.setString(2, book_title);
		       prepared_statement.setObject(3, book[2]);
		       prepared_statement.setObject(4, book[3]);
		       prepared_statement.setObject(5, book[4]);
		       prepared_statement.setObject(6, book[5]);
		       prepared_statement.setObject(7, book[6]);
		       prepared_statement.setObject(8, book[7]);
		       prepared_statement.setObject(9, book[8]);
		       prepared_statement.execute();
		       
		     
		       add_author(author_first_name,author_last_name,ISBN, author_id);
		      
		     System.out.println("Book was Added successfully");
	   
		   }
	        
	    }catch (Exception error) {
	
		            System.out.println("Exception : " + error.getMessage());
		}
	}



	    
	    private static void add_author(String author_first_name, String author_last_name, String ISBN, int auth_ID){
	    	
	    	int author_id =0; 
	    	
	    	PreparedStatement prepared_statement;
	    	
	    	String author_insert = "insert into author (first_name ,last_name)"
	    		       + " values (?,?)";
	    	 
	    	 String author_book_insert = "insert into author_book (ISBN  ,author_id)"
	    		       + " values (?,?)";
	    	           
	    	          
	    	 
	    	try {
	    		
	    		if(auth_ID == 0){
	    			
	    			prepared_statement = connection.prepareStatement(author_insert,Statement.RETURN_GENERATED_KEYS);
					  
	    			prepared_statement.setString(1, author_first_name);
		    	       
	    			prepared_statement.setString(2, author_last_name);
					
					prepared_statement.executeUpdate();
					
					ResultSet rs = prepared_statement.getGeneratedKeys();
		    	       if(rs.next()){
		    	    	   
		    	    	   author_id = rs.getInt(1);
		    	       }
	    			
	    		}else{
	    			
	    			author_id =  auth_ID;
	    		}
				 
	    	       
	    	       prepared_statement = connection.prepareStatement(author_book_insert);
	    	       
	    	       prepared_statement.setString(1, ISBN);
	    	       
	    	       prepared_statement.setInt(2, author_id);
	    	       
	    	       prepared_statement.executeUpdate();
	    	       
				
				
			} catch (SQLException error) {

	            System.out.println("Exception : " + error.getMessage());

			}
	    	            
	
        }

		public static int get_admin_choice(){
	        

	    	int choice = 0;
	    	
	    	do{
	    		
		        print_admin_menu();
		        
		    	System.out.print("Enter you choice: ");
		    	
		        choice = sc.nextInt();
		        
		        sc.nextLine();
		        
		        if(choice == 1){
	            		        	
		        	add_book(); 
		              
		        }else if(choice == 2){
		            
		            remove_book(); 
		            
		        }else if(choice == 3) {
		        	
		        	int pub_id = create_publisher();
		        	
		        	if(pub_id == -1){
		        		
		        		System.out.println("Could not add publisher");
		        		
		        	}else{
		        		
		        		System.out.println("Publisher with id: " + pub_id +" was added successfully");
		        	}
		        	
		        	
		        }else if(choice == 4) {
		        	
		        	remove_publisher(); 
		        	
		        }else if(choice == 5) {
		        	
		        	sales_expenditures_report(); 
		        	
		        }else if(choice == 6) {
		        	
		        	sales_by_genre(); 
		        	
		        }else if(choice == 7) {
		        	
		        	sales_by_author(); 
		        	
		        }else if (choice == 8){
		        	
		        	System.out.println("Exiting ... Adious Amigo!");
		        	
		        	break;
		        	
		        	
		        }else{
		        	
		        	System.out.println("Invalid choice");
		        }
		        
	    	}while(true);
	        
	        return choice;
	    }
		    
	public static void track_order() {
		    	
		    	try{
		    	    
	    		    Scanner scanner = new Scanner(System.in); 
	
	    		 	String order_id = ""; 
	    		 	
	    		 	System.out.print("Enter the order id: ");
	            
	    		 	order_id = scanner.nextLine();
			        
		            Statement statement = connection.createStatement();
		            
		            ResultSet result_set;
		            
		            String tracking_query = "select shipping_id,order_id,first_name,last_name,shipping_date,city,postal_code,country,current_location from stock_order natural join shipping_info where order_id = \'" + order_id + "\'";
		            	            
		            result_set = statement.executeQuery(tracking_query);
		            
		            System.out.println(); 
		            
		            System.out.println("Tracking Order....");
		            
		            System.out.println(); 
		            	            
		            if(result_set.next() == false) {
		            	
		            	System.out.println("The tracking information doesn't exist"); 
								            	
		            }else {
		            	
		                do {
		            		
		            		System.out.printf("%-12s %-12s %-12s %-12s %-12s %-12s %-12s %-12s\n",result_set.getString("shipping_id") , result_set.getString("order_id")
	                        ,result_set.getString("first_name") ,result_set.getString("last_name"),
	                        result_set.getString("shipping_date"),result_set.getString("city"),
	                        result_set.getString("postal_code"),result_set.getString("country")); 
	                        System.out.println(); 
	                        System.out.println("It is currently at this location: " + result_set.getString("current_location"));
		            		
		            	}while(result_set.next());
									            	
		            }
		
									
	    	 }catch (Exception error) {
	
		            System.out.println("Exception : " + error.getMessage());
		     }
		    	
	}

		public static int get_user_choice(){
		
			int choice = 0;
			
			do{
		        print_user_menu();
		        
		    	System.out.print("Enter you choice: ");
		    	
		        choice = sc.nextInt();
		        
		        sc.nextLine();
		        
		        if(choice == 1){
		    	
		          get_books();	
		          
			    }else if(choice == 2) {
			        	
			        	get_book_by_title(); 
			        	
			    }else if(choice == 3) {
			    	
			    	get_book_by_author(); 
			    	
			    }else if(choice == 4) {
			    	
			    	get_book_by_ISBN();
			    	
			    }else if(choice == 5) {
			    	
			    	get_book_by_genre(); 
			    	
			    }else if(choice == 6) {
			    	
			    	add_to_basket(); 
			    	
			    }else if(choice == 7) {
			    	
			    	checkout();
			    	
			    }else if(choice == 8) {
			    	
			    	track_order(); 
			    	
			    }else if (choice == 9){
			    	
			    	    log_out();
			    	    
			        	System.out.println("Logging out ... Adious Amigo!");
			        	
			        	break;
			    }else{
			        	System.out.println("Invalid choice");
			    }
			
			    
			}while(true);
		    
		    return choice;
		}
	    
	 
	    
	    public  static void log_out(){
	    	
	    	String basket_delete = "delete from user_account_book where  user_account_id = ?";

			PreparedStatement prepared_statement;
			
				try{
					
				prepared_statement = connection.prepareStatement(basket_delete);
				
				
	    	    prepared_statement.setString(1, user_account_id);
	    	   
				
				prepared_statement.executeUpdate();
				
				System.out.println("Exiting...."); 
				
                System.exit(0);
			
				}catch (SQLException error) {
					
					System.out.println(error.getMessage());
				}

         }

		public static void add_to_basket(){
			
			System.out.println(); 
			
			System.out.println("Adding to Basket");
			
			System.out.println("---------------------------------"); 
	    	

		 	String ISBN = "";
		 	
		 	int quantity = 0;
		 	
		 	int qn = 0;
		 	
		 	get_books();
		 	
		 	System.out.println();
		 	
		 	System.out.print("Enter the ISBN of the book you want to Add: ");
        
		 	ISBN = sc.nextLine();
		 	
		 	System.out.print("Enter the amount of books: ");
		 	quantity = sc.nextInt();
		 	sc.nextLine();
		 	
		 	
		 	 Statement statement;
	            
	         ResultSet result_set;
	            
	         String ISBN_query = "select * from book where ISBN = \'" + ISBN + "\'";
	            
	         
	            try{
	            	
	            	statement = connection.createStatement();
	           
	            	result_set = statement
						.executeQuery(ISBN_query);
	            	            
	            if(result_set.next() == false) {
	            	
	            	System.out.println("The book doesn't exist"); 
						            	
	            }else{
	            	      qn = result_set.getInt("number_of_books");
	           
	            		 if(qn < quantity){
	            			 
	            			 System.out.println("Quantity is no available please enter a number less than " + qn);
	            		 }
	            }
		 	
		 	 String basker_insert = "insert into user_account_book (ISBN ,user_account_id,quantity)"
	                	+ " values (?,?,?)";
		 	 
		 	 String book_update = "update book set number_of_books = number_of_books - ? where isbn =\'" + ISBN + "\'";
	                	    
	        
			PreparedStatement prepared_statement = connection.prepareStatement(basker_insert);
				
			prepared_statement.setString(1, ISBN);
	    	
			prepared_statement.setString(2, user_account_id);
	    	    
			prepared_statement.setInt(3, quantity);
				
				
			prepared_statement.executeUpdate();
				
	
			prepared_statement = connection.prepareStatement(book_update);
				  
			prepared_statement.setInt(1, quantity);
				  
			prepared_statement.executeUpdate();
				  
			System.out.println("Book was addedd successfully to Basket");
			
			}catch (SQLException error) {
				
				System.out.println(error.getMessage());
				
			}
	                 
	
		}

		public static void get_book_by_ISBN() {
	    	
	    	try{
	    		 
    		    Scanner scanner = new Scanner(System.in); 

    		 	String ISBN = ""; 
    		 	
    		 	System.out.print("Enter the ISBN of the book: ");
            
    		 	ISBN = scanner.nextLine();
		        
	            Statement statement = connection.createStatement();
	            
	            ResultSet result_set;
	            
	            String ISBN_query = "select * from book where ISBN = \'" + ISBN + "\'";
	            	            
	            result_set = statement
						.executeQuery(ISBN_query);
	            
	            System.out.println(); 
	            
	            System.out.println("The Book You Are Searching For"); 
	            
	            System.out.println("----------------------------------------");
	            	            
	            if(result_set.next() == false) {
	            	
	            	System.out.println("The book doesn't exist"); 
						            	
	            }else {
	            		            		            		
	            		System.out.printf("%-40s   %-12s  %4d  %.2f  %3d %3d\n",result_set.getString("title") , result_set.getString("genre")
								,result_set.getInt("year") ,result_set.getFloat("price"),
								result_set.getInt("number_of_pages") , result_set.getInt("number_of_books")); 
	            								
	            	
	            }
	
							
    	 } 
    	 catch (Exception error) {

	            System.out.println("Exception : " + error.getMessage());
	        }
	    }
	    
	    public static int input_shipping_info(){
	      
	            int shipping_id = 0;
	            
	            String first_name = ""; 
	            
	            String last_name = ""; 
	            
	            String shipping_date = ""; 
	            
	            String street_address = ""; 
	            
	            String city = ""; 
	            
	            String postal_code = "";
	            
	            String country = ""; 
	            
	            
	            try{
	                   
	            	Statement statement = connection.createStatement();
	            
	                System.out.println("Enter first name of person you want to ship product to: ");
	                
	                first_name = sc.nextLine();

	                System.out.println("Enter last name of person you want to ship product to: ");

	                last_name = sc.nextLine(); 

	                System.out.println("Enter date you want the shipment to be recieved: ");
	        
	                shipping_date = sc.nextLine(); 
	                
	                System.out.println("Enter address: ");
	                
	                street_address = sc.nextLine(); 
	                
	                System.out.println("Enter the city: ");
	                
	                city = sc.nextLine(); 
	                
	                System.out.println("Enter the postal code: ");
	                
	                postal_code = sc.nextLine(); 
	                
	                System.out.println("Enter the country you want it set to: ");
	                
	                country = sc.nextLine();
	                
	                 ResultSet result_set;
	                       
	                 String shipping_query = "insert into shipping_info (first_name,last_name,shipping_date,street_address,city,postal_code,country)"
	                	+ " values (?,?,?,?,?,?,?)";
	                 	                 
	                 PreparedStatement prepared_statement = connection.prepareStatement(shipping_query,Statement.RETURN_GENERATED_KEYS);
	                 
	             
	                 
	                 prepared_statement.setString(1, first_name);
	                 
	                 prepared_statement.setString(2, last_name);
	                 
	                 prepared_statement.setString(3, shipping_date);
	                 
	                 prepared_statement.setString(4, street_address);

	                 prepared_statement.setString(5, city);

	                 prepared_statement.setString(6, postal_code);

	                 prepared_statement.setString(7, country);
	                 
	                 prepared_statement.executeUpdate();
	           
	                 
	                 ResultSet rs = prepared_statement.getGeneratedKeys();
	                 
	         	       if(rs.next()){
	         	    	   
	         	    	   
	         	    	   shipping_id = rs.getInt(1);
	         	       }           
	                       

	        }catch (Exception error) {

	               System.out.println("Exception : " + error.getMessage());
	        }
	            
	            return shipping_id;
	       
	    }
	    

	    public static int input_billing_info(){
	       
	    	  
	    	            int billing_id = 0;
	    	            
	    	            String first_name = ""; 
	    	            
	    	            String last_name = ""; 
	    	            
	    	            String street_address = ""; 
	    	            
	    	            String city = ""; 
	    	            
	    	            String postal_code = "";
	    	            
	    	            String country = ""; 
	    	            
	    	            String card_type = ""; 
	    	            
	    	            String card_number = ""; 
	    	            
	    	            String card_expiry_date = ""; 
	    	            
	    	            String three_digit_code = ""; 

	    	            try{
	    	   
	    	            	Statement statement = connection.createStatement();
	    	            

	    	               System.out.println("Billing Information");
	    	                
	    	                System.out.println("Enter first name of person: ");
	    	                
	    	                first_name = sc.nextLine();

	    	                System.out.println("Enter last name of person: ");

	    	                last_name = sc.nextLine(); 

	    	                System.out.println("Enter address: ");
	    	                
	    	                street_address = sc.nextLine(); 
	    	                
	    	                System.out.println("Enter the city: ");
	    	                
	    	                city = sc.nextLine(); 
	    	                
	    	                System.out.println("Enter the postal code: ");
	    	                
	    	                postal_code = sc.nextLine(); 
	    	                
	    	                System.out.println("Enter the country you want it set to: ");
	    	                
	    	                country = sc.nextLine();
	    	                
	    	                System.out.println("Enter the card type: ");
	    	           
	        	           card_type = sc.nextLine();
	        	           
	        	           System.out.println("Enter the card number: ");
	        	           
	        	            card_number = sc.nextLine(); 
	        	            
	        	            System.out.println("Enter the card expiry date: ");
	        	            
	        	            card_expiry_date = sc.nextLine(); 
	        	            
	        	            System.out.println("Enter the three digit code: ");
	        	            
	        	            three_digit_code = sc.nextLine(); 
	    	                
	    	                 ResultSet result_set;
	    	                       
	    	                 String billing_query = "insert into billing_info (first_name,last_name,city,postal_code,country,street_address,card_type,card_number,card_expiry_date,three_digit_code) "
	                         + " values (?,?,?,?,?,?,?,?,?,?)";
	    	                 	    	                 
	    	                 PreparedStatement prepared_statement = connection.prepareStatement(billing_query,Statement.RETURN_GENERATED_KEYS);
	    	                 
	    	                 prepared_statement.setString(1, first_name);
	    	                 
	    	                 prepared_statement.setString(2, last_name);
	    	                 
	    	                 prepared_statement.setString(3, city);
	    	                 
	    	                 prepared_statement.setString(4, postal_code);

	    	                 prepared_statement.setString(5, country);
	    	                 
	    	                 prepared_statement.setString(6,street_address); 
	    	                 
	    	                 prepared_statement.setString(7,card_type);
	    	                 
	    	                 prepared_statement.setString(8,card_number); 
	    	                 
	    	                 prepared_statement.setString(9,card_expiry_date); 
	    	                 
	    	                 prepared_statement.setString(10,three_digit_code);

	    	                 prepared_statement.executeUpdate() ;
	    	                 
	    	                 ResultSet rs = prepared_statement.getGeneratedKeys();
	    	                 
	  	         	       if(rs.next()){
	  	         	    	   
	  	         	    	   billing_id = rs.getInt(1);
	  	         	       }           

	    	        }catch (Exception error){

	    	               System.out.println("Exception : " + error.getMessage());
	    	        }
	        
	        return billing_id;

	    }
	    

	    
	    public static void checkout() {
	    	
	    	System.out.println();
	    	
	    	System.out.println("Checkout"); 
	    	
	    	System.out.println("--------------------------------------"); 
	    	
	    	int billing_id =  input_billing_info(); 
	    	
	    	int shipping_id = input_shipping_info();
	    	
	    	int order_id =  0;  
	    	
	    	String ISBN ="";
	    	int qunatity = 0;
	    	
	    	List<basket_item>  basket_list =  new ArrayList<basket_item>();

            String order_query = "insert into stock_order (order_date,shipping_id,billing_id,user_account_id)"
           		 + "values(CURRENT_DATE,?,?,?)"; 	    	                 
   
            PreparedStatement prepared_statement;
			
            try{
            	
				prepared_statement = connection.prepareStatement(order_query,Statement.RETURN_GENERATED_KEYS);
			
                        
	            prepared_statement.setInt(1, shipping_id);
	            
	            prepared_statement.setInt(2, billing_id);
	            
	            prepared_statement.setString(3, user_account_id);
	            
	            prepared_statement.executeUpdate();
            
	            ResultSet rs = prepared_statement.getGeneratedKeys();
    	       
	            if(rs.next()){

    	    	   order_id = rs.getInt(1);
    	       }
    	       
   
  		       Statement statement = connection.createStatement();
  		            
  		       ResultSet result_set;
  		            
  		           
  		       result_set = statement
  						        .executeQuery("select isbn, quantity "
  						        		+ " from user_account_book");
  					
  						
  				while(result_set.next()){
  					
  							ISBN = result_set.getString("isbn");
  							
  							qunatity = result_set.getInt("quantity");
  							
  							basket_list.add(new basket_item(ISBN, qunatity));
  							
  				}
  						
  				String orderItem_query = "insert into order_items (isbn,order_id,quantity) "
  				           		 + " values(?,?,?)"; 
  						 
  					
  						
  				for(int i= 0; i<basket_list.size(); i++){
  							
  							prepared_statement = connection.prepareStatement(orderItem_query);

  				            prepared_statement.setString(1, basket_list.get(i).isbn);
  				            
  				            prepared_statement.setInt(2, order_id);
  				            
  				            prepared_statement.setInt(3,basket_list.get(i).quantity);
  				            
  				            prepared_statement.executeUpdate();
  				}
  						
  	      System.out.println("Order was created successfully, your order_id is: " +  order_id);
    	   
    	       
			}catch (SQLException error){
				
				System.out.println(error.getMessage());
			}
    	          	
	    	
	    }
  
	    public static void get_book_by_genre(){
	    	
	        try{
	       
	            String genre = "";
	               
	            System.out.print("Enter the genre of the books: ");
	               
	            genre = sc.nextLine();
	                   
	            Statement statement = connection.createStatement();
	                       
	            ResultSet result_set;
	                       
	             result_set = statement.executeQuery("select * from book where genre = \'" + genre + "\'"); 					
	            
	             System.out.println(); 
					
	             System.out.println("List of " + genre + " Books");
					
				System.out.println("-----------------------------------------");
	             
	             if(result_set.next() == false) {
	                       
	               System.out.println("The books don't exist with that genre");
	            
	                        
	             }do{
	                       
		            System.out.printf("%-40s   %-12s  %4d  %.2f  %3d %3d\n",result_set.getString("title") , result_set.getString("genre")
		            ,result_set.getInt("year") ,result_set.getFloat("price"),
		            result_set.getInt("number_of_pages") , result_set.getInt("number_of_books"));
	                       
	            }while(result_set.next());

	        }catch (Exception error) {

	               System.out.println("Exception : " + error.getMessage());
	        }
	       
	       
	    }
	    
	    public static String login(){
	    	
	    	String user_id  ="";
	    	
	    	String password = "";
	    	
	    	boolean is_valid = false;
	    	
	    	do{
	        
		    	System.out.print("Enter a user_account_id: ");
		        user_id = sc.nextLine();
		        System.out.print("Enter password for your account: ");
		        password = sc.nextLine();
		        
		        is_valid = validate_user(user_id,password);
		        
		        if(!is_valid){
		        	
		        	 System.out.println("Invalid login try again!");
		        }
		       
	        
	    	}while(!is_valid);
	        
	       
	        
	        return user_id; 
	    }
	    
	    
	    public static void  get_books(){
	    	
	    	 try{
	  
	                
		            Statement statement = connection.createStatement();
		            
		            ResultSet result_set;
		           
					result_set = statement
						        .executeQuery("select isbn, title,genre,year,price,number_of_pages,number_of_books "
						        		+ " from book");
					
					System.out.println(); 
					
					System.out.println("List of Books");
					
					System.out.println("-----------------------------------------");
					
						while(result_set.next()){
							
							System.out.printf("%-10s  %-40s   %-12s  %4d  %.2f  %3d %3d\n",result_set.getString("isbn"),result_set.getString("title") , result_set.getString("genre")
									,result_set.getInt("year") ,result_set.getFloat("price"),
									result_set.getInt("number_of_pages") , result_set.getInt("number_of_books"));
						}
						
	    	 }catch (Exception error) {

		            System.out.println("Exception : " + error.getMessage());
		     }
	    	 	    	
		    	
	    }
	    
	    public static void get_book_by_author() {
	    	
	    	try{
	    		 
    		 	String author_first_name = ""; 
    		 	
    		 	String author_last_name = ""; 
            
    		 	System.out.print("Enter the first name of the author: ");
            
    		 	author_first_name = sc.nextLine();
    		 	
    		 	System.out.print("Enter the last name of the author: ");
                
    		 	author_last_name = sc.nextLine();
		        
	            Statement statement = connection.createStatement();
	            
	            ResultSet result_set;
	            
	            
	            String query = "select * from book where ISBN in (Select ISBN from author_book "
						+ "where author_id in (select author_id from author where first_name = \'" + 
						author_first_name + "\' and last_name = \'" + author_last_name +"\'))"; 
	            
	            
	            result_set = statement
						.executeQuery(query);
	            
	            System.out.println(); 
				
				System.out.println("List of Books Written By " + author_first_name + " " + author_last_name);
				
				System.out.println("-----------------------------------------");
	           
	            	            
	            if(result_set.next() == false) {
	            	
	            	System.out.println("The author doesn't exist"); 
						            	
	            }else{
	            		            	
	            	do{
	            		
	            		System.out.printf("%-40s   %-12s  %4d  %.2f  %3d %3d\n",result_set.getString("title") , result_set.getString("genre")
								,result_set.getInt("year") ,result_set.getFloat("price"),
								result_set.getInt("number_of_pages") , result_set.getInt("number_of_books")); 
	            		
	            	}while(result_set.next());
						
						      		            	
	            }
	
				
    	 }catch (Exception error) {

	            System.out.println("Exception : " + error.getMessage());
	     }
	    	
    	
	    }
	    
	    public static void get_book_by_title(){
	    	
	    	 try{
	    		 

	    		 	String title = ""; 
	            
	    		 	System.out.print("Enter the book title: ");
	            
	    		 	title = sc.nextLine();
			        
		            Statement statement = connection.createStatement();
		            
		            ResultSet result_set;
		            
					result_set = statement
						        .executeQuery("select * from book where title = \'" + title + "\'");
					
					System.out.println(); 
					
		            System.out.println("The Book You Are Searching For"); 
		            
					System.out.println("-----------------------------------------");

						
						while(result_set.next()){
							
							String found_title = result_set.getString("title"); 
							
							System.out.println("Found Title: " + found_title); 
							
							if(found_title == "") {
								
								System.out.println("The book doesn't exist"); 
								
								break; 
								
								
							}
 
							System.out.printf("%-40s   %-12s  %4d  %.2f  %3d %3d\n",result_set.getString("title") , result_set.getString("genre")
									,result_set.getInt("year") ,result_set.getFloat("price"),
									result_set.getInt("number_of_pages") , result_set.getInt("number_of_books"));
						}
										
						
	    	 } 
	    	 catch (Exception error) {

		            System.out.println("Exception : " + error.getMessage());
		        }
	    	
		    	
	 }

	    
	    public static boolean validate_user(String user_id, String password){
	    	 
	    	try{
	   
	                
		            Statement statement = connection.createStatement();
		            
		            ResultSet result_set;
					
						result_set = statement
						        .executeQuery("select * from user_account  where user_account_id = \'"  + user_id + 
						        		"\' and password = \'" + password + "\'");
					
						
						if(result_set.next()){
							
							return true;
						}
						
	    	 }catch (Exception error) {

		            System.out.println("Exception : " + error.getMessage());
		     }

	    	return false;
	        	 
	    }

	  
	}

 class basket_item{
	 
	  public	String isbn;
	  
	  public 	int quantity;
	
	  public basket_item(String isbn, int quantity) {
	
		this.isbn = isbn;
		
		this.quantity = quantity;
		
	}
  
}

