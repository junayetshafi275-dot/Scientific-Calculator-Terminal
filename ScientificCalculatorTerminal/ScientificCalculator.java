import java.util.Scanner;
import java.util.ArrayList;

public class ScientificCalculator {

    static final double PHI     = (1 + Math.sqrt(5)) / 2;
    static final double EULER_M = 0.57721566490153286060;

    // ===========================
    // LOGIN
    // ===========================

    static String savedUser    = "junayet";
    static String savedPass    = "1234";
    static String loggedInUser = "";

    static boolean login(Scanner input) {
        int tries = 0;
        System.out.println("==============================");
        System.out.println("   Scientific Calculator     ");
        System.out.println("==============================");
        System.out.println("Please login to continue.\n");

        while (tries < 3) {
            System.out.print("Username: ");
            String user = input.nextLine().trim().toLowerCase();

            System.out.print("Password: ");
            String pass = input.nextLine().trim();

            if (user.equals(savedUser) && pass.equals(savedPass)) {
                loggedInUser = user;
                System.out.println("\nHey " + loggedInUser + "! You are logged in.\n");
                return true;
            }

            tries++;
            int left = 3 - tries;
            if (left > 0) System.out.println("Wrong! " + left + " attempt(s) left.\n");
        }
        return false;
    }

    // ===========================
    // SAFE INPUT HELPERS
    // ===========================

    static double lastAnswer = 0;

    static double readDouble(Scanner input) {
        while (true) {
            String val = input.nextLine().trim();

            if (val.isEmpty()) {
                System.out.print("  Please enter a number: ");
                continue;
            }

            if (val.equalsIgnoreCase("ans")) {
                if (Double.isNaN(lastAnswer)) {
                    System.out.println("  Warning: last answer is undefined. Enter a new number: ");
                    continue;
                }
                System.out.println("  (using last answer: " + lastAnswer + ")");
                return lastAnswer;
            }

            if (val.equalsIgnoreCase("pi") || val.equals("π")) return Math.PI;
            if (val.equalsIgnoreCase("e"))                      return Math.E;
            if (val.equalsIgnoreCase("phi") || val.equals("φ")) return PHI;

            try {
                return Double.parseDouble(val);
            } catch (NumberFormatException e) {
                System.out.print("  Invalid! Enter a number (or ans/pi/e/phi): ");
            }
        }
    }

    static int readInt(Scanner input, int min, int max) {
        while (true) {
            String val = input.nextLine().trim();

            if (val.isEmpty()) {
                System.out.print("  Please enter a whole number (" + min + "-" + max + "): ");
                continue;
            }

            if (val.contains(".")) {
                System.out.print("  Whole numbers only (" + min + "-" + max + "): ");
                continue;
            }

            try {
                long longVal = Long.parseLong(val);

                if (longVal > Integer.MAX_VALUE || longVal < Integer.MIN_VALUE) {
                    System.out.print("  Number is too large. Enter between " + min + " and " + max + ": ");
                    continue;
                }

                int n = (int) longVal;

                if (n < min || n > max) {
                    System.out.print("  Enter a number between " + min + " and " + max + ": ");
                    continue;
                }

                return n;

            } catch (NumberFormatException e) {
                System.out.print("  Whole numbers only (" + min + "-" + max + "): ");
            }
        }
    }

    static double[] readNumbers(Scanner input) {
        int count = 0;
        while (count <= 0) {
            System.out.print("  How many numbers? (1-10000): ");
            count = readInt(input, 1, 10000);
        }

        double[] nums = new double[count];
        System.out.println("  Tip: enter numbers separated by spaces, or one per line.");
        System.out.print("  Enter " + count + " number(s): ");

        int filled = 0;
        while (filled < count) {
            String line = input.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            int extraCount = 0;

            for (String part : parts) {
                if (filled >= count) {
                    extraCount++;
                    continue;
                }
                try {
                    nums[filled++] = Double.parseDouble(part);
                } catch (NumberFormatException e) {
                    System.out.print("  Invalid value '" + part + "'. Re-enter remaining " + (count - filled) + ": ");
                }
            }

            if (extraCount > 0) {
                System.out.println("  Note: " + extraCount + " extra number(s) were ignored.");
            }
        }

        return nums;
    }

    // ===========================
    // BASIC MATH
    // ===========================

    static double add(double a, double b)      { return a + b; }
    static double subtract(double a, double b) { return a - b; }
    static double multiply(double a, double b) { return a * b; }
    static double modulus(double a, double b)  { return a % b; }

