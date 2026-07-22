import java.util.Scanner;

public class Utils {
    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("Input cannot be empty.");
        }
    }

    // Reads a date in strict YYYY-MM-DD format, re-prompting until valid.
    // Validates: correct format, month 1-12, and day valid for that month/year.
    public static String readValidDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();

            String[] parts = line.split("-");
            if (parts.length != 3) {
                System.out.println("Invalid Date Format! Please use YYYY-MM-DD.");
                continue;
            }

            try {
                if (parts[0].length() != 4 || parts[1].length() != 2 || parts[2].length() != 2) {
                    System.out.println("Invalid Date Format! Please use YYYY-MM-DD.");
                    continue;
                }

                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);

                if (month < 1 || month > 12) {
                    System.out.println("Invalid Month! Month should be between 1 and 12.");
                    continue;
                }

                int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                boolean leapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                int maxDay = (month == 2 && leapYear) ? 29 : daysInMonth[month - 1];

                if (day < 1 || day > maxDay) {
                    System.out.println("Invalid Day! Day should be between 1 and " + maxDay + " for the given month.");
                    continue;
                }

                return line;

            } catch (NumberFormatException e) {
                System.out.println("Invalid Date! Please use numeric YYYY-MM-DD format.");
            }
        }
    }
}