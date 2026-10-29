package ecommerce;

import ecommerce.model.product.Book;
import ecommerce.model.product.Clothing;
import ecommerce.model.product.Electronics;
import ecommerce.model.user.Customer;
import ecommerce.model.user.Employee;
import ecommerce.service.Store;
import ecommerce.ui.ConsoleApp;
import ecommerce.ui.InputHelper;

import java.util.Scanner;

/**
 * Entry point. Creates the store, loads demo data and starts the console menu.
 */
public class Main {

    public static void main(String[] args) {
        Store store = new Store();
        loadSampleData(store);

        Scanner scanner = new Scanner(System.in);
        ConsoleApp app = new ConsoleApp(store, new InputHelper(scanner));
        app.run();
        scanner.close();
    }

    /** Demo data so the application can be shown immediately. */
    private static void loadSampleData(Store store) {
        store.addUser(new Employee(store.generateUserId(), "Admin", "admin@shop.kz", "admin123", "Manager", 450000));
        store.addUser(new Customer(store.generateUserId(), "Aida", "aida@mail.kz", "1234", "Almaty, Abay Ave 10"));
        store.addUser(new Customer(store.generateUserId(), "Daniyar", "dan@mail.kz", "1234", "Astana, Mangilik El 5"));

        store.addProduct(new Book(store.generateProductId(), "Clean Code", 12500, 10, "Robert C. Martin", 464));
        store.addProduct(new Book(store.generateProductId(), "Head First Java", 15000, 5, "Kathy Sierra", 720));
        store.addProduct(new Electronics(store.generateProductId(), "Wireless Mouse", 8900, 20, "Logitech", 12));
        store.addProduct(new Electronics(store.generateProductId(), "Laptop 15\"", 420000, 3, "Lenovo", 24));
        store.addProduct(new Clothing(store.generateProductId(), "Hoodie", 18000, 15, "M", "Black"));
        store.addProduct(new Clothing(store.generateProductId(), "T-Shirt", 6500, 30, "L", "White"));
    }
}