    static double divide(double a, double b) {
        if (b == 0) {
            System.out.println("  Math error: division by zero is undefined.");
            return Double.NaN;
        }
        return a / b;
    }

    static double percentage(double a, double b) { return (a / 100.0) * b; }
    static double square(double a)               { return a * a; }
    static double cube(double a)                 { return a * a * a; }
    static double power(double a, double b)      { return Math.pow(a, b); }
    static double absolute(double a)             { return Math.abs(a); }
    static double round(double a)                { return Math.round(a); }
    static double tenToX(double a)               { return Math.pow(10, a); }

    static double squareRoot(double a) {
        if (a < 0) {
            System.out.println("  Math error: sqrt of negative is complex (not supported).");
            return Double.NaN;
        }
        return Math.sqrt(a);
    }

    static double cubeRoot(double a) {
        return Math.cbrt(a);
    }

    static double nthRoot(double a, double n) {
        if (n == 0) {
            System.out.println("  Math error: 0th root is undefined.");
            return Double.NaN;
        }
        if (a < 0) {
            if (n == Math.floor(n) && (long) n % 2 != 0) {
                return -Math.pow(-a, 1.0 / n);
            } else {
                System.out.println("  Math error: even root of negative is complex (not supported).");
                return Double.NaN;
            }
        }
        return Math.pow(a, 1.0 / n);
    }

    // ===========================
    // TRIG
    // ===========================

    static double sine(double val, boolean isDeg) {
        double rad    = isDeg ? Math.toRadians(val) : val;
        double result = Math.sin(rad);
        return Math.abs(result) < 1e-10 ? 0 : result;
    }

    static double cosine(double val, boolean isDeg) {
        double rad    = isDeg ? Math.toRadians(val) : val;
        double result = Math.cos(rad);
        return Math.abs(result) < 1e-10 ? 0 : result;
    }

    static double tangent(double val, boolean isDeg) {
        double rad = isDeg ? Math.toRadians(val) : val;
        if (Math.abs(Math.cos(rad)) < 1e-10) {
            System.out.println("  Math warning: tan(" + val + (isDeg ? "°" : " rad") + ") is undefined.");
            return Double.NaN;
        }
        return Math.tan(rad);
    }

    static double sineInverse(double a, boolean isDeg) {
        if (a < -1 || a > 1) {
            System.out.println("  Math error: sin inverse only accepts -1 to 1. You entered " + a + ".");
            return Double.NaN;
        }
        return isDeg ? Math.toDegrees(Math.asin(a)) : Math.asin(a);
    }

    static double cosineInverse(double a, boolean isDeg) {
        if (a < -1 || a > 1) {
            System.out.println("  Math error: cos inverse only accepts -1 to 1. You entered " + a + ".");
            return Double.NaN;
        }
        return isDeg ? Math.toDegrees(Math.acos(a)) : Math.acos(a);
    }

    static double tangentInverse(double a, boolean isDeg) {
        return isDeg ? Math.toDegrees(Math.atan(a)) : Math.atan(a);
    }

    // ===========================
    // STATISTICS
    // ===========================

    static double mean(double[] nums) {
        double sum = 0;
        for (double n : nums) sum += n;
        return sum / nums.length;
    }

    static double stdev(double[] nums) {
        if (nums.length < 2) {
            System.out.println("  Math error: sample stdev needs at least 2 numbers.");
            return Double.NaN;
        }
        double m   = mean(nums);
        double sum = 0;
        for (double n : nums) sum += (n - m) * (n - m);
        return Math.sqrt(sum / (nums.length - 1));
    }

    static double stdevp(double[] nums) {
        double m   = mean(nums);
        double sum = 0;
        for (double n : nums) sum += (n - m) * (n - m);
        return Math.sqrt(sum / nums.length);
    }

    // ===========================
    // LOG AND EXP
    // ===========================

    static double naturalLog(double a) {
        if (a <= 0) {
            System.out.println("  Math error: ln only works on positive numbers.");
            return Double.NaN;
        }
        return Math.log(a);
    }

    static double log10(double a) {
        if (a <= 0) {
            System.out.println("  Math error: log only works on positive numbers.");
            return Double.NaN;
        }
        return Math.log10(a);
    }

    static double eToX(double a) { return Math.exp(a); }

    // ===========================
    // COMBINATORICS
    // ===========================

    static double factorial(int n) {
        if (n < 0) {
            System.out.println("  Math error: factorial of negative is undefined.");
            return Double.NaN;
        }
        if (n > 170) {
            System.out.println("  Math error: " + n + "! overflows. Max is 170.");
            return Double.POSITIVE_INFINITY;
        }
        double result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }

