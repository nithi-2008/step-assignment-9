# STEP Assignment 9: Object-Oriented Abstraction & Interfaces

This repository contains complete implementations and written solutions for **STEP SEM-3 · CodInClub | Powered by BridgeLabz — Category C (Assignment 9)**.

---

## Repository Structure

```text
step-assignment-9/
├── .gitignore                          # Standard Java ignore rules
├── README.md                           # Project overview & running instructions
├── SOLUTIONS.md                        # Complete QLA solutions (Parts A, B, and C)
│
├── problem1/
│   └── GardenPlotAreaReport.java       # Problem 1: Garden Plot Area Report
├── problem2/
│   └── WeeklyStaffPay.java             # Problem 2: Weekly Staff Pay
├── problem3/
│   └── LibraryLateFineCounter.java     # Problem 3: Library Late Fine Counter
├── problem4/
│   └── ElectricityConnectionBilling.java # Problem 4: Electricity Connection Billing
├── problem5/
│   └── TravelBookingApp.java           # Problem 5: Travel Booking with a Common Fee
│
└── tests/
    └── AssignmentTestSuite.java        # Automated End-to-End Test Suite
```

---

## Part A: Coding Problems Summary

| Problem | Title | Concept Demonstrated | Primary Rule / Design |
| :---: | :--- | :--- | :--- |
| **1** | **Garden Plot Area Report** | Polymorphism & OCP | Abstract base class `GardenPlot` with shape subclasses (`CirclePlot`, `RectanglePlot`, `TrianglePlot`). Report logic remains unchanged when new shapes are added. |
| **2** | **Weekly Staff Pay** | Abstract Class Instantiation Rule | Abstract class `Staff` prevents direct instantiation. Subclasses calculate pay: full-time (fixed), hourly (overtime $1.5\times$ above 40h), intern (stipend). |
| **3** | **Library Late Fine Counter** | Mandated Subclass Behavior | Abstract class `LibraryItem` with `calculateFine()`: Book ($2\times \text{days}$), DVD ($5\times \text{days}$, max 50), Magazine ($1\times \text{days}$). |
| **4** | **Electricity Connection Billing** | Shared State & Abstract Tariff | Common unit state in `ElectricityConnection`: Home slab tariff, Shop fixed charge, Factory minimum bill. |
| **5** | **Travel Booking with a Common Fee** | Template Method Pattern | Base class `TravelBooking` holds single definition of `BOOKING_FEE = 50.0`. Modifying the fee requires changing only one line. |

---

## How to Compile and Run

### Prerequisites
- JDK 8 or higher (tested and verified on OpenJDK / Oracle JDK 25)

### 1. Compile All Problems and Tests
From the root directory of the repository:
```bash
javac problem1/*.java problem2/*.java problem3/*.java problem4/*.java problem5/*.java tests/*.java
```

### 2. Run the Automated Test Suite
To verify all problems against sample inputs and edge cases:
```bash
java tests.AssignmentTestSuite
```

### 3. Run Individual Problems Interactively

- **Problem 1: Garden Plot Area Report**
  ```bash
  java problem1.GardenPlotAreaReport
  ```
  *Sample Input:*
  ```
  3
  CIRCLE Asha 5
  RECTANGLE Ravi 4 6
  TRIANGLE Neha 10 3
  ```

- **Problem 2: Weekly Staff Pay**
  ```bash
  java problem2.WeeklyStaffPay
  ```
  *Sample Input:*
  ```
  3
  FULLTIME Asha 12000
  HOURLY Ravi 45 200
  INTERN Neha 5000
  ```

- **Problem 3: Library Late Fine Counter**
  ```bash
  java problem3.LibraryLateFineCounter
  ```
  *Sample Input:*
  ```
  3
  BOOK Algebra 4
  DVD Inception 12
  MAGAZINE Sports 3
  ```

- **Problem 4: Electricity Connection Billing**
  ```bash
  java problem4.ElectricityConnectionBilling
  ```
  *Sample Input:*
  ```
  3
  HOME 150
  SHOP 90
  FACTORY 120
  ```

- **Problem 5: Travel Booking with a Common Fee**
  ```bash
  java problem5.TravelBookingApp
  ```
  *Sample Input:*
  ```
  3
  BUS 200
  TRAIN 300
  FLIGHT 500
  ```

---

## Part B: Quiz Key Summary

| Question | Topic | Answer | Key Reason |
| :---: | :--- | :---: | :--- |
| **Q1** | Abstraction Definition | **C** | Hides *how* operations work, exposes *what* it does |
| **Q2** | Abstract Class Rule | **C** | Cannot be instantiated directly with `new` |
| **Q3** | Unimplemented Abstract Method | **B** | Subclass must be declared abstract or compilation fails |
| **Q4** | Disallowed Modifier | **C** | `private` is illegal for abstract methods |
| **Q5** | Interface Fields | **C** | Implicitly `public static final` |
| **Q6** | Multiple Interface Support | **C** | A class can implement any number of interfaces |
| **Q7** | Overriding Access Modifier | **D** | Implemented interface methods must be `public` |
| **Q8** | Single Class vs Multiple Interfaces | **C** | Interfaces carry no instance state, avoiding diamond ambiguity |
| **Q9** | Conflicting Default Methods | **D** | Compile-time error until class overrides the conflicting method |
| **Q10** | Constructors in Abstract Classes | **B** | Run via constructor chaining (`super()`) when subclass is instantiated |

*Detailed explanations for each question are available in [SOLUTIONS.md](SOLUTIONS.md).*

---

## Part C: Concept Questions Summary

- **Question 1: Abstraction Real-Life Example**: Automated Teller Machine (ATM) showing simple UI actions while hiding encryption, banking protocols, and mechanical sensors.
- **Question 2: Abstraction vs Encapsulation**: Encapsulation hides state (data protection); abstraction hides complexity (design contract).
- **Question 3: Abstract Class Constructors**: Shared state initialization executed via constructor chaining (`super()`) upon subclass instantiation.
- **Question 4: Prohibited Modifiers (`private`, `static`, `final`)**: Explained through visibility, static binding vs dynamic dispatch, and override prohibition.
- **Question 5: The Diamond Problem**: Inherited state and method ambiguity, resolved in Java through single class inheritance and explicit interface collision resolution.

*Read the full essay answers in [SOLUTIONS.md](SOLUTIONS.md).*
