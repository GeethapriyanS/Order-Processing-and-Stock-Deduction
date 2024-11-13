import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Inventory {
	private int productId;
    private int stockQuantity;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public static int getquantity(int productid) {
		String query="select stock_quantity from stock_levels where product_id=?";
		new DatabaseConnection();
		try(Connection con=new DatabaseConnection().getConnection();
			PreparedStatement pst=con.prepareStatement(query)){
			pst.setInt(1,productid);
			ResultSet rs=pst.executeQuery();
			if(rs.next()) {
               return rs.getInt("stock_quantity");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
}
