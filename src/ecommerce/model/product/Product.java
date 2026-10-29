package ecommerce.model.product;

/**
 * Base class for everything the store sells.
 * Abstract because every real product belongs to a category (Book, Electronics, Clothing).
 */
public abstract class Product {

    private int id;
    private String name;
    private double price;
    private int stockQuantity;

    protected Product(int id, String name, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    /** Prints the common product fields followed by the category-specific details. */
    public void displayInfo() {
        System.out.printf("#%-3d %-12s %-28s %10.2f KZT  stock: %-4d %s%n",
                id, getCategory(), name, price, stockQuantity, getDetails());
    }

    /** Category name shown in listings, e.g. "Book". */
    public abstract String getCategory();

    /** Category-specific attributes as text, e.g. "author: ...". */
    protected abstract String getDetails();

    public boolean isInStock(int quantity) {
        return quantity > 0 && quantity <= stockQuantity;
    }

    /** Removes sold items from stock. Called only after isInStock() was checked. */
    public void reduceStock(int quantity) {
        if (!isInStock(quantity)) {
            throw new IllegalArgumentException("Not enough stock for " + name);
        }
        stockQuantity -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        stockQuantity += quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }
}
