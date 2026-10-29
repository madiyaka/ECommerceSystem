"""Builds report.html (then print it to PDF) from the template text below + the Java sources.

Fill in the values below, then run:  python docs/build_report.py
"""
import html
import pathlib

STUDENT_NAME = "Madiyar Seilkhan"
COURSE_NAME = "ICT"
GIT_URL = "https://github.com/madiyaka/ECommerceSystem"
SUBMISSION_DATE = "29 October 2026"

ROOT = pathlib.Path(__file__).resolve().parent.parent
DOCS = ROOT / "docs"
SRC = ROOT / "src"

SOURCE_ORDER = [
    "ecommerce/Main.java",
    "ecommerce/model/user/User.java",
    "ecommerce/model/user/Customer.java",
    "ecommerce/model/user/Employee.java",
    "ecommerce/model/product/Product.java",
    "ecommerce/model/product/Book.java",
    "ecommerce/model/product/Electronics.java",
    "ecommerce/model/product/Clothing.java",
    "ecommerce/model/order/Order.java",
    "ecommerce/model/order/OrderItem.java",
    "ecommerce/service/Store.java",
    "ecommerce/ui/InputHelper.java",
    "ecommerce/ui/ConsoleApp.java",
]

SAMPLE_RUN = """===== CUSTOMER MENU (Aida) =====
1. List products
2. Add product to cart
3. View cart
4. Place order
5. View my orders
0. Logout
Choose: 1
--- Products ---
#1   Book         Clean Code                     12500,00 KZT  stock: 10   author: Robert C. Martin, 464 pages
#3   Electronics  Wireless Mouse                  8900,00 KZT  stock: 20   brand: Logitech, warranty 12 months
#5   Clothing     Hoodie                         18000,00 KZT  stock: 15   size: M, color: Black
...
Choose: 4
Order placed successfully!
==================================================
 RECEIPT  -  Order #1
 Date:     2026-10-29 23:02
 Customer: Aida (aida@mail.kz)
 Ship to:  Almaty, Abay Ave 10
--------------------------------------------------
 Wireless Mouse             2 x   8900,00 =   17800,00
 Laptop 15"                 1 x 420000,00 =  420000,00
--------------------------------------------------
 TOTAL:                              437800,00 KZT
=================================================="""


def code_annex():
    parts = []
    for i, rel in enumerate(SOURCE_ORDER, 1):
        text = (SRC / rel).read_text(encoding="utf-8")
        parts.append(f'<h3>A.{i} src/{rel}</h3><pre class="code">{html.escape(text)}</pre>')
    return "\n".join(parts)


svg = (DOCS / "uml-class-diagram.svg").read_text(encoding="utf-8")

