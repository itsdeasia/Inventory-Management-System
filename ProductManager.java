import java.util.*;

public class ProductManager {
    private Map<String, Product> itemMap;
    private Map<ProductCategory, ProductLinkedList> itemCategories;

    public ProductManager() {
        itemMap = new HashMap<>();
        itemCategories = new HashMap<>();
        for (ProductCategory category : ProductCategory.values()) {
            itemCategories.put(category, new ProductLinkedList());
        }
    }

    public void insertItem(Product item) {
        itemMap.put(item.itemName, item);
        itemCategories.get(item.category).insert(item);
    }

    public void deleteItem(String itemName) {
        Product item = itemMap.get(itemName);
        if (item != null) {
            itemCategories.get(item.category).delete(itemName);
            itemMap.remove(itemName);
        }
    }

    public List<Product> getItemsByCategory(ProductCategory category) {
        return itemCategories.get(category).getAllItems();
    }

    public void sortItemsByCost(ProductCategory category, boolean ascending) {
        List<Product> items = getItemsByCategory(category);
        MergeSort.sort(items, ascending);
    }

    public void sortItemsByTitle(ProductCategory category, boolean ascending) {
        List<Product> items = getItemsByCategory(category);
        Collections.sort(items, Comparator.comparing(product -> product.itemName));
        if (!ascending) {
            Collections.reverse(items);
        }
    }

    public void sortItemsByDate(ProductCategory category, boolean ascending) {
        List<Product> items = getItemsByCategory(category);
        Collections.sort(items, Comparator.comparing(Product::getAddedDate));
        if (!ascending) {
            Collections.reverse(items);
        }
    }
     public void sortItemsByPrice(ProductCategory category, boolean ascending) {
    List<Product> items = getItemsByCategory(category);
    MergeSort.sort(items, ascending);
}

public List<Product> getAllItemsByCategory() {
    List<Product> allItems = new ArrayList<>();
    for (ProductCategory category : ProductCategory.values()) {
        allItems.addAll(getItemsByCategory(category));
    }
    return allItems;
}
    public Product searchItem(String itemName) {
        return itemMap.get(itemName);
    }

    public List<Product> searchByCategory(ProductCategory category) {
        return getItemsByCategory(category);
    }

    public List<Product> searchByDateRange(ProductCategory category, Date startDate, Date endDate) {
        List<Product> items = getItemsByCategory(category);
        List<Product> result = new ArrayList<>();
        for (Product item : items) {
            if (item.getAddedDate().after(startDate) && item.getAddedDate().before(endDate)) {
                result.add(item);
            }
        }
        return result;
    }
}




