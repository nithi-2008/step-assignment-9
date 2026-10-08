package problem1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Problem 1: Garden Plot Area Report
 * Demonstrates Abstraction and Polymorphism using an abstract base class
 * representing garden plots with shape-specific area calculations.
 */
abstract class GardenPlot {
    private final String shape;
    private final String owner;

    public GardenPlot(String shape, String owner) {
        this.shape = shape;
        this.owner = owner;
    }

    public String getShape() {
        return shape;
    }

    public String getOwner() {
        return owner;
    }

    /**
     * Abstract method enforcing subclasses to implement their specific area formula.
     */
    public abstract double calculateArea();
}

class CirclePlot extends GardenPlot {
    private final double radius;

    public CirclePlot(String owner, double radius) {
        super("CIRCLE", owner);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
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

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
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

    public double getBase() {
        return base;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

public class GardenPlotAreaReport {

    /**
     * Generates and prints the report. Adding new shapes requires no changes to this reporting logic.
     */
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
        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        List<GardenPlot> plots = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String shape = scanner.next();
            String owner = scanner.next();

            switch (shape.toUpperCase()) {
                case "CIRCLE":
                    double radius = scanner.nextDouble();
                    plots.add(new CirclePlot(owner, radius));
                    break;
                case "RECTANGLE":
                    double length = scanner.nextDouble();
                    double width = scanner.nextDouble();
                    plots.add(new RectanglePlot(owner, length, width));
                    break;
                case "TRIANGLE":
                    double base = scanner.nextDouble();
                    double height = scanner.nextDouble();
                    plots.add(new TrianglePlot(owner, base, height));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown shape type: " + shape);
            }
        }

        printReport(plots);
        scanner.close();
    }
}
