package ecommerce.model.order;

import ecommerce.model.user.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * An order placed by one customer, containing one or more order items.
 */
public class Order {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private int orderId;
    private Customer customer;
    private ArrayList<OrderItem> items;
    private double totalAmount;
    private LocalDateTime orderDate;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.totalAmount = 0;
        this.orderDate = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotal();
    }

    /** Recalculates totalAmount from the items, so the total is always consistent. */
    public double calculateTotal() {
        double sum = 0;
        for (OrderItem item : items) {
            sum += item.getSubtotal();
        }
        totalAmount = sum;
        return totalAmount;
    }

    public void printReceipt() {
        System.out.println("==================================================");
        System.out.println(" RECEIPT  -  Order #" + orderId);
        System.out.println(" Date:     " + orderDate.format(DATE_FORMAT));
        System.out.println(" Customer: " + customer.getName() + " (" + customer.getEmail() + ")");
        System.out.println(" Ship to:  " + customer.getShippingAddress());
        System.out.println("--------------------------------------------------");
        for (OrderItem item : items) {
            System.out.printf(" %-24s %3d x %9.2f = %10.2f%n",
                    item.getProduct().getName(), item.getQuantity(),
                    item.getUnitPrice(), item.getSubtotal());
        }
        System.out.println("--------------------------------------------------");
        System.out.printf(" TOTAL: %38.2f KZT%n", totalAmount);
        System.out.println("==================================================");
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    /** Returns a copy so callers cannot modify the order's items directly. */
    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
