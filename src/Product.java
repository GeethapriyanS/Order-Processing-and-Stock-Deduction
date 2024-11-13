import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Product {
	protected int productId;
    protected String productName;
    protected String category;
    protected float price;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public static void displayProducts() {
		String query="select p.product_id,p.product_name,p.price,i.stock_quantity from product as p inner join inventory as i on p.product_id=i.product_id;";
		new DatabaseConnection();
		try(Connection con=new DatabaseConnection().getConnection();
			PreparedStatement pst=con.prepareStatement(query)){
			ResultSet rs=pst.executeQuery();
			System.out.println("**** Product details ****");
			System.out.printf("%-15s %-15s %-15s %-15s\n","Product_id","Product_name","Price","Quantity");
			while(rs.next()) {
				System.out.printf("%-15d %-15s %-15.2f %-15d\n",rs.getInt("product_id"),rs.getString("product_name"),rs.getFloat("price"),rs.getInt("stock_quantity"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
