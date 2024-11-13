import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Admin {
	private int admin_id;
	private String username;
    private String password;
    private String email;
	
    public Admin() {};
    
    public Admin(int admin_id, String username, String password, String email) {
		this.admin_id = admin_id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
    
    public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static Admin checkLogin(String userName,String passWord) {
		  String query="select * from admin where username= ? and password = ?";
		  new DatabaseConnection();
		  try(Connection con =new DatabaseConnection().getConnection();
				  PreparedStatement pst=con.prepareStatement(query)){
			  pst.setString(1, userName);
			  pst.setString(2, passWord );
			  ResultSet res=pst.executeQuery();
			  if(res.next()) {
				  return new Admin(res.getInt("admin_id"),res.getString("username"),res.getString("password"),res.getString("email"));
			  }else {
				  return new Admin();
			  }
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  return new Admin();
	  }
	
	public boolean addProduct(String productName,String category,float price,int stock){
		if(category.equalsIgnoreCase("electronics")) { 
			return new ElectronicsProduct().addElectronic(productName,category,price,stock); 
		}else {
			return new ClothingProduct().addClothing(productName,category,price,stock);
		}
	}

	public boolean updateInventory(int updateProductId, int newStock) {
		try(Connection connection =new DatabaseConnection().getConnection();
		PreparedStatement pst = connection.prepareStatement(
		"update inventory set stock_quantity=? where product_id=?")){
    pst.setInt(1,newStock);
    pst.setInt(2,updateProductId);
      if(pst.executeUpdate()==1) {
    	  return true;
      }
	}catch(Exception e) {
		e.printStackTrace();
	}
		return false;
   }
}
