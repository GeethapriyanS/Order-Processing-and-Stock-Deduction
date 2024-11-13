import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ElectronicsProduct extends Product{
	public ElectronicsProduct() {
        this.category = "clothing";
    }

	public boolean addElectronic(String productName, String category, float price, int stock) {
		int productid=0;
		try(Connection connection =new DatabaseConnection().getConnection();
			PreparedStatement orderStmt = connection.prepareStatement(
            "INSERT INTO product (product_name,category,price) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			PreparedStatement pst = connection.prepareStatement(
			"INSERT INTO inventory (product_id,stock_quantity) VALUES (?, ?)")	){
        orderStmt.setString(1, productName);
        orderStmt.setString(2, category);
        orderStmt.setFloat(3, price);
        orderStmt.executeUpdate();
        ResultSet orderRs = orderStmt.getGeneratedKeys();
        if (orderRs.next()) {
           productid = orderRs.getInt(1);
        }
		pst.setInt(1, productid);
        pst.setInt(2, stock);
        if(pst.executeUpdate()==1) {
        	return true;
        }  
	}catch(SQLException e) {
		e.printStackTrace();
	}
		return false;
	}
}
