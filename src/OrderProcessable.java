
public interface OrderProcessable {
	Order placeOrder(int customerId, int productId, int quantity) throws OutOfStockException, InvalidOrderException;
    void updateStockLevel(int productId, int quantity);
    void generateOrderReceipt(int orderId);
}
