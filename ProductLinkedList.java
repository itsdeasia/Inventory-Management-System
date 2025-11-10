import java.util.ArrayList;
import java.util.List;

public class ProductLinkedList {
    private ProductNode head;

    public void insert(Product item) {
        ProductNode newNode = new ProductNode(item);
        newNode.next = head;
        head = newNode;
    }

    public void delete(String itemName) {
        ProductNode current = head;
        ProductNode prev = null;

        while (current != null && !current.item.itemName.equals(itemName)) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            if (prev == null) {
                head = current.next;
            } else {
                prev.next = current.next;
            }
        }
    }

    public ProductNode getHead() {
        return head;
    }

 public List<Product> getAllItems() {
        List<Product> items = new ArrayList<>();
        ProductNode node = head;
        while (node != null) {
            items.add(node.item);
            node = node.next;
        }
        return items;
    }
    
}