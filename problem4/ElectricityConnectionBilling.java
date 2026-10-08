package problem4;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Problem 4: Electricity Connection Billing
 * Demonstrates an abstract base class with common unit state
 * and abstract billing method for polymorphic tariff calculation.
 */
abstract class ElectricityConnection {
    private final String type;
    private final double units;

    public ElectricityConnection(String type, double units) {
        this.type = type;
        this.units = units;
    }

    public String getType() {
        return type;
    }

    public double getUnits() {
        return units;
    }

    /**
     * Abstract method calculating the bill based on connection-specific tariff rules.
     */
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
        double amount = getUnits() * 6.0;
        return Math.max(amount, 1000.0);
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
        if (!scanner.hasNextInt()) {
            return;
        }

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
