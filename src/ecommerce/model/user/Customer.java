package ecommerce.model.user;

/**
 * A customer can browse products and place orders.
 */
public class Customer extends User {

    private String shippingAddress;

    public Customer(int userId, String name, String email, String password, String shippingAddress) {
        super(userId, name, email, password, "CUSTOMER");
        this.shippingAddress = shippingAddress;
    }

    @Override
    protected String getExtraInfo() {
        return "address: " + shippingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
