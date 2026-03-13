import java.util.concurrent.*;

public class FlashSaleManager {
  private final ConcurrentHashMap<String, Integer> stock = new ConcurrentHashMap<>();
  private final ConcurrentLinkedQueue<Integer> waitingList = new ConcurrentLinkedQueue<>();

  public void addProduct(String id, int count) { stock.put(id, count); }

  public String purchase(String productId, int userId) {
    // Atomic update to prevent overselling in multi-threaded environment
    int currentStock = stock.getOrDefault(productId, 0);

    if (currentStock > 0) {
      // compute() ensures only one thread updates this key at a time
      stock.compute(productId, (id, val) -> val > 0 ? val - 1 : 0);
      return "User " + userId + " successfully purchased " + productId;
    } else {
      waitingList.add(userId);
      return "Product out of stock. User " + userId + " added to waiting list.";
    }
  }

  public static void main(String[] args) {
    FlashSaleManager sale = new FlashSaleManager();
    sale.addProduct("IPHONE15", 2);

    System.out.println(sale.purchase("IPHONE15", 101));
    System.out.println(sale.purchase("IPHONE15", 102));
    System.out.println(sale.purchase("IPHONE15", 103)); // Will go to waiting list
  }
}