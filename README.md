# Console-Based E-Commerce System (Java)

Assignment 1: a console e-commerce system that shows classes, inheritance, encapsulation and `ArrayList` collections.

## Run

IntelliJ IDEA: open this folder, mark `src` as Sources Root, run `ecommerce.Main`.

Command line:

```bash
javac -d out src/ecommerce/*.java src/ecommerce/*/*.java src/ecommerce/*/*/*.java
java -cp out ecommerce.Main
```

## Demo accounts

| Role     | Email          | Password |
|----------|----------------|----------|
| Employee | admin@shop.kz  | admin123 |
| Customer | aida@mail.kz   | 1234     |
| Customer | dan@mail.kz    | 1234     |

## Structure

```
src/ecommerce/
├── Main.java                 entry point + demo data
├── model/user/               User (abstract), Customer, Employee
├── model/product/            Product (abstract), Book, Electronics, Clothing
├── model/order/              Order, OrderItem
├── service/Store.java        ArrayLists of users/products/orders + business rules
└── ui/                       ConsoleApp (menus), InputHelper (safe Scanner input)
docs/                         UML diagram (svg/png), report (html/pdf), build_report.py
```