    static double nCr(int n, int r) {
        if (r < 0 || r > n) {
            System.out.println("  Math error: r must be between 0 and n.");
            return Double.NaN;
        }
        if (r > n - r) r = n - r;
        double result = 1;
        for (int i = 0; i < r; i++) {
            result *= (n - i);
            result /= (i + 1);
        }
        return Math.round(result);
    }

    static double nPr(int n, int r) {
        if (r < 0 || r > n) {
            System.out.println("  Math error: r must be between 0 and n.");
            return Double.NaN;
        }
        double result = 1;
        for (int i = 0; i < r; i++) {
            result *= (n - i);
        }
        return result;
    }

    // ===========================
    // MEMORY
    // ===========================

    static double memory = 0;

    static void memoryAdd(double val) {
        memory += val;
        System.out.println("  M+ done. Memory = " + memory);
    }

    static void memorySubtract(double val) {
        memory -= val;
        System.out.println("  M- done. Memory = " + memory);
    }

    static double memoryRecall() {
        System.out.println("  MR: recalling " + memory + " from memory.");
        System.out.println("  (use 'ans' in next calculation to use this value)");
        return memory;
    }

    static void memoryClear() {
        memory = 0;
        System.out.println("  MC: memory cleared.");
    }

    // ===========================
    // HISTORY
    // ===========================

    static final int MAX_HISTORY = 100;
    static ArrayList<String> history = new ArrayList<>();

    static void addToHistory(String expression, double result) {
        history.add(0, expression + " = " + formatResult(result));
        if (history.size() > MAX_HISTORY) {
            history.remove(history.size() - 1);
        }
    }

    static void showHistory() {
        System.out.println("\n--- Calculation History (max " + MAX_HISTORY + ") ---");
        if (history.isEmpty()) {
            System.out.println("  No calculations yet.");
        } else {
            int show = Math.min(history.size(), 20);
            for (int i = 0; i < show; i++) {
                System.out.println("  " + (i + 1) + ". " + history.get(i));
            }
            if (history.size() > 20) {
                System.out.println("  ... and " + (history.size() - 20) + " more.");
            }
        }
        System.out.println("--------------------------------------");
    }

    // ===========================
    // CONSTANTS
    // ===========================

    static void showConstants() {
        System.out.println("\n--- Scientific Constants ---");
        System.out.println("  PI (Pi)               = " + Math.PI);
        System.out.println("  e  (Euler number)     = " + Math.E);
        System.out.println("  PHI (Golden ratio)    = " + PHI);
        System.out.println("  EULER_M               = " + EULER_M);
        System.out.println("  Tip: type 'pi', 'e', or 'phi' when entering numbers.");
        System.out.println("----------------------------");
    }

    // ===========================
    // DISPLAY
    // ===========================

    static String formatResult(double result) {
        if (Double.isNaN(result))      return "undefined";
        if (Double.isInfinite(result)) return "Infinity (too large)";
        double rounded = Math.round(result * 1e10) / 1e10;
        if (rounded == (long) rounded) return String.valueOf((long) rounded);
        return String.format("%.10f", rounded).replaceAll("0+$", "");
    }

    static void showResult(double result, String expression) {
        System.out.println("\n  " + expression + " = " + formatResult(result));
        if (Double.isNaN(result)) {
            System.out.println("  (result is undefined — check the error message above)");
        }
        if (Double.isInfinite(result)) {
            System.out.println("  (number is too large to represent)");
        }
    }

    // ===========================
    // MENUS
    // ===========================

    static void showMainMenu(String mode) {
        System.out.println("\n==============================");
        System.out.println("  MAIN TAB  |  Mode: " + mode);
        System.out.println("==============================");
        System.out.println("  BASIC");
        System.out.println("  1. Add        2. Subtract");
        System.out.println("  3. Multiply   4. Divide");
        System.out.println("  5. Modulus    6. Percentage");
        System.out.println("  7. Fraction (a/b)");
        System.out.println("  SCIENTIFIC");
        System.out.println("  8.  Square    9.  Cube");
        System.out.println("  10. Power     11. Sqrt");
        System.out.println("  12. Cube Root 13. Nth Root");
        System.out.println("  14. Absolute  15. Sin");
        System.out.println("  16. Cos       17. Tan");
        System.out.println("  NAVIGATE");
        System.out.println("  F=Func  M=Mode  C=Constants");
        System.out.println("  M+=MemAdd  M-=MemSub");
        System.out.println("  MR=MemRecall  MC=MemClear");
        System.out.println("  H=History     0=Exit");
        System.out.println("==============================");
        System.out.print("Option: ");
    }