page = f"""<!doctype html>
<html lang="en"><head><meta charset="utf-8">
<title>E-Commerce System Report</title>
<style>
@page {{ size: A4; margin: 20mm 18mm; }}
@page wide {{ size: A3 landscape; margin: 10mm; }}
body {{ font-family: Calibri, 'Segoe UI', Arial, sans-serif; font-size: 11pt; line-height: 1.45; color: #111; }}
h1 {{ font-size: 20pt; margin: 0 0 6px; }}
h2 {{ font-size: 15pt; border-bottom: 2px solid #1e3a8a; padding-bottom: 3px; margin-top: 26px; color: #1e3a8a; }}
h3 {{ font-size: 12pt; margin: 16px 0 6px; }}
table {{ border-collapse: collapse; width: 100%; margin: 8px 0 12px; font-size: 10pt; }}
th, td {{ border: 1px solid #9ca3af; padding: 4px 7px; text-align: left; vertical-align: top; }}
th {{ background: #e8eefc; }}
code {{ font-family: Consolas, monospace; font-size: 9.5pt; background: #f3f4f6; padding: 0 3px; }}
pre {{ font-family: Consolas, monospace; font-size: 8.3pt; line-height: 1.3; background: #f8fafc;
       border: 1px solid #d1d5db; padding: 8px; white-space: pre-wrap; }}
pre.code {{ break-inside: auto; }}
.cover {{ height: 245mm; display: flex; flex-direction: column; justify-content: center; text-align: center; break-after: page; }}
.cover .title {{ font-size: 26pt; font-weight: bold; color: #1e3a8a; margin-bottom: 10px; }}
.cover .sub {{ font-size: 14pt; color: #374151; margin-bottom: 60px; }}
.cover table {{ width: 85%; margin: 0 auto; font-size: 12pt; }}
.cover td {{ border: none; padding: 6px; }}
.cover td:first-child {{ font-weight: bold; text-align: right; width: 38%; white-space: nowrap; }}
.toc li {{ margin: 2px 0; }}
.uml {{ page: wide; break-before: page; break-after: page; text-align: center; }}
.uml svg {{ width: 100%; height: auto; max-height: 265mm; }}
.annex {{ break-before: page; }}
.newpage {{ break-before: page; }}
</style></head><body>

<section class="cover">
  <div class="title">Design and Implementation of a<br>Console-Based E-Commerce System using Java</div>
  <div class="sub">Assignment 1 &mdash; Report</div>
  <table>
    <tr><td>Student name:</td><td>{STUDENT_NAME}</td></tr>
    <tr><td>Course:</td><td>{COURSE_NAME}</td></tr>
    <tr><td>Date of submission:</td><td>{SUBMISSION_DATE}</td></tr>
    <tr><td>Git repository:</td><td>{html.escape(GIT_URL)}</td></tr>
  </table>
</section>

<h2>Contents</h2>
<ol class="toc">
  <li>Objective</li>
  <li>Requirement Analysis</li>
  <li>Object Modeling</li>
  <li>UML Class Diagram</li>
  <li>Design and Implementation Notes</li>
  <li>How to Run and Sample Session</li>
  <li>Conclusion</li>
  <li>Annex A &mdash; Source Code</li>
</ol>

<h2>1. Objective</h2>
<p>The goal of this project is to design and implement a simple console-based e-commerce system in Java.
The system manages users (customers and employees), products (books, electronics and clothing) and orders.
Customers can browse products, fill a shopping cart and place orders; employees can add products and users,
restock products and view all orders.</p>
<p>The project is used to practise the main principles of object-oriented programming:
class design with attributes and methods, <b>encapsulation</b>, <b>inheritance</b>, <b>abstraction</b> and
<b>polymorphism</b>, together with the Java Collections Framework (<code>ArrayList</code>) and console
input with <code>Scanner</code>.</p>

<h2>2. Requirement Analysis</h2>
<h3>2.1 Functional requirements</h3>
<table>
<tr><th>ID</th><th>Requirement</th><th>Who</th><th>Implemented in</th></tr>
<tr><td>FR1</td><td>Log in with email and password; show a menu that depends on the role</td><td>All users</td><td><code>Store.login()</code>, <code>ConsoleApp.login()</code></td></tr>
<tr><td>FR2</td><td>Register a new customer account</td><td>Guest</td><td><code>ConsoleApp.registerCustomer()</code>, <code>Store.addUser()</code></td></tr>
<tr><td>FR3</td><td>List available products with category-specific details</td><td>All</td><td><code>Product.displayInfo()</code>, <code>ConsoleApp.listProducts()</code></td></tr>
<tr><td>FR4</td><td>Add products (Book, Electronics, Clothing) to the system</td><td>Employee</td><td><code>ConsoleApp.addProduct()</code>, <code>Store.addProduct()</code></td></tr>
<tr><td>FR5</td><td>Add users (Customer or Employee) and list all users</td><td>Employee</td><td><code>ConsoleApp.addUser()</code>, <code>ConsoleApp.listUsers()</code></td></tr>
<tr><td>FR6</td><td>Add products to a cart and view the cart</td><td>Customer</td><td><code>ConsoleApp.addToCart()</code>, <code>viewCart()</code></td></tr>
<tr><td>FR7</td><td>Place an order from the cart; stock decreases; a receipt is printed</td><td>Customer</td><td><code>Store.placeOrder()</code>, <code>Order.printReceipt()</code></td></tr>
<tr><td>FR8</td><td>View own orders (customer) / all orders (employee)</td><td>Customer, Employee</td><td><code>Store.getOrdersByCustomer()</code>, <code>Store.getOrders()</code></td></tr>
<tr><td>FR9</td><td>Restock a product</td><td>Employee</td><td><code>Product.increaseStock()</code></td></tr>
</table>

<h3>2.2 Non-functional requirements</h3>
<ul>
  <li>All attributes are <code>private</code> and accessed through getters/setters (encapsulation).</li>
  <li>Data is kept in memory in <code>ArrayList</code> collections.</li>
  <li>Invalid input (letters instead of numbers, empty values, unknown IDs, too large quantities) must not crash the program.</li>
  <li>Code is organised in packages and uses meaningful names.</li>
</ul>

<h3>2.3 Identifying objects, attributes and methods</h3>
<p>Classes were found by taking the <b>nouns</b> from the requirements (user, customer, employee, product, book,
electronics, clothing, order) and methods from the <b>verbs</b> (add, list, place order, view, print receipt).</p>
<table>
<tr><th>Object</th><th>Attributes</th><th>Main methods</th></tr>
<tr><td>User</td><td>userId, name, email, password, role</td><td>checkPassword(), displayInfo(), getters/setters</td></tr>
<tr><td>Customer</td><td>+ shippingAddress</td><td>getShippingAddress(), setShippingAddress()</td></tr>
<tr><td>Employee</td><td>+ position, salary</td><td>getPosition(), setSalary()</td></tr>
<tr><td>Product</td><td>id, name, price, stockQuantity</td><td>displayInfo(), isInStock(), reduceStock(), increaseStock()</td></tr>
<tr><td>Book</td><td>+ author, pages</td><td>getCategory(), getDetails()</td></tr>
<tr><td>Electronics</td><td>+ brand, warrantyMonths</td><td>getCategory(), getDetails()</td></tr>
<tr><td>Clothing</td><td>+ size, color</td><td>getCategory(), getDetails()</td></tr>
<tr><td>Order</td><td>orderId, customer, items, totalAmount, orderDate</td><td>addItem(), calculateTotal(), printReceipt()</td></tr>
<tr><td>OrderItem</td><td>product, quantity, unitPrice</td><td>getSubtotal()</td></tr>
<tr><td>Store</td><td>users, products, orders (ArrayLists), ID counters</td><td>addUser(), addProduct(), login(), placeOrder(), find...()</td></tr>
</table>
<p><code>OrderItem</code> and <code>Store</code> are not named directly in the requirements; they were added during design
(see section 5.5).</p>

<h2>3. Object Modeling</h2>
<h3>3.1 Classes and packages</h3>
<table>
<tr><th>Package</th><th>Classes</th><th>Responsibility</th></tr>
<tr><td><code>ecommerce</code></td><td>Main</td><td>Program entry point, loads demo data</td></tr>
<tr><td><code>ecommerce.model.user</code></td><td>User (abstract), Customer, Employee</td><td>People who use the system</td></tr>
<tr><td><code>ecommerce.model.product</code></td><td>Product (abstract), Book, Electronics, Clothing</td><td>Items that are sold</td></tr>
<tr><td><code>ecommerce.model.order</code></td><td>Order, OrderItem</td><td>Purchases</td></tr>
<tr><td><code>ecommerce.service</code></td><td>Store</td><td>Stores all data in ArrayLists and applies business rules</td></tr>
<tr><td><code>ecommerce.ui</code></td><td>ConsoleApp, InputHelper</td><td>Console menus and safe input with Scanner</td></tr>
</table>

<h3>3.2 Relationships</h3>
<table>
<tr><th>Relationship</th><th>Type</th><th>Multiplicity</th><th>Meaning</th></tr>
<tr><td>Customer, Employee &rarr; User</td><td>Inheritance (generalization)</td><td>&ndash;</td><td>A customer <i>is a</i> user; an employee <i>is a</i> user</td></tr>
<tr><td>Book, Electronics, Clothing &rarr; Product</td><td>Inheritance (generalization)</td><td>&ndash;</td><td>Each is a kind of product</td></tr>
<tr><td>Order &rarr; Customer</td><td>Association</td><td>0..* orders &ndash; 1 customer</td><td>Every order belongs to exactly one customer</td></tr>
<tr><td>Order &#9670;&ndash; OrderItem</td><td>Composition</td><td>1 &ndash; 1..*</td><td>Order items exist only as part of an order</td></tr>
<tr><td>OrderItem &rarr; Product</td><td>Association</td><td>0..* &ndash; 1</td><td>Each line refers to one product</td></tr>
<tr><td>Store &#9671;&ndash; User / Product / Order</td><td>Aggregation</td><td>1 &ndash; 0..*</td><td>The store keeps collections of all users, products and orders</td></tr>
<tr><td>ConsoleApp &rarr; Store, InputHelper</td><td>Association</td><td>1 &ndash; 1</td><td>The UI uses the store for data and InputHelper for reading input</td></tr>
<tr><td>Main ..&gt; ConsoleApp, Store</td><td>Dependency &laquo;create&raquo;</td><td>&ndash;</td><td>Main creates the objects and starts the app</td></tr>
</table>

<section class="uml">
<h2 style="text-align:left">4. UML Class Diagram</h2>
{svg}
<p style="font-size:9pt;text-align:left">Notation: <b>-</b> private, <b>+</b> public, <b>#</b> protected; <i>italic</i> = abstract class/method;
<u>underlined</u> = static. Hollow triangle = inheritance, filled diamond = composition, hollow diamond = aggregation,
open arrow = navigable association, dashed arrow = dependency. The diagram is also provided as <code>docs/uml-class-diagram.svg</code>/<code>.png</code>.</p>
</section>

<h2>5. Design and Implementation Notes</h2>
<h3>5.1 Encapsulation</h3>
<p>Every attribute in every class is <code>private</code>. Other classes access data only through public getters and setters.
Setters are used to protect the object's state, for example <code>Product.setPrice()</code> and <code>setStockQuantity()</code>
reject negative values, and <code>Employee.setSalary()</code> rejects a negative salary. The password has a setter but
<b>no getter</b>: it can only be checked with <code>checkPassword()</code>, so it is never exposed. Methods that return lists
(<code>Store.getProducts()</code>, <code>Order.getItems()</code>, ...) return a <b>copy</b> of the internal ArrayList, so code outside
the class cannot add or remove elements behind the class's back. Stock can only change through
<code>reduceStock()</code> / <code>increaseStock()</code>, which check the values.</p>

<h3>5.2 Inheritance and abstraction</h3>
<p><code>User</code> and <code>Product</code> are declared <code>abstract</code> because a "plain" user or product never exists &mdash;
there is always a concrete type. The common attributes (id, name, price, stock...) and common behaviour
(<code>displayInfo()</code>, <code>isInStock()</code>, <code>checkPassword()</code>) are written once in the superclass and reused by all
subclasses. Subclass constructors call <code>super(...)</code> to initialise the inherited private fields. The role of a user
("CUSTOMER" / "EMPLOYEE") is set automatically by the subclass constructor, so it can never be inconsistent with the
real type.</p>

<h3>5.3 Polymorphism</h3>
<p><code>Product.displayInfo()</code> prints the common fields and then calls the abstract methods <code>getCategory()</code> and
<code>getDetails()</code>. Each subclass overrides them (<code>@Override</code>), so when the store loops over an
<code>ArrayList&lt;Product&gt;</code> and calls <code>displayInfo()</code>, a book shows its author, electronics show brand and
warranty, and clothing shows size and color. The same idea is used in <code>User.displayInfo()</code> with
<code>getExtraInfo()</code>. After login, <code>instanceof</code> is used to decide whether the customer menu or the employee
menu is shown.</p>

<h3>5.4 Collections</h3>
<p><code>Store</code> keeps three collections: <code>ArrayList&lt;User&gt;</code>, <code>ArrayList&lt;Product&gt;</code> and
<code>ArrayList&lt;Order&gt;</code>. Because the lists use the superclass types, one list can hold customers and employees
(or books, electronics and clothing) together. <code>Order</code> keeps an <code>ArrayList&lt;OrderItem&gt;</code>, and the
customer's shopping cart is a <code>List&lt;OrderItem&gt;</code> that lives only during the customer's session.</p>

<h3>5.5 Design decisions</h3>
<ul>
<li><b>OrderItem class.</b> The requirements say an order contains a "list of products". A plain
<code>ArrayList&lt;Product&gt;</code> cannot store <i>how many</i> units were bought, and if the product's price changed later,
old receipts would change too. <code>OrderItem</code> stores the product, the quantity and the unit price at the time of purchase.</li>
<li><b>Store class (service layer).</b> All data and business rules are in <code>Store</code>; the menu code in
<code>ConsoleApp</code> only reads input and prints output. This separation makes the code easier to read and would let us
replace the console with a GUI without changing the model.</li>
<li><b>Safe order placement.</b> <code>Store.placeOrder()</code> first checks the stock of <i>every</i> cart item and only then
reduces stock and creates the order, so an order is either completed fully or not at all.</li>
<li><b>Total amount consistency.</b> <code>Order.totalAmount</code> has no setter; it is recalculated by
<code>calculateTotal()</code> every time an item is added, so it always matches the items.</li>
<li><b>Unique e-mail.</b> <code>Store.addUser()</code> refuses a second user with the same e-mail, because e-mail is used to log in.</li>
<li><b>InputHelper.</b> All <code>Scanner</code> input goes through this class, which repeats the question until the value is
valid, so typing letters instead of a number does not throw an exception.</li>
<li><b>ID generation.</b> The store generates IDs with counters (<code>nextUserId</code>, ...), so IDs are unique and the user
does not need to type them.</li>
</ul>

<h3>5.6 Console menus</h3>
<table>
<tr><th>Main menu</th><th>Customer menu</th><th>Employee menu</th></tr>
<tr><td>1. Login<br>2. Register as customer<br>3. Browse products (guest)<br>0. Exit</td>
<td>1. List products<br>2. Add product to cart<br>3. View cart<br>4. Place order<br>5. View my orders<br>0. Logout</td>
<td>1. List products<br>2. Add product<br>3. Restock product<br>4. List users<br>5. Add user<br>6. View all orders<br>0. Logout</td></tr>
</table>

<h2 class="newpage">6. How to Run and Sample Session</h2>
<p>Open the project folder in IntelliJ IDEA (mark <code>src</code> as the sources root) and run <code>ecommerce.Main</code>,
or from the command line:</p>
<pre>javac -d out src/ecommerce/*.java src/ecommerce/*/*.java src/ecommerce/*/*/*.java
java -cp out ecommerce.Main</pre>
<p>Demo accounts loaded at start-up:</p>
<table>
<tr><th>Role</th><th>Email</th><th>Password</th></tr>
<tr><td>Employee (Manager)</td><td>admin@shop.kz</td><td>admin123</td></tr>
<tr><td>Customer</td><td>aida@mail.kz</td><td>1234</td></tr>
<tr><td>Customer</td><td>dan@mail.kz</td><td>1234</td></tr>
</table>
<p>Part of a real session (customer lists products, adds a mouse and a laptop to the cart and places the order):</p>
<pre>{html.escape(SAMPLE_RUN)}</pre>
<p>Tested scenarios: login with wrong password; registration with an existing e-mail; entering letters instead of a number;
adding more items than in stock (the program answers "Only 15 in stock."); placing an order with an empty cart;
employee adding a new book and viewing all orders.</p>

<h2>7. Conclusion</h2>
<p>A working console e-commerce system was designed and implemented in Java. The requirement analysis produced ten
classes in five packages. Two inheritance hierarchies (<code>User</code> &rarr; <code>Customer</code>/<code>Employee</code> and
<code>Product</code> &rarr; <code>Book</code>/<code>Electronics</code>/<code>Clothing</code>) remove duplicated code and, together with
abstract methods, give polymorphic behaviour when products and users are listed. Encapsulation with private fields and
validating setters keeps every object in a valid state, and <code>ArrayList</code> collections store all data. Separating the
model, the service (<code>Store</code>) and the user interface (<code>ConsoleApp</code>) keeps the code clear and easy to extend
&mdash; for example, adding a new product category only requires one new subclass of <code>Product</code>.</p>
<p>Possible improvements: saving data to files or a database, hashing passwords, searching/filtering products, and
order statuses (paid, shipped, delivered).</p>

<section class="annex">
<h2>Annex A &mdash; Source Code</h2>
<p>Git repository: <b>{html.escape(GIT_URL)}</b></p>
<p>The complete source code of the project is also included below.</p>
<p>Project structure:</p>
<pre>ECommerceSystem/
└── src/ecommerce/
    ├── Main.java
    ├── model/
    │   ├── user/     User.java, Customer.java, Employee.java
    │   ├── product/  Product.java, Book.java, Electronics.java, Clothing.java
    │   └── order/    Order.java, OrderItem.java
    ├── service/      Store.java
    └── ui/           ConsoleApp.java, InputHelper.java</pre>
{code_annex()}
</section>
</body></html>
"""

(DOCS / "report.html").write_text(page, encoding="utf-8")
print("wrote", DOCS / "report.html")
