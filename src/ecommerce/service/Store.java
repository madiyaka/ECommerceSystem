package ecommerce.service;

import ecommerce.model.order.Order;
import ecommerce.model.order.OrderItem;
import ecommerce.model.product.Product;
import ecommerce.model.user.Customer;
import ecommerce.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all data of the shop in ArrayLists and contains the business rules
 * (login, adding users/products, placing orders). The console UI only talks to this class.
 */
public class Store {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();

    private int nextUserId = 1;
    private int nextProductId = 1;
    private int nextOrderId = 1;

    // ---------- ID generation ----------

    public int generateUserId() {
        return nextUserId++;
    }

    public int generateProductId() {
        return nextProductId++;
    }

    // ---------- Users ----------

    public void addUser(User user) {
        if (findUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("A user with email " + user.getEmail() + " already exists");
        }
        users.add(user);
    }

    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    /** Returns the matching user, or null if the email/password is wrong. */
    public User login(String email, String password) {
        User user = findUserByEmail(email);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    // ---------- Products ----------

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product findProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    // ---------- Orders ----------

    /**
     * Creates an order from the customer's cart.
     * Stock is checked for every item first, so either the whole order succeeds or nothing changes.
     */
    public Order placeOrder(Customer customer, List<OrderItem> cart) {
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        for (OrderItem item : cart) {
            if (!item.getProduct().isInStock(item.getQuantity())) {
                throw new IllegalArgumentException("Not enough stock for " + item.getProduct().getName()
                        + " (available: " + item.getProduct().getStockQuantity() + ")");
            }
        }

        Order order = new Order(nextOrderId++, customer);
        for (OrderItem item : cart) {
            item.getProduct().reduceStock(item.getQuantity());
            order.addItem(item);
        }
        orders.add(order);
        return order;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public List<Order> getOrdersByCustomer(Customer customer) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomer() == customer) {
                result.add(order);
            }
        }
        return result;
    }
}
