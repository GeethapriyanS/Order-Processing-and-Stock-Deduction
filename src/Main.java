import java.util.*;

public class Main {
public static void main(String[] args) throws OutOfStockException, InvalidOrderException {
       Scanner sc = new Scanner(System.in);
       Admin adminAccount = null;
       Customer customerAccount = null;
       OrderProcessing orders=null;
       Order orderSuccess=null;
       Boolean loginstatus=true;
       System.out.println("Welcome to Order Processing and Stock deduction System !!\n");
       while (loginstatus) {
           System.out.println("1. Login\n" +
                   "2. CreateAccount\n"+
                   "3. View Products\n" +
                   "4. Place Order\n" +
                   "5. Check Order History\n" +
                   "6. Manage Inventory (Admin)\n" +
                   "7. Logout\n"+
                   "8. Exit");
           int choice = sc.nextInt();

           if (customerAccount == null && adminAccount == null && choice > 2 && choice < 6) {
               System.out.println("Please log in first.");
               continue;
           }

           switch (choice) {
               case 1:
            	  if(customerAccount==null && adminAccount==null) { 
                   System.out.println("Login as (Admin/User):");
                   String userType = sc.next();

                   if (userType.equalsIgnoreCase("User")) {
                       System.out.println("Enter Username:");
                       String username = sc.next();
                       System.out.println("Enter Password:");
                       String password = sc.next();

                       customerAccount = Customer.checkUserlogin(username, password);
                       if (customerAccount != null) {
                           System.out.println("User logged in successfully.\n"+"Hii "+customerAccount.getUsername());
                       } else {
                           System.out.println("Invalid credentials.");
                       }
                   } else if (userType.equalsIgnoreCase("Admin")) {
                       System.out.println("Enter Admin Name:");
                       String adminName = sc.next();
                       System.out.println("Enter Password:");
                       String adminPassword = sc.next();

                       adminAccount = Admin.checkLogin(adminName, adminPassword);
                       if (adminAccount != null) {
                           System.out.println("Admin logged in successfully.");
                       } else {
                           System.out.println("Invalid credentials.");
                       }
                     }
            	  }else {
            		  System.out.println("Please logout from existing account and then login");
            	  }
                   break;
               
               case 2:
            	   if(customerAccount == null && adminAccount == null) {
            	   System.out.println("Enter Username:");
                   String newusername = sc.next();
                   System.out.println("Enter Password:");
                   String newpassword = sc.next();
                   System.out.println("Enter Email:");
                   String email=sc.next();
                   System.out.println("Enter PhoneNumber:");
                   String phoneno=sc.next();
                   System.out.println("Enter Address:");
                   String address=sc.next();
                   customerAccount=new Customer().Add_customer(newusername,email,phoneno,address,newpassword);
                   if (customerAccount != null) {
                       System.out.println("User Account Created Successfully.");
                       System.out.println("User logged in successfully.\n"+"Hii "+customerAccount.getUsername());
                   } else {
                       System.out.println("Invalid credentials.");
                   }
            	   }else {
            		   System.out.println("Logout from existing account and then create new account !\n");
            	   }
                   break;    
               case 3:
                   Product.displayProducts();
                   break;

               case 4:
                   if (customerAccount != null && adminAccount==null) {
                       System.out.println("Enter Product ID to order:");
                       int productId = sc.nextInt();
                       if(productId>10) {
                    	   System.out.println("Invalid ProductId !!");
                    	   break;
                       }
                       System.out.println("Enter Quantity:");
                       int quantity = sc.nextInt();
                       int stockquantity=Inventory.getquantity(productId);
                       if(quantity>stockquantity) {
                    	   System.out.println("!! Out of Stock !! "+"Only "+stockquantity+" Stock Available");
                    	   break;
                       }
                       orders = new OrderProcessing();
                       orderSuccess = orders.placeOrder(customerAccount.getCustomerId(),productId,quantity);
                       if (orderSuccess!=null) {
                           System.out.println("Order placed successfully with Order ID: " + orderSuccess.getOrderId());
                           //order_details.add(orderSuccess);
                       } else {
                           System.out.println("Order failed. Check stock or product availability.");
                       }
                   } else {
                       System.out.println("Please log in as a customer to place orders.");
                   }
                   break;

               case 5:
            	   if(adminAccount==null) {
                   Order.orderhistory(customerAccount.getCustomerId());}
            	   else{
            		  System.out.println("Enter Customer ID to see order history :");
            		  int id1=sc.nextInt();
            		  Order.orderhistory(id1);  
            	   }
                   break;

               case 6:
                   if (adminAccount != null) {
                       System.out.println("Admin Options:\n1. Add Product\n2. Update Inventory\n3. Back\n4.Logout");
                       int adminChoice = sc.nextInt();

                       switch (adminChoice) {
                           case 1:
                               System.out.println("Enter Product Name:");
                               String productName = sc.next();
                               System.out.println("Enter Category:");
                               String category = sc.next();
                               System.out.println("Enter Price:");
                               float price = sc.nextFloat();
                               System.out.println("Enter Initial Stock:");
                               int stock = sc.nextInt();

                               boolean addSuccess = adminAccount.addProduct(productName, category, price, stock);
                               if (addSuccess) {
                                   System.out.println("Product added successfully.");
                               } else {
                                   System.out.println("Failed to add product.");
                               }
                               break;

                           case 2:
                               System.out.println("Enter Product ID to update stock:");
                               int updateProductId = sc.nextInt();
                               System.out.println("Enter new stock quantity:");
                               int newStock = sc.nextInt();

                               boolean updateSuccess = adminAccount.updateInventory(updateProductId, newStock);
                               if (updateSuccess) {
                                   System.out.println("Stock updated successfully.");
                               } else {
                                   System.out.println("Failed to update stock.");
                               }
                               break;

                           case 3:
                               break;
                           case 4:
                        	   adminAccount = null;
                        	   break;
                       }
                   } else {
                       System.out.println("Please log in as an admin to manage inventory.");
                   }
                   break;

               case 7:
                   System.out.println("Logging out...");
                  // order_details=null;
                   customerAccount = null;
                   adminAccount = null;
                   break;
               case 8:
            	   loginstatus=false;
            	   System.out.println(" ** Thank you ** ");
            	   break;
               default:
                   System.out.println("Invalid choice. Please try again.");
           }
       }
    }
}
