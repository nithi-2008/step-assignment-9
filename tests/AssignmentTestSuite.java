package tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class AssignmentTestSuite {

    private static int testsPassed = 0;
    private static int testsTotal = 0;

    private static void assertEquals(String testName, String expected, String actual) {
        testsTotal++;
        // Normalize line separators and whitespace
        String normalizedExpected = expected.replace("\r\n", "\n").trim();
        String normalizedActual = actual.replace("\r\n", "\n").trim();

        if (normalizedExpected.equals(normalizedActual)) {
            System.out.println("  [PASS] " + testName);
            testsPassed++;
        } else {
            System.err.println("  [FAIL] " + testName);
            System.err.println("    Expected:\n" + normalizedExpected);
            System.err.println("    Actual:\n" + normalizedActual);
        }
    }

    private static String runMainWithInput(Class<?> clazz, String input) throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        try {
            System.setIn(inContent);
            System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));

            Method mainMethod = clazz.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[]{});

            return outContent.toString(StandardCharsets.UTF_8);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  Running STEP Assignment 9 Automated Test Suite ");
        System.out.println("=================================================");

        try {
            // Problem 1: Garden Plot Area Report
            System.out.println("\nTesting Problem 1: Garden Plot Area Report...");
            String p1Input = "3\nCIRCLE Asha 5\nRECTANGLE Ravi 4 6\nTRIANGLE Neha 10 3\n";
            String p1Expected = "Asha (CIRCLE): 78.54\nRavi (RECTANGLE): 24.00\nNeha (TRIANGLE): 15.00\nTotal Area: 117.54";
            String p1Actual = runMainWithInput(problem1.GardenPlotAreaReport.class, p1Input);
            assertEquals("Problem 1 - Sample Input", p1Expected, p1Actual);

            // Problem 2: Weekly Staff Pay
            System.out.println("\nTesting Problem 2: Weekly Staff Pay...");
            String p2Input = "3\nFULLTIME Asha 12000\nHOURLY Ravi 45 200\nINTERN Neha 5000\n";
            String p2Expected = "Asha: 12000.00\nRavi: 9500.00\nNeha: 5000.00\nTotal Payroll: 26500.00";
            String p2Actual = runMainWithInput(problem2.WeeklyStaffPay.class, p2Input);
            assertEquals("Problem 2 - Sample Input", p2Expected, p2Actual);

            // Problem 2: Edge case - Hourly with <= 40 hours
            String p2InputEdge = "2\nHOURLY John 40 100\nHOURLY Jane 35 100\n";
            String p2ExpectedEdge = "John: 4000.00\nJane: 3500.00\nTotal Payroll: 7500.00";
            String p2ActualEdge = runMainWithInput(problem2.WeeklyStaffPay.class, p2InputEdge);
            assertEquals("Problem 2 - Hourly without Overtime", p2ExpectedEdge, p2ActualEdge);

            // Problem 3: Library Late Fine Counter
            System.out.println("\nTesting Problem 3: Library Late Fine Counter...");
            String p3Input = "3\nBOOK Algebra 4\nDVD Inception 12\nMAGAZINE Sports 3\n";
            String p3Expected = "Algebra: 8.00\nInception: 50.00\nSports: 3.00\nTotal Fines: 61.00";
            String p3Actual = runMainWithInput(problem3.LibraryLateFineCounter.class, p3Input);
            assertEquals("Problem 3 - Sample Input", p3Expected, p3Actual);

            // Problem 3: Edge case - DVD below max cap (e.g. 5 days * 5 = 25)
            String p3InputEdge = "1\nDVD Matrix 5\n";
            String p3ExpectedEdge = "Matrix: 25.00\nTotal Fines: 25.00";
            String p3ActualEdge = runMainWithInput(problem3.LibraryLateFineCounter.class, p3InputEdge);
            assertEquals("Problem 3 - DVD below cap", p3ExpectedEdge, p3ActualEdge);

            // Problem 4: Electricity Connection Billing
            System.out.println("\nTesting Problem 4: Electricity Connection Billing...");
            String p4Input = "3\nHOME 150\nSHOP 90\nFACTORY 120\n";
            String p4Expected = "HOME: 850.00\nSHOP: 820.00\nFACTORY: 1000.00\nTotal: 2670.00";
            String p4Actual = runMainWithInput(problem4.ElectricityConnectionBilling.class, p4Input);
            assertEquals("Problem 4 - Sample Input", p4Expected, p4Actual);

            // Problem 4: Edge case - Factory above minimum bill (e.g. 200 units * 6 = 1200)
            String p4InputEdge = "2\nHOME 80\nFACTORY 200\n";
            String p4ExpectedEdge = "HOME: 400.00\nFACTORY: 1200.00\nTotal: 1600.00";
            String p4ActualEdge = runMainWithInput(problem4.ElectricityConnectionBilling.class, p4InputEdge);
            assertEquals("Problem 4 - Home <= 100 & Factory > 1000", p4ExpectedEdge, p4ActualEdge);

            // Problem 5: Travel Booking with a Common Fee
            System.out.println("\nTesting Problem 5: Travel Booking with a Common Fee...");
            String p5Input = "3\nBUS 200\nTRAIN 300\nFLIGHT 500\n";
            String p5Expected = "BUS: 450.00\nTRAIN: 500.00\nFLIGHT: 4550.00";
            String p5Actual = runMainWithInput(problem5.TravelBookingApp.class, p5Input);
            assertEquals("Problem 5 - Sample Input", p5Expected, p5Actual);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n=================================================");
        System.out.printf("  Summary: %d / %d Tests Passed%n", testsPassed, testsTotal);
        System.out.println("=================================================");

        if (testsPassed != testsTotal) {
            System.exit(1);
        }
    }
}
