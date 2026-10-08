# STEP Assignment 9 — Complete Solutions (QLA)
**Program:** STEP SEM-3 · CodInClub | Powered by BridgeLabz  
**Category:** Category C (Object-Oriented Programming: Abstraction & Interfaces)

---

## Table of Contents
1. [Part A — Coding Problems](#part-a--coding-problems)
   - [Problem 1: Garden Plot Area Report](#problem-1-garden-plot-area-report)
   - [Problem 2: Weekly Staff Pay](#problem-2-weekly-staff-pay)
   - [Problem 3: Library Late Fine Counter](#problem-3-library-late-fine-counter)
   - [Problem 4: Electricity Connection Billing](#problem-4-electricity-connection-billing)
   - [Problem 5: Travel Booking with a Common Fee](#problem-5-travel-booking-with-a-common-fee)
2. [Part B — Quiz Solutions](#part-b--quiz-solutions)
3. [Part C — Concept Questions](#part-c--concept-questions)

---

# Part A — Coding Problems

---

### Problem 1: Garden Plot Area Report

#### Problem Summary
A community garden rents out plots of three shapes: circular, rectangular, and triangular. Each plot has an owner and its area is calculated using a specific formula. The report must print each plot's owner, shape, and area, followed by the total area rented. Adding a new plot shape later must not require changes to the reporting code.

#### Key OOP Design
- **Abstract Base Class:** `GardenPlot` encapsulates common attributes (`shape`, `owner`) and defines the abstract method `calculateArea()`.
- **Subclasses:** `CirclePlot` ($\pi \times r^2$), `RectanglePlot` ($l \times w$), `TrianglePlot` ($\frac{1}{2} \times b \times h$).
- **Open-Closed Principle (OCP):** `GardenPlotAreaReport.printReport()` iterates over `GardenPlot` polymorphically. New shapes can be added by creating new subclasses without modifying the report generator.

#### Java Implementation
```java
package problem1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

abstract class GardenPlot {
    private final String shape;
    private final String owner;

    public GardenPlot(String shape, String owner) {
        this.shape = shape;
        this.owner = owner;
    }

    public String getShape() { return shape; }
    public String getOwner() { return owner; }
    public abstract double calculateArea();
}

class CirclePlot extends GardenPlot {
    private final double radius;

    public CirclePlot(String owner, double radius) {
        super("CIRCLE", owner);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class RectanglePlot extends GardenPlot {
    private final double length;
    private final double width;

    public RectanglePlot(String owner, double length, double width) {
        super("RECTANGLE", owner);
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }
}

class TrianglePlot extends GardenPlot {
    private final double base;
    private final double height;

    public TrianglePlot(String owner, double base, double height) {
        super("TRIANGLE", owner);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

public class GardenPlotAreaReport {
    public static void printReport(List<GardenPlot> plots) {
        double totalArea = 0.0;
        for (GardenPlot plot : plots) {
            double area = plot.calculateArea();
            totalArea += area;
            System.out.printf(Locale.US, "%s (%s): %.2f%n", plot.getOwner(), plot.getShape(), area);
        }
        System.out.printf(Locale.US, "Total Area: %.2f%n", totalArea);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<GardenPlot> plots = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String shape = scanner.next();
            String owner = scanner.next();

            switch (shape.toUpperCase()) {
                case "CIRCLE":
                    plots.add(new CirclePlot(owner, scanner.nextDouble()));
                    break;
                case "RECTANGLE":
                    plots.add(new RectanglePlot(owner, scanner.nextDouble(), scanner.nextDouble()));
                    break;
                case "TRIANGLE":
                    plots.add(new TrianglePlot(owner, scanner.nextDouble(), scanner.nextDouble()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown shape: " + shape);
            }
        }

        printReport(plots);
        scanner.close();
    }
}
```

---

### Problem 2: Weekly Staff Pay

#### Problem Summary
A company pays three kinds of staff:
1. **Full-time staff:** Fixed weekly salary.
2. **Hourly staff:** Standard rate for first 40 hours; $1.5 \times \text{rate}$ for overtime hours ($> 40$).
3. **Interns:** Fixed weekly stipend.
A generic "staff member" with no pay rule must never be directly instantiated.

#### Key OOP Design
- **Abstract Base Class:** `Staff` is declared `abstract` to prohibit direct instantiation (`new Staff(...)` is disallowed).
- **Subclasses:** `FullTimeStaff`, `HourlyStaff`, `InternStaff` provide concrete implementations of `calculatePay()`.
- **Polymorphism:** Payroll calculation aggregates total pay without caring about specific staff types.

#### Java Implementation
```java
package problem2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

abstract class Staff {
    private final String name;

    public Staff(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public abstract double calculatePay();
}

class FullTimeStaff extends Staff {
    private final double weeklySalary;

    public FullTimeStaff(String name, double weeklySalary) {
        super(name);
        this.weeklySalary = weeklySalary;
    }

    @Override
    public double calculatePay() {
        return weeklySalary;
    }
}

class HourlyStaff extends Staff {
    private final double hours;
    private final double rate;

    public HourlyStaff(String name, double hours, double rate) {
        super(name);
        this.hours = hours;
        this.rate = rate;
    }

    @Override
    public double calculatePay() {
        if (hours <= 40) {
            return hours * rate;
        } else {
            return (40.0 * rate) + ((hours - 40.0) * 1.5 * rate);
        }
    }
}

class InternStaff extends Staff {
    private final double stipend;

    public InternStaff(String name, double stipend) {
        super(name);
        this.stipend = stipend;
    }

    @Override
    public double calculatePay() {
        return stipend;
    }
}

public class WeeklyStaffPay {
    public static void printPayroll(List<Staff> staffMembers) {
        double totalPayroll = 0.0;
        for (Staff staff : staffMembers) {
            double pay = staff.calculatePay();
            totalPayroll += pay;
            System.out.printf(Locale.US, "%s: %.2f%n", staff.getName(), pay);
        }
        System.out.printf(Locale.US, "Total Payroll: %.2f%n", totalPayroll);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<Staff> staffList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            String name = scanner.next();

            switch (type.toUpperCase()) {
                case "FULLTIME":
                    staffList.add(new FullTimeStaff(name, scanner.nextDouble()));
                    break;
                case "HOURLY":
                    staffList.add(new HourlyStaff(name, scanner.nextDouble(), scanner.nextDouble()));
                    break;
                case "INTERN":
                    staffList.add(new InternStaff(name, scanner.nextDouble()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown staff type: " + type);
            }
        }

        printPayroll(staffList);
        scanner.close();
    }
}
```

---

### Problem 3: Library Late Fine Counter

#### Problem Summary
Calculate late return fines for library items:
- **Books:** 2 per day late.
- **DVDs:** 5 per day late, capped at a maximum of 50.
- **Magazines:** 1 per day late.

#### Key OOP Design
- **Abstract Base Class:** `LibraryItem` captures common fields (`title`, `daysLate`) and mandates `calculateFine()`.
- **Subclasses:** `Book`, `Dvd` (with `Math.min(fine, 50.0)` cap), and `Magazine`.

#### Java Implementation
```java
package problem3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

abstract class LibraryItem {
    private final String title;
    private final int daysLate;

    public LibraryItem(String title, int daysLate) {
        this.title = title;
        this.daysLate = daysLate;
    }

    public String getTitle() { return title; }
    public int getDaysLate() { return daysLate; }
    public abstract double calculateFine();
}

class Book extends LibraryItem {
    public Book(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        return getDaysLate() * 2.0;
    }
}

class Dvd extends LibraryItem {
    public Dvd(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        return Math.min(getDaysLate() * 5.0, 50.0);
    }
}

class Magazine extends LibraryItem {
    public Magazine(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        return getDaysLate() * 1.0;
    }
}

public class LibraryLateFineCounter {
    public static void printFineReport(List<LibraryItem> items) {
        double totalFines = 0.0;
        for (LibraryItem item : items) {
            double fine = item.calculateFine();
            totalFines += fine;
            System.out.printf(Locale.US, "%s: %.2f%n", item.getTitle(), fine);
        }
        System.out.printf(Locale.US, "Total Fines: %.2f%n", totalFines);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<LibraryItem> items = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            String title = scanner.next();
            int daysLate = scanner.nextInt();

            switch (type.toUpperCase()) {
                case "BOOK":
                    items.add(new Book(title, daysLate));
                    break;
                case "DVD":
                    items.add(new Dvd(title, daysLate));
                    break;
                case "MAGAZINE":
                    items.add(new Magazine(title, daysLate));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown item type: " + type);
            }
        }

        printFineReport(items);
        scanner.close();
    }
}
```

---

### Problem 4: Electricity Connection Billing

#### Problem Summary
Calculate bills for different connection types based on units consumed:
- **Home:** 5 per unit for first 100 units, and 7 per unit for every unit above 100.
- **Shop:** 8 per unit plus a fixed charge of 100.
- **Factory:** 6 per unit, subject to a minimum bill of 1000.

#### Key OOP Design
- **Abstract Base Class:** `ElectricityConnection` stores common `units` and connection `type`, exposing abstract `calculateBill()`.
- **Subclasses:** `HomeConnection`, `ShopConnection`, `FactoryConnection`.

#### Java Implementation
```java
package problem4;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

abstract class ElectricityConnection {
    private final String type;
    private final double units;

    public ElectricityConnection(String type, double units) {
        this.type = type;
        this.units = units;
    }

    public String getType() { return type; }
    public double getUnits() { return units; }
    public abstract double calculateBill();
}

class HomeConnection extends ElectricityConnection {
    public HomeConnection(double units) {
        super("HOME", units);
    }

    @Override
    public double calculateBill() {
        double u = getUnits();
        if (u <= 100.0) {
            return u * 5.0;
        } else {
            return (100.0 * 5.0) + ((u - 100.0) * 7.0);
        }
    }
}

class ShopConnection extends ElectricityConnection {
    public ShopConnection(double units) {
        super("SHOP", units);
    }

    @Override
    public double calculateBill() {
        return (getUnits() * 8.0) + 100.0;
    }
}

class FactoryConnection extends ElectricityConnection {
    public FactoryConnection(double units) {
        super("FACTORY", units);
    }

    @Override
    public double calculateBill() {
        return Math.max(getUnits() * 6.0, 1000.0);
    }
}

public class ElectricityConnectionBilling {
    public static void printBillingReport(List<ElectricityConnection> connections) {
        double totalBill = 0.0;
        for (ElectricityConnection conn : connections) {
            double bill = conn.calculateBill();
            totalBill += bill;
            System.out.printf(Locale.US, "%s: %.2f%n", conn.getType(), bill);
        }
        System.out.printf(Locale.US, "Total: %.2f%n", totalBill);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<ElectricityConnection> connections = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            double units = scanner.nextDouble();

            switch (type.toUpperCase()) {
                case "HOME":
                    connections.add(new HomeConnection(units));
                    break;
                case "SHOP":
                    connections.add(new ShopConnection(units));
                    break;
                case "FACTORY":
                    connections.add(new FactoryConnection(units));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown connection type: " + type);
            }
        }

        printBillingReport(connections);
        scanner.close();
    }
}
```

---

### Problem 5: Travel Booking with a Common Fee

#### Problem Summary
Calculates fares for travel modes (BUS, TRAIN, FLIGHT). Every booking incurs an additional common booking fee of 50. The booking fee must be defined in **one place only** so that changing it requires altering only a single line of code.

#### Key OOP Design
- **Template Method Pattern:** `TravelBooking` defines `BOOKING_FEE = 50.00` as a constant and provides a `final` method `calculateTotalFare() = calculateBaseFare() + BOOKING_FEE`.
- **Subclasses:** `BusBooking`, `TrainBooking`, `FlightBooking` only implement `calculateBaseFare()`. If the booking fee changes, only one line in `TravelBooking` changes.

#### Java Implementation
```java
package problem5;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

abstract class TravelBooking {
    // Single point of definition for the booking fee
    public static final double BOOKING_FEE = 50.00;

    private final String mode;
    private final double distanceKm;

    public TravelBooking(String mode, double distanceKm) {
        this.mode = mode;
        this.distanceKm = distanceKm;
    }

    public String getMode() { return mode; }
    public double getDistanceKm() { return distanceKm; }

    public abstract double calculateBaseFare();

    public final double calculateTotalFare() {
        return calculateBaseFare() + BOOKING_FEE;
    }
}

class BusBooking extends TravelBooking {
    public BusBooking(double distanceKm) {
        super("BUS", distanceKm);
    }

    @Override
    public double calculateBaseFare() {
        return getDistanceKm() * 2.0;
    }
}

class TrainBooking extends TravelBooking {
    public TrainBooking(double distanceKm) {
        super("TRAIN", distanceKm);
    }

    @Override
    public double calculateBaseFare() {
        return getDistanceKm() * 1.5;
    }
}

class FlightBooking extends TravelBooking {
    public FlightBooking(double distanceKm) {
        super("FLIGHT", distanceKm);
    }

    @Override
    public double calculateBaseFare() {
        return 2500.0 + (getDistanceKm() * 4.0);
    }
}

public class TravelBookingApp {
    public static void printBookingReport(List<TravelBooking> bookings) {
        for (TravelBooking booking : bookings) {
            System.out.printf(Locale.US, "%s: %.2f%n", booking.getMode(), booking.calculateTotalFare());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<TravelBooking> bookings = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String mode = scanner.next();
            double distanceKm = scanner.nextDouble();

            switch (mode.toUpperCase()) {
                case "BUS":
                    bookings.add(new BusBooking(distanceKm));
                    break;
                case "TRAIN":
                    bookings.add(new TrainBooking(distanceKm));
                    break;
                case "FLIGHT":
                    bookings.add(new FlightBooking(distanceKm));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown travel mode: " + mode);
            }
        }

        printBookingReport(bookings);
        scanner.close();
    }
}
```

---

# Part B — Quiz Solutions

| Question | Question Text | Correct Option | Correct Answer Text |
| :---: | :--- | :---: | :--- |
| **Q1** | Which of the following best describes abstraction in object-oriented programming? | **C** | Hiding how an object performs its operations and showing only what it does |
| **Q2** | Which statement about an abstract class in Java is true? | **C** | It cannot be instantiated directly |
| **Q3** | If a concrete class extends an abstract class but fails to implement an inherited abstract method, what is the consequence? | **B** | The class must be declared abstract, or the code will not compile |
| **Q4** | Which of the following modifiers is NOT allowed for an abstract method in Java? | **C** | private |
| **Q5** | Fields declared within a Java interface are implicitly: | **C** | public, static, and final |
| **Q6** | How many interfaces can a single Java class implement? | **C** | Any number of interfaces |
| **Q7** | If a class implements an interface, what access modifier must it use for the implemented methods? | **D** | public |
| **Q8** | Why does Java allow a class to implement multiple interfaces but only extend a single class? | **C** | Interfaces carry no instance state, avoiding the 'diamond problem' ambiguity with conflicting data inheritance |
| **Q9** | What happens if a class implements two interfaces that both provide a default method with the same signature, and the class does not override this method? | **D** | A compile-time error occurs until the class overrides the method |
| **Q10** | Which of the following is true regarding constructors in Java? | **B** | Abstract classes can have constructors that run when a subclass object is created |

### In-Depth Quiz Explanations

1. **Q1 — Option C:** Abstraction emphasizes what an object does from an external viewpoint while encapsulating or concealing internal algorithmic complexity. Option B corresponds to encapsulation.
2. **Q2 — Option C:** In Java, declaring a class `abstract` immediately revokes direct instantiation via `new`. An abstract class can have zero, one, or multiple abstract methods; regardless, direct instantiation remains prohibited.
3. **Q3 — Option B:** Subclasses must fulfill the contract established by their parent. If an abstract method is not overridden, the subclass retains an abstract method and must itself be marked `abstract`.
4. **Q4 — Option C:** Abstract methods exist specifically to be overridden by subclasses. Declaring an abstract method `private` prevents subclasses from seeing or overriding it, which causes a compilation error.
5. **Q5 — Option C:** By Java Language Specification (JLS §9.3), all fields in an interface are implicitly constants: `public static final`.
6. **Q6 — Option C:** Java supports multiple inheritance of type via interfaces (`implements InterfaceA, InterfaceB, ...`). There is no upper limit.
7. **Q7 — Option D:** Interface methods are implicitly `public`. Java overriding rules dictate that overriding methods cannot reduce accessibility; hence, implementing classes must declare the methods `public`.
8. **Q8 — Option C:** Disallowing multiple class inheritance prevents state collisions and ambiguous field resolution (the diamond problem of instance state). Since interfaces cannot define instance variables, multiple interface implementation remains safe.
9. **Q9 — Option D:** When two interfaces offer competing default implementations with identical signatures, Java requires the implementing class to explicitly override the method and resolve the conflict.
10. **Q10 — Option B:** Abstract classes can have constructors to initialize inherited fields. These constructors are called via `super()` during constructor chaining whenever a concrete subclass instance is created.

---

# Part C — Concept Questions

---

### Question 1
**Prompt:**  
*Explain abstraction in your own words. Provide a real-life example, different from those mentioned in the material, detailing what is shown to the user and what details are hidden.*

**Answer:**  
Abstraction is the fundamental object-oriented design principle of exposing only the essential capabilities and interactive interface of an entity while completely hiding the underlying complexity, mechanics, and internal algorithms. It provides a simple, clean contract for users to interact with a system without needing to know or worry about how that system accomplishes the task internally.

**Real-Life Example: An Automated Teller Machine (ATM)**
- **What is shown to the user (The Abstraction / Interface):**
  - A card slot and PIN entry screen.
  - Simple push-button actions: *"Withdraw Cash"*, *"Deposit Check"*, *"View Balance"*.
  - A numeric keypad to input amounts, and a cash dispensing slot with a receipt printer.
- **What is hidden from the user (The Implementation Details):**
  - Hardware driver commands controlling physical rollers, motorized bill counters, and optical thickness sensors.
  - Network-layer TCP/IP socket connections and ISO 8583 financial message framing.
  - Cryptographic algorithms (e.g., AES/RSA) securing PIN transmission to bank switch networks.
  - SQL transactions executing across distributed database clusters handling ACID updates, account balances, and automated rollback logs.

---

### Question 2
**Prompt:**  
*Describe the primary difference between abstraction and encapsulation. Explain how these two concepts work together within a single class to manage complexity and data protection.*

**Answer:**  
The primary distinction between abstraction and encapsulation lies in their intent and scope:
- **Abstraction is complexity hiding (Design Level):** It focuses on *what* an object does rather than *how* it does it. It hides internal implementation logic and provides high-level contracts (using abstract classes and interfaces).
- **Encapsulation is data hiding and containment (Implementation Level):** It binds state (fields) and behavior (methods) together into a single cohesive unit (a class) while restricting direct external access to that state (using access modifiers like `private`).

**How they work together in a single class:**
Encapsulation establishes a secure perimeter around the object's internal data using `private` fields and provides controlled mutation through methods, guaranteeing that internal state remains valid and immune to external corruption. Abstraction then sits on top of this encapsulated state, presenting a clean public API (`public double calculatePay()`) that allows clients to achieve high-level goals without understanding or touching the underlying variables and calculation steps. Encapsulation protects the data; abstraction manages complexity.

---

### Question 3
**Prompt:**  
*An abstract class cannot be instantiated, yet it can define a constructor. Explain why an abstract class might have a constructor and describe when this constructor is executed.*

**Answer:**  
**Why an abstract class defines a constructor:**  
Even though an abstract class cannot be instantiated on its own with the `new` operator, it frequently defines instance fields that are shared by all subclasses (e.g., an `owner` name or an employee `id`). An abstract class constructor ensures that:
1. Shared state is initialized in a centralized location, preventing duplicate initialization code across subclasses.
2. Invariants, validation checks, and non-null constraints are reliably enforced at the moment any subclass instance is instantiated.

**When the constructor is executed:**  
The abstract class constructor is executed during **constructor chaining** whenever a concrete subclass instance is instantiated. When `new SubClass(...)` is called:
1. The subclass constructor's first statement executes `super(...)` (either explicitly or via Java's implicit default `super()`).
2. Execution flows directly to the abstract class constructor, which initializes its fields.
3. Once the abstract class constructor finishes, control returns to complete the subclass constructor.

---

### Question 4
**Prompt:**  
*Explain why an abstract method cannot be declared with the private, static, or final modifiers. Provide a reason for each of these restrictions.*

**Answer:**  
An abstract method has no body and exists solely to establish a contract that must be overridden and implemented by a subclass. The modifiers `private`, `static`, and `final` each break this contract for distinct technical reasons:

1. **`private` Restriction:**  
   `private` members are accessible only within the declaring class and are strictly invisible to subclasses. Because a subclass cannot see or override a `private` method, an abstract method marked `private` could never be implemented, resulting in an immediate compile-time contradiction.
2. **`static` Restriction:**  
   `static` methods belong to the class itself and are resolved at compile-time via static binding (method hiding), rather than dynamic runtime dispatch. Abstract methods rely on runtime polymorphism (dynamic method lookup via virtual method tables) to execute subclass-specific overrides.
3. **`final` Restriction:**  
   The `final` keyword in Java explicitly forbids a method from ever being overridden. Because an abstract method *must* be overridden to have an implementation, declaring a method simultaneously `abstract` and `final` is an impossible logical contradiction.

---

### Question 5
**Prompt:**  
*What is the 'diamond problem' in the context of inheritance? Explain how Java's design, allowing a class to implement multiple interfaces but extend only one class, addresses or avoids this problem.*

**Answer:**  
**The Diamond Problem:**  
The diamond problem arises in multiple class inheritance when a class $D$ inherits from two distinct classes $B$ and $C$, both of which inherit from a common ancestor $A$ ($A \leftarrow B, C \leftarrow D$). If both $B$ and $C$ override a method or inherit mutable instance state from $A$, two major ambiguities occur:
1. **State ambiguity:** Does $D$ inherit two distinct copies of $A$'s fields, and which constructor initializes them?
2. **Behavioral ambiguity:** When $D$ calls the inherited method, which version ($B$'s or $C$'s) should be invoked?

**How Java Addresses and Avoids This:**
1. **Single Class Inheritance:** Java permits a class to extend only a single superclass (`extends ClassName`). This eliminates multiple inheritance of instance state, guaranteeing a single, unambiguous path of field inheritance and constructor execution.
2. **Multiple Interface Implementation:** Java allows multiple interface implementation because interfaces historically carried **no instance state** (no non-static fields).
3. **Java 8 Default Method Ambiguity Resolution:** With the introduction of default methods in interfaces, if two interfaces implement a method with the identical signature, the Java compiler refuses to guess. It throws a compile-time error and forces the implementing class to explicitly override the conflicting method (e.g., calling `InterfaceA.super.method()`), thereby eliminating any runtime ambiguity.
