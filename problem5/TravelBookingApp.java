package problem5;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Problem 5: Travel Booking with a Common Fee
 * Demonstrates the Template Method pattern using an abstract base class
 * where the common booking fee is defined in exactly one place.
 */
abstract class TravelBooking {
    // Single place of definition for booking fee: if it changes, only this line changes
    public static final double BOOKING_FEE = 50.00;

    private final String mode;
    private final double distanceKm;

    public TravelBooking(String mode, double distanceKm) {
        this.mode = mode;
        this.distanceKm = distanceKm;
    }

    public String getMode() {
        return mode;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    /**
     * Abstract method for mode-specific base fare calculation.
     */
    public abstract double calculateBaseFare();

    /**
     * Final template method combining the unique base fare with the common booking fee.
     */
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
        if (!scanner.hasNextInt()) {
            return;
        }

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
