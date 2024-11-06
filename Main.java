import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigIntCalculation calculator = new BigIntCalculation();

        System.out.println("Enter an expression with large integers (e.g., '123456789 + 987654321' or '987654321 - 123456789'): ");

        // Read the input expression from the user
        String expression = scanner.nextLine();

        try {
            // Evaluate the expression using the BigIntCalculation class
            String result = calculator.evaluateExpression(expression);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}