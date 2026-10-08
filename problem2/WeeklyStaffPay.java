package problem2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Problem 2: Weekly Staff Pay
 * Demonstrates Abstract Classes to prevent generic instantiation
 * and enforce contract fulfillment for staff payment calculation.
 */
abstract class Staff {
    private final String name;

    public Staff(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Abstract method enforcing subclasses to implement their specific pay rule.
     */
    public abstract double calculatePay();
}

class FullTimeStaff extends Staff {
    private final double weeklySalary;

    public FullTimeStaff(String name, double weeklySalary) {
        super(name);
        this.weeklySalary = weeklySalary;
    }

    public double getWeeklySalary() {
        return weeklySalary;
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

    public double getHours() {
        return hours;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public double calculatePay() {
        if (hours <= 40) {
            return hours * rate;
        } else {
            double regularPay = 40.0 * rate;
            double overtimePay = (hours - 40.0) * 1.5 * rate;
            return regularPay + overtimePay;
        }
    }
}

class InternStaff extends Staff {
    private final double stipend;

    public InternStaff(String name, double stipend) {
        super(name);
        this.stipend = stipend;
    }

    public double getStipend() {
        return stipend;
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
        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        List<Staff> staffList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            String name = scanner.next();

            switch (type.toUpperCase()) {
                case "FULLTIME":
                    double weeklySalary = scanner.nextDouble();
                    staffList.add(new FullTimeStaff(name, weeklySalary));
                    break;
                case "HOURLY":
                    double hours = scanner.nextDouble();
                    double rate = scanner.nextDouble();
                    staffList.add(new HourlyStaff(name, hours, rate));
                    break;
                case "INTERN":
                    double stipend = scanner.nextDouble();
                    staffList.add(new InternStaff(name, stipend));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown staff type: " + type);
            }
        }

        printPayroll(staffList);
        scanner.close();
    }
}
