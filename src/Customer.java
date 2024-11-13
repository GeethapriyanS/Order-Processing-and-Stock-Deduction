import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {
	private int customerId;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String Password;
	public Customer() {};
	public Customer(int customerId, String username, String email, String phoneNumber, String address,String Password) {
		this.customerId = customerId;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.Password=Password;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public static Customer checkUserlogin(String username,String password) {
		   String query="select * from customer where username = ? and password = ?";
		   new DatabaseConnection();
		try(Connection con = new DatabaseConnection().getConnection();
					  PreparedStatement pst=con.prepareStatement(query)){
				  pst.setString(1, username);
				  pst.setString(2, password );
				  ResultSet res=pst.executeQuery();
				  if(res.next()) {
					 return new Customer(res.getInt("customer_id"),res.getString("username"),res.getString("email"),res.getString("phone_number"),res.getString("address"),res.getString("password"));
				  }else {
					  return new Customer();
				  }
			  }
			  catch(Exception e){
				  e.printStackTrace();
			  }
			  return new Customer();
	   }
	public Customer Add_customer(String newusername, String email2, String phoneno, String address2,String newpassword) {
		try(Connection connection =new DatabaseConnection().getConnection();
			PreparedStatement orderStmt = connection.prepareStatement(
            "INSERT INTO customer (username,email,phone_number,address,password) VALUES (?, ?, ?,?,?)",Statement.RETURN_GENERATED_KEYS)){
        orderStmt.setString(1,newusername);
        orderStmt.setString(2, email2);
        orderStmt.setString(3, phoneno);
        orderStmt.setString(4, address2);
        orderStmt.setString(5, newpassword);
        orderStmt.executeUpdate();
        ResultSet orderRs = orderStmt.getGeneratedKeys();
        if (orderRs.next()) {
           int customerid = orderRs.getInt(1);
           Customer cus=new Customer(customerid,newusername,email2,phoneno,address,newpassword);
           return cus;
        }
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
		return null;
	}
}