    static void showFuncMenu(String mode) {
        System.out.println("\n==============================");
        System.out.println("  FUNC TAB  |  Mode: " + mode);
        System.out.println("==============================");
        System.out.println("  TRIG");
        System.out.println("  1. sin    2. cos    3. tan");
        System.out.println("  4. sin-1  5. cos-1  6. tan-1");
        System.out.println("  STATISTICS");
        System.out.println("  7. mean   8. stdev  9. stdevp");
        System.out.println("  POWER AND LOG");
        System.out.println("  10. a^b   11. sqrt  12. nth root");
        System.out.println("  13. e^x   14. 10^x");
        System.out.println("  15. ln    16. log");
        System.out.println("  COMBINATORICS");
        System.out.println("  17. nPr   18. nCr   19. n!");
        System.out.println("  OTHER");
        System.out.println("  20. abs   21. round");
        System.out.println("  22. PI    23. e     24. PHI");
        System.out.println("  NAVIGATE");
        System.out.println("  B=Main  M=Mode  H=History  0=Exit");
        System.out.println("==============================");
        System.out.print("Option: ");
    }

    // ===========================
    // MAIN
    // ===========================

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        boolean loggedIn = login(input);
        if (!loggedIn) {
            System.out.println("\nToo many wrong attempts. Goodbye!");
            input.close();
            return;
        }

        boolean onMainTab = true;
        boolean isDeg     = true;

        System.out.println("Tip: type 'ans' to reuse last answer (starts at 0).");
        System.out.println("Tip: type 'pi', 'e', or 'phi' as number inputs.");

        while (true) {

            String mode = isDeg ? "DEG" : "RAD";

            if (onMainTab) showMainMenu(mode);
            else           showFuncMenu(mode);

            String line = input.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println("Please enter an option.");
                continue;
            }

            String lineUpper = line.toUpperCase();

            if (lineUpper.equals("M+")) { memoryAdd(lastAnswer);           continue; }
            if (lineUpper.equals("M-")) { memorySubtract(lastAnswer);      continue; }
            if (lineUpper.equals("MR")) { lastAnswer = memoryRecall();      continue; }
            if (lineUpper.equals("MC")) { memoryClear();                    continue; }
            if (lineUpper.equals("F") && onMainTab)  { onMainTab = false;  continue; }
            if (lineUpper.equals("B") && !onMainTab) { onMainTab = true;   continue; }
            if (lineUpper.equals("H")) { showHistory();                     continue; }
            if (lineUpper.equals("C")) { showConstants();                   continue; }

            if (lineUpper.equals("M")) {
                isDeg = !isDeg;
                System.out.println("Mode switched to " + (isDeg ? "DEG (degrees)" : "RAD (radians)"));
                continue;
            }

            if (line.equals("0")) {
                System.out.print("Are you sure you want to exit? (Y/N): ");
                String confirm = input.nextLine().trim();
                if (confirm.equalsIgnoreCase("Y")) {
                    System.out.println("\nGoodbye " + loggedInUser + "!");
                    break;
                }
                System.out.println("Cancelled.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Enter a number or F/B/M/H/C/M+/M-/MR/MC.");
                continue;
            }

            double result    = 0;
            String expression = "";

