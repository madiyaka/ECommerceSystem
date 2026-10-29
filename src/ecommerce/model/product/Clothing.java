package ecommerce.model.product;

public class Clothing extends Product {

    private String size;
    private String color;

    public Clothing(int id, String name, double price, int stockQuantity, String size, String color) {
        super(id, name, price, stockQuantity);
        this.size = size;
        this.color = color;
    }

    @Override
    public String getCategory() {
        return "Clothing";
    }

    @Override
    protected String getDetails() {
        return "size: " + size + ", color: " + color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
