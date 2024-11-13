// OrderProcessing.java
import java.sql.*;
// import java.util.ArrayList;
// import java.util.HashMap;

public class OrderProcessing implements OrderProcessable {
   // private ArrayList<Order> orderList = new ArrayList<>();
   // private HashMap<Integer, Integer> inventoryMap = new HashMap<>();

    @Override
    public Order placeOrder(int customerId, int productId, int quantity) throws OutOfStockException, InvalidOrderException {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            // Fetch product details and inventory
        	int orderId=0;
            PreparedStatement productStmt = connection.prepareStatement("SELECT price FROM product WHERE product_id = ?");
            productStmt.setInt(1, productId);
            ResultSet productRs = productStmt.executeQuery();

            if (!productRs.next()) {
                throw new InvalidOrderException("Product not found.");
            }
            float price = productRs.getFloat("price");

            PreparedStatement inventoryStmt = connection.prepareStatement("SELECT stock_quantity FROM inventory WHERE product_id = ?");
            inventoryStmt.setInt(1, productId);
            ResultSet inventoryRs = inventoryStmt.executeQuery();

            if (!inventoryRs.next() || inventoryRs.getInt("stock_quantity") < quantity) {
                throw new OutOfStockException("Insufficient stock.");
            }

            // Deduct stock and insert order
            float totalAmount = price * quantity;
            PreparedStatement orderStmt = connection.prepareStatement(
                "INSERT INTO orders (customer_id, product_id, quantity, total_amount, status) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            orderStmt.setInt(1, customerId);
            orderStmt.setInt(2, productId);
            orderStmt.setInt(3, quantity);
            orderStmt.setFloat(4, totalAmount);
            orderStmt.setString(5, "pending");
            orderStmt.executeUpdate();
            
            ResultSet orderRs = orderStmt.getGeneratedKeys();
            if (orderRs.next()) {
               orderId = orderRs.getInt(1);
            }
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM orders where order_id=?");
            pst.setInt(1,orderId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
            	return new Order(rs.getInt("order_id"), rs.getInt("customer_id"), rs.getInt("product_id"),  rs.getInt("quantity"),  rs.getFloat("total_amount"), rs.getString("status"));
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Order();
    }

    @Override
    public void updateStockLevel(int productId, int quantity) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            PreparedStatement updateStmt = connection.prepareStatement("UPDATE inventory SET stock_quantity = stock_quantity + ? WHERE product_id = ?");
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, productId);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateOrderReceipt(int orderId) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            PreparedStatement orderStmt = connection.prepareStatement("SELECT * FROM orders WHERE order_id = ?");
            orderStmt.setInt(1, orderId);
            ResultSet rs = orderStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Order ID: " + rs.getInt("order_id"));
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Total Amount: " + rs.getFloat("total_amount"));
                System.out.println("Status: " + rs.getString("status"));
            } else {
                System.out.println("Order not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
