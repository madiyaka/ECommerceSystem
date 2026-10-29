package ecommerce.model.user;

/**
 * An employee manages the store: adds products and users, restocks and views all orders.
 */
public class Employee extends User {

    private String position;
    private double salary;

    public Employee(int userId, String name, String email, String password, String position, double salary) {
        super(userId, name, email, password, "EMPLOYEE");
        this.position = position;
        setSalary(salary);
    }

    @Override
    protected String getExtraInfo() {
        return "position: " + position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }
}
