import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    Connection con=null;
    String url="jdbc:mysql://localhost:3306/order_management_system";
    String username="root";
    String password="W7301@jqir#";
    
  public Connection getConnection() {
    if(con==null) {
    	try{
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	con= DriverManager.getConnection(url,username,password);
    	System.out.println("Connection Established Sucessfully\n");
    	}catch(Exception e){
    		e.printStackTrace();//System.out.println("Exception Occured"+e);
    	}
      }
    return con;
    }
    
  public static void main(String args[]) {
	  DatabaseConnection connect = new DatabaseConnection();
	  Connection con=connect.getConnection();
  }
}