            // ===========================
            // MAIN TAB
            // ===========================
            if (onMainTab) {

                if (choice == 8 || choice == 9 || choice == 11 || choice == 12 ||
                    choice == 14 || choice == 15 || choice == 16 || choice == 17) {

                    System.out.print("Enter number: ");
                    double a = readDouble(input);

                    switch (choice) {
                        case 8:  result = square(a);         expression = a + "^2"; break;
                        case 9:  result = cube(a);           expression = a + "^3"; break;
                        case 11: result = squareRoot(a);     expression = "sqrt(" + a + ")"; break;
                        case 12: result = cubeRoot(a);       expression = "cbrt(" + a + ")"; break;
                        case 14: result = absolute(a);       expression = "|" + a + "|"; break;
                        case 15: result = sine(a, isDeg);    expression = "sin(" + a + ")"; break;
                        case 16: result = cosine(a, isDeg);  expression = "cos(" + a + ")"; break;
                        case 17: result = tangent(a, isDeg); expression = "tan(" + a + ")"; break;
                    }

                } else if ((choice >= 1 && choice <= 7) || choice == 10 || choice == 13) {

                    if (choice == 6) System.out.print("Percentage value (e.g. 20 for 20%): ");
                    else             System.out.print("First number: ");
                    double num1 = readDouble(input);

                    if (choice == 6) System.out.print("Percentage of what number? ");
                    else             System.out.print("Second number: ");
                    double num2 = readDouble(input);

                    switch (choice) {
                        case 1:  result = add(num1, num2);        expression = num1 + " + " + num2; break;
                        case 2:  result = subtract(num1, num2);   expression = num1 + " - " + num2; break;
                        case 3:  result = multiply(num1, num2);   expression = num1 + " x " + num2; break;
                        case 4:  result = divide(num1, num2);     expression = num1 + " / " + num2; break;
                        case 5:  result = modulus(num1, num2);    expression = num1 + " % " + num2; break;
                        case 6:  result = percentage(num1, num2); expression = num1 + "% of " + num2; break;
                        case 7:  result = divide(num1, num2);     expression = num1 + " / " + num2; break;
                        case 10: result = power(num1, num2);      expression = num1 + "^" + num2; break;
                        case 13: result = nthRoot(num1, num2);    expression = num2 + " root of " + num1; break;
                    }

                } else {
                    System.out.println("That option doesn't exist on MAIN tab.");
                    continue;
                }

            // ===========================
            // FUNC TAB
            // ===========================
            } else {

                if (choice == 22) {
                    result = Math.PI; expression = "PI";
                } else if (choice == 23) {
                    result = Math.E;  expression = "e";
                } else if (choice == 24) {
                    result = PHI;     expression = "PHI";

                } else if (choice >= 7 && choice <= 9) {
                    double[] nums = readNumbers(input);
                    switch (choice) {
                        case 7: result = mean(nums);   expression = "mean"; break;
                        case 8: result = stdev(nums);  expression = "stdev"; break;
                        case 9: result = stdevp(nums); expression = "stdevp"; break;
                    }

                } else if (choice >= 17 && choice <= 19) {
                    System.out.print("Enter n (0-10000): ");
                    int n = readInt(input, 0, 10000);

                    if (choice == 19) {
                        if (n > 170) {
                            System.out.println("  n! is capped at 170. Enter n (0-170): ");
                            n = readInt(input, 0, 170);
                        }
                        result = factorial(n);
                        expression = n + "!";
                    } else {
                        System.out.print("Enter r (0-" + n + "): ");
                        int r = readInt(input, 0, n);
                        result = (choice == 17) ? nPr(n, r) : nCr(n, r);
                        expression = (choice == 17 ? "P(" : "C(") + n + "," + r + ")";
                    }

                } else if (choice == 10 || choice == 12) {
                    System.out.print("First number: ");
                    double num1 = readDouble(input);
                    System.out.print("Second number: ");
                    double num2 = readDouble(input);
                    if (choice == 10) {
                        result = power(num1, num2);
                        expression = num1 + "^" + num2;
                    } else {
                        result = nthRoot(num1, num2);
                        expression = num2 + " root of " + num1;
                    }

                } else if ((choice >= 1 && choice <= 6) || (choice >= 11 && choice <= 16) ||
                            choice == 20 || choice == 21) {

                    System.out.print("Enter number: ");
                    double a = readDouble(input);

                    switch (choice) {
                        case 1:  result = sine(a, isDeg);           expression = "sin(" + a + ")"; break;
                        case 2:  result = cosine(a, isDeg);         expression = "cos(" + a + ")"; break;
                        case 3:  result = tangent(a, isDeg);        expression = "tan(" + a + ")"; break;
                        case 4:  result = sineInverse(a, isDeg);    expression = "sin-1(" + a + ")"; break;
                        case 5:  result = cosineInverse(a, isDeg);  expression = "cos-1(" + a + ")"; break;
                        case 6:  result = tangentInverse(a, isDeg); expression = "tan-1(" + a + ")"; break;
                        case 11: result = squareRoot(a);            expression = "sqrt(" + a + ")"; break;
                        case 13: result = eToX(a);                  expression = "e^" + a; break;
                        case 14: result = tenToX(a);                expression = "10^" + a; break;
                        case 15: result = naturalLog(a);            expression = "ln(" + a + ")"; break;
                        case 16: result = log10(a);                 expression = "log(" + a + ")"; break;
                        case 20: result = absolute(a);              expression = "|" + a + "|"; break;
                        case 21: result = round(a);                 expression = "round(" + a + ")"; break;
                    }

                } else {
                    System.out.println("That option doesn't exist on FUNC tab.");
                    continue;
                }
            }

            if (!Double.isNaN(result)) lastAnswer = result;
            addToHistory(expression, result);
            showResult(result, expression);

            System.out.print("\nPress Enter to continue...");
            input.nextLine();
        }

        input.close();
    }
}