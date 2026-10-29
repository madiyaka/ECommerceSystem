package ecommerce.model.order;

import ecommerce.model.product.Product;

/**
 * One line of an order: a product and how many units were bought.
 * The unit price is copied at purchase time so later price changes
 * do not change old receipts.
 */
public class OrderItem {

    private Product product;
    private int quantity;
    private double unitPrice;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
