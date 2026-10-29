package ecommerce.model.product;

public class Electronics extends Product {

    private String brand;
    private int warrantyMonths;

    public Electronics(int id, String name, double price, int stockQuantity, String brand, int warrantyMonths) {
        super(id, name, price, stockQuantity);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }

    @Override
    protected String getDetails() {
        return "brand: " + brand + ", warranty " + warrantyMonths + " months";
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }
}
