import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Order {
	private int orderId;
    private int customerId;
    private int productId;
    private int quantity;
    private float totalAmount;
    private String status;
    
    public Order() {};
    
	public Order(int orderId, int customerId, int productId, int quantity, float totalAmount, String status) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.productId = productId;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.status = status;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public static void orderhistory(int id) {
		String query="select * from orders where customer_id=?";
		new DatabaseConnection();
		try(Connection con=new DatabaseConnection().getConnection();
			PreparedStatement pst=con.prepareStatement(query)){
			pst.setInt(1,id);
			ResultSet rs=pst.executeQuery();
			System.out.println("**** Order details ****");
			System.out.printf("%-15s %-15s %-15s %-15s %-15s %-19s %-15s\n","Order_id","Customer_id","Product_id","Quantity","Orderdate","Total_Amount","Status");
			while(rs.next()) {
				System.out.printf("%-15d %-15d %-15d %-15d %-15s %-19.2f %-15s\n",rs.getInt("order_id"),rs.getInt("customer_id"),rs.getInt("product_id"),rs.getInt("quantity"),rs.getString("order_date").toString(),rs.getFloat("total_amount"),rs.getString("status"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}
