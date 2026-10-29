package ecommerce.ui;

import ecommerce.model.order.Order;
import ecommerce.model.order.OrderItem;
import ecommerce.model.product.Book;
import ecommerce.model.product.Clothing;
import ecommerce.model.product.Electronics;
import ecommerce.model.product.Product;
import ecommerce.model.user.Customer;
import ecommerce.model.user.Employee;
import ecommerce.model.user.User;
import ecommerce.service.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Console user interface: main menu, customer menu and employee menu.
 * All data operations are delegated to the Store.
 */
public class ConsoleApp {

    private Store store;
    private InputHelper input;

    public ConsoleApp(Store store, InputHelper input) {
        this.store = store;
        this.input = input;
    }

    public void run() {
        System.out.println("Welcome to the Console E-Commerce System!");
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("===== MAIN MENU =====");
            System.out.println("1. Login");
            System.out.println("2. Register as customer");
            System.out.println("3. Browse products (guest)");
            System.out.println("0. Exit");
            int choice = input.readInt("Choose: ");
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    registerCustomer();
                    break;
                case 3:
                    listProducts();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        }
        System.out.println("Goodbye!");
    }

    private void login() {
        String email = input.readNonEmpty("Email: ");
        String password = input.readNonEmpty("Password: ");
        User user = store.login(email, password);
        if (user == null) {
            System.out.println("Wrong email or password.");
            return;
        }
        System.out.println("Hello, " + user.getName() + "! You are logged in as " + user.getRole() + ".");
        // The menu depends on the real (runtime) type of the user object.
        if (user instanceof Employee) {
            employeeMenu((Employee) user);
        } else if (user instanceof Customer) {
            customerMenu((Customer) user);
        }
    }

    private void registerCustomer() {
        try {
            store.addUser(readNewCustomer());
            System.out.println("Registration successful. You can now log in.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===================== CUSTOMER MENU =====================

    private void customerMenu(Customer customer) {
        List<OrderItem> cart = new ArrayList<>();
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println();
            System.out.println("===== CUSTOMER MENU (" + customer.getName() + ") =====");
            System.out.println("1. List products");
            System.out.println("2. Add product to cart");
            System.out.println("3. View cart");
            System.out.println("4. Place order");
            System.out.println("5. View my orders");
            System.out.println("0. Logout");
            int choice = input.readInt("Choose: ");
            switch (choice) {
                case 1:
                    listProducts();
                    break;
                case 2:
                    addToCart(cart);
                    break;
                case 3:
                    viewCart(cart);
                    break;
                case 4:
                    placeOrder(customer, cart);
                    break;
                case 5:
                    printOrders(store.getOrdersByCustomer(customer));
                    break;
                case 0:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    private void addToCart(List<OrderItem> cart) {
        listProducts();
        int id = input.readInt("Product ID: ");
        Product product = store.findProductById(id);
        if (product == null) {
            System.out.println("No product with ID " + id + ".");
            return;
        }
        int quantity = input.readPositiveInt("Quantity: ");

        // If the product is already in the cart, increase its quantity instead of adding a second line.
        OrderItem existing = null;
        for (OrderItem item : cart) {
            if (item.getProduct() == product) {
                existing = item;
            }
        }
        int newQuantity = (existing == null) ? quantity : existing.getQuantity() + quantity;
        if (!product.isInStock(newQuantity)) {
            System.out.println("Only " + product.getStockQuantity() + " in stock.");
            return;
        }
        if (existing == null) {
            cart.add(new OrderItem(product, quantity));
        } else {
            existing.setQuantity(newQuantity);
        }
        System.out.println("Added " + quantity + " x " + product.getName() + " to cart.");
    }

    private void viewCart(List<OrderItem> cart) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        double total = 0;
        System.out.println("--- Cart ---");
        for (OrderItem item : cart) {
            System.out.printf(" %-24s %3d x %9.2f = %10.2f%n",
                    item.getProduct().getName(), item.getQuantity(),
                    item.getUnitPrice(), item.getSubtotal());
            total += item.getSubtotal();
        }
        System.out.printf(" Total: %.2f KZT%n", total);
    }

    private void placeOrder(Customer customer, List<OrderItem> cart) {
        try {
            Order order = store.placeOrder(customer, cart);
            System.out.println("Order placed successfully!");
            order.printReceipt();
            cart.clear();
        } catch (IllegalArgumentException e) {
            System.out.println("Could not place order: " + e.getMessage());
        }
    }

    // ===================== EMPLOYEE MENU =====================

    private void employeeMenu(Employee employee) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println();
            System.out.println("===== EMPLOYEE MENU (" + employee.getName() + ", " + employee.getPosition() + ") =====");
            System.out.println("1. List products");
            System.out.println("2. Add product");
            System.out.println("3. Restock product");
            System.out.println("4. List users");
            System.out.println("5. Add user");
            System.out.println("6. View all orders");
            System.out.println("0. Logout");
            int choice = input.readInt("Choose: ");
            switch (choice) {
                case 1:
                    listProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    restockProduct();
                    break;
                case 4:
                    listUsers();
                    break;
                case 5:
                    addUser();
                    break;
                case 6:
                    printOrders(store.getOrders());
                    break;
                case 0:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    private void addProduct() {
        System.out.println("Product type: 1. Book  2. Electronics  3. Clothing");
        int type = input.readInt("Choose: ");
        if (type < 1 || type > 3) {
            System.out.println("Unknown product type.");
            return;
        }
        String name = input.readNonEmpty("Name: ");
        double price = input.readNonNegativeDouble("Price (KZT): ");
        int stock = input.readPositiveInt("Stock quantity: ");
        int id = store.generateProductId();

        Product product;
        if (type == 1) {
            String author = input.readNonEmpty("Author: ");
            int pages = input.readPositiveInt("Pages: ");
            product = new Book(id, name, price, stock, author, pages);
        } else if (type == 2) {
            String brand = input.readNonEmpty("Brand: ");
            int warranty = input.readPositiveInt("Warranty (months): ");
            product = new Electronics(id, name, price, stock, brand, warranty);
        } else {
            String size = input.readNonEmpty("Size: ");
            String color = input.readNonEmpty("Color: ");
            product = new Clothing(id, name, price, stock, size, color);
        }
        store.addProduct(product);
        System.out.println("Product added:");
        product.displayInfo();
    }

    private void restockProduct() {
        listProducts();
        int id = input.readInt("Product ID: ");
        Product product = store.findProductById(id);
        if (product == null) {
            System.out.println("No product with ID " + id + ".");
            return;
        }
        int quantity = input.readPositiveInt("Quantity to add: ");
        product.increaseStock(quantity);
        System.out.println("New stock for " + product.getName() + ": " + product.getStockQuantity());
    }

    private void addUser() {
        System.out.println("User type: 1. Customer  2. Employee");
        int type = input.readInt("Choose: ");
        try {
            if (type == 1) {
                store.addUser(readNewCustomer());
            } else if (type == 2) {
                String name = input.readNonEmpty("Name: ");
                String email = input.readNonEmpty("Email: ");
                String password = input.readNonEmpty("Password: ");
                String position = input.readNonEmpty("Position: ");
                double salary = input.readNonNegativeDouble("Salary (KZT): ");
                store.addUser(new Employee(store.generateUserId(), name, email, password, position, salary));
            } else {
                System.out.println("Unknown user type.");
                return;
            }
            System.out.println("User added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===================== SHARED HELPERS =====================

    private Customer readNewCustomer() {
        String name = input.readNonEmpty("Name: ");
        String email = input.readNonEmpty("Email: ");
        String password = input.readNonEmpty("Password: ");
        String address = input.readNonEmpty("Shipping address: ");
        return new Customer(store.generateUserId(), name, email, password, address);
    }

    private void listProducts() {
        List<Product> products = store.getProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("--- Products ---");
        for (Product product : products) {
            product.displayInfo(); // polymorphism: each subclass supplies its own details
        }
    }

    private void listUsers() {
        System.out.println("--- Users ---");
        for (User user : store.getUsers()) {
            user.displayInfo();
        }
    }

    private void printOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }
        for (Order order : orders) {
            order.printReceipt();
        }
    }
}
