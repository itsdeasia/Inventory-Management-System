import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductManager inventoryManager = new ProductManager();
        Random randomGenerator = new Random();
        Scanner scanner = new Scanner(System.in);

        // Insert 10,000 items in random categories
        for (int i = 0; i < 10000; i++) {
            // Randomly select a category
            ProductCategory category = getRandomCategory(randomGenerator);
            String productName = category + "-" + i;
            double itemPrice = randomGenerator.nextDouble() * 100;
            Date dateAdded = new Date();

            Product item = new Product(itemPrice, productName, dateAdded, category);
            inventoryManager.insertItem(item);
        }

        // Read data from CSV and insert items
        readDataFromCSV(inventoryManager, "Pant_Info_1K.csv");

        boolean continueProgram = true;

        // User interaction
        while (continueProgram) {
             long startTime = System.currentTimeMillis(); 
            System.out.println("\nChoose an operation:");
            System.out.println("1. Sort by price (ascending)");
            System.out.println("2. Sort by price (descending)");
            System.out.println("3. Delete item");
            System.out.println("4. Insert new item");
            System.out.println("5. Search item");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    sortItems(inventoryManager, ProductCategory.Pant, true);
                    break;
                case 2:
                    sortItems(inventoryManager, ProductCategory.Pant, false);
                    break;
                case 3:
                    System.out.print("Enter the item to delete (e.g., P789): ");
                    String itemToDelete = scanner.nextLine();
                    deleteItem(inventoryManager, itemToDelete);
                    break;
                case 4:
                    System.out.print("Enter the price for the new item: ");
                    double newItemPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character

                    System.out.print("Enter the name for the new item (e.g., P123): ");
                    String newItemName = scanner.nextLine();

                    insertItem(inventoryManager, newItemPrice, newItemName);
                    break;
                case 5:
                    System.out.print("Enter the item to search (e.g., P456): ");
                    String itemToSearch = scanner.nextLine();
                    searchItem(inventoryManager, itemToSearch);
                    break;
                case 6:
                    continueProgram = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }

        scanner.close();
    
}


    private static void sortItems(ProductManager inventoryManager, ProductCategory category, boolean ascending) {
    long startTime = System.currentTimeMillis();
    inventoryManager.sortItemsByPrice(category, ascending);
    long endTime = System.currentTimeMillis();

    String order = ascending ? "ascending" : "descending";
    System.out.println("Sorting by price (" + order + "): " + (endTime - startTime) + " ms");
    printSortedItems(inventoryManager.getAllItemsByCategory());
}

    private static void deleteItem(ProductManager inventoryManager, String itemToDelete) {
        long startTime = System.currentTimeMillis();
        inventoryManager.deleteItem(itemToDelete);
        long endTime = System.currentTimeMillis();

        System.out.println("Delete Time: " + (endTime - startTime) + " ms");
    }

    private static void insertItem(ProductManager inventoryManager, double newItemPrice, String newItemName) {
        long startTime = System.currentTimeMillis();
        Product newItem = new Product(newItemPrice, newItemName, new Date(), ProductCategory.Pant);
        inventoryManager.insertItem(newItem);
        long endTime = System.currentTimeMillis();

        System.out.println("Insert Time: " + (endTime - startTime) + " ms");
    }

    private static void searchItem(ProductManager inventoryManager, String itemToSearch) {
        long startTime = System.currentTimeMillis();
        Product foundItem = inventoryManager.searchItem(itemToSearch);
        long endTime = System.currentTimeMillis();

        System.out.println("Search Time: " + (endTime - startTime) + " ms");
        if (foundItem != null) {
            System.out.println("Item found: " + foundItem.itemName);
        } else {
            System.out.println("Item not found.");
        }
    }
private static void sortItemsByPrice(ProductManager inventoryManager, ProductCategory category, boolean ascending) {
    long startTime = System.currentTimeMillis();
    inventoryManager.sortItemsByPrice(category, ascending);
    long endTime = System.currentTimeMillis();

    String order = ascending ? "ascending" : "descending";
    System.out.println("Sorting by price (" + order + "): " + (endTime - startTime) + " ms");
    printSortedItems(inventoryManager.getAllItemsByCategory());
}
    private static void printSortedItems(List<Product> sortedItems) {
        System.out.println(",Title,Price,Date");

        for (Product item : sortedItems) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(item.getAddedDate());
            String formattedCost = String.format("$%.2f", item.cost);

            System.out.println(sortedItems.indexOf(item) + "," + item.itemName + "," + formattedCost + "," + formattedDate);
        }
        System.out.println();
    }

    private static ProductCategory getRandomCategory(Random randomGenerator) {
        int categoryIndex = randomGenerator.nextInt(ProductCategory.values().length);
        return ProductCategory.values()[categoryIndex];
    }

    private static void readDataFromCSV(ProductManager inventoryManager, String csvFilePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
        String line;
        String splitBy = ",";
        
        // Read and skip the header line
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] product = line.split(splitBy);

            // Assuming price is in the third column (index 2)
            if (product.length >= 3) {
                // Validate that the price is a valid double value
                try {
                    double itemPrice = Double.parseDouble(product[2]);
                    Date dateAdded = new Date();

                    Product item = new Product(itemPrice, product[1], dateAdded, ProductCategory.Pant);
                    inventoryManager.insertItem(item);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing price for item: " + product[1]);
                    // Handle the error or log it as needed
                }
            } else {
                System.err.println("Invalid data format for line: " + line);
                // Handle the error or log it as needed
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
