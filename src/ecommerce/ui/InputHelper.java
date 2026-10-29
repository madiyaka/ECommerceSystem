package ecommerce.ui;

import java.util.Scanner;

/**
 * Small wrapper around Scanner that keeps asking until the input is valid,
 * so a typo never crashes the program.
 */
public class InputHelper {

    private Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String readNonEmpty(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("  Value cannot be empty.");
        }
    }

    public int readInt(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a whole number.");
            }
        }
    }

    public int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("  Number must be greater than 0.");
        }
    }

    public double readNonNegativeDouble(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                double number = Double.parseDouble(value);
                if (number >= 0) {
                    return number;
                }
                System.out.println("  Number cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a number (e.g. 1500 or 99.90).");
            }
        }
    }
}
