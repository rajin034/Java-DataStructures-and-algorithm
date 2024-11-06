import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides methods to evaluate and perform arithmetic operations
 * (addition and subtraction) on large integers represented as strings.
 * It uses stacks to handle digit-by-digit operations and avoids overflow
 * issues associated with standard integer types.
 */
class BigIntCalculation {
    // Regular expression pattern to match expressions in the form "operand1 operator operand2"
    private static final Pattern PATTERN = Pattern.compile("\\s*(\\d+)\\s*([+-])\\s*(\\d+)\\s*");
 /**
  *  Regular expression pattern to match expressions in the form "operand1 operator operand2"
 Breakdown of the pattern:
 \\s*      : Matches any whitespace character (spaces, tabs) zero or more times, allowing flexibility around each component.
 (\\d+)    : Matches one or more digits (0-9) and captures it as the first operand (operand1).
 \\s*      : Matches any whitespace character zero or more times between the first operand and operator.
 ([+-])    : Matches either the '+' or '-' operator and captures it as the operator.
 \\s*      : Matches any whitespace character zero or more times between the operator and second operand.
 (\\d+)    : Matches one or more digits (0-9) and captures it as the second operand (operand2).
 \\s*      : Matches any trailing whitespace after the expression.))
 */

    /**
     * Evaluates a simple arithmetic expression involving large integers and
     * returns the result as a string. Supports addition and subtraction.
     *
     * @param expression The expression in the format "operand1 operator operand2".
     * @return The result of the evaluated expression as a string.
     * @throws IllegalArgumentException if the expression format is invalid.
     */
    public String evaluateExpression(String expression) {
        Matcher m = PATTERN.matcher(expression.strip());

        // Validate that the input expression matches the expected format
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid expression format. Expected format: 'operand1 + or - operand2'");
        }

        // Extract operands and operator from the matched expression
        String operand1 = m.group(1);  // First number (left operand)
        String operator = m.group(2);  // Operator, either + or -
        String operand2 = m.group(3);  // Second number (right operand)


        // Determine the operation based on the operator and invoke respective methods
        if (operator.equals("+")) {
            return addBigIntegers(operand1, operand2);
        } else if (operator.equals("-")) {
            return subtractBigIntegers(operand1, operand2);
        } else {
            throw new IllegalStateException("Unexpected operator: " + operator);
        }
    }

    /**
     * Adds two large integers represented as strings and returns the result as a string.
     *
     * @param operand1 The first operand as a string.
     * @param operand2 The second operand as a string.
     * @return The sum of the two operands as a string.
     */
    private String addBigIntegers(String operand1, String operand2) {
        Stack<Integer> stack1 = createStackFromString(operand1);
        Stack<Integer> stack2 = createStackFromString(operand2);
        Stack<Integer> resultStack = new Stack<>();

        int carry = 0;

        // Perform digit-by-digit addition, managing carry-over between each step
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int digit1 = stack1.isEmpty() ? 0 : stack1.pop();  /*Pop the top digit from each stack, or use 0 if the stack is empty.*/
            int digit2 = stack2.isEmpty() ? 0 : stack2.pop();

            int sum = digit1 + digit2 + carry;
            resultStack.push(sum % 10);  // Store the last digit of the sum
            carry = sum / 10;  // Update carry for the next iteration


            /*Debugging statements to check the values at each step
            System.out.println("Digit1: " + digit1 + ", Digit2: " + digit2 + ", Sum: " + sum + ", Carry: " + carry);
            System.out.println("Current Result Stack: " + resultStack);*/
        }

        return stackToString(resultStack);

    }

    /**
     * Subtracts the second large integer from the first, both represented as strings,
     * and returns the result as a string. If the result is negative, a '-' sign is prepended.
     *
     * @param operand1 The first operand as a string.
     * @param operand2 The second operand as a string.
     * @return The difference between operand1 and operand2 as a string.
     */
    private String subtractBigIntegers(String operand1, String operand2) {
        boolean isNegative = false;

        // Check if operand1 is smaller than operand2; if so, swap to avoid negative results
        if (isSmaller(operand1, operand2)) {
            String temp = operand1;
            operand1 = operand2;
            operand2 = temp;
            isNegative = true;
        }

        Stack<Integer> stack1 = createStackFromString(operand1);
        Stack<Integer> stack2 = createStackFromString(operand2);
        Stack<Integer> resultStack = new Stack<>();

        int borrow = 0;

        // Perform digit-by-digit subtraction, managing borrow when needed
        while (!stack1.isEmpty()) {
            int digit1 = stack1.pop();
            int digit2 = stack2.isEmpty() ? 0 : stack2.pop();

            int diff = digit1 - digit2 - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            resultStack.push(diff);
        }

        // Remove leading zeros from the result
        while (resultStack.size() > 1 && resultStack.peek() == 0) {
            resultStack.pop();
        }

        return (isNegative ? "-" : "") + stackToString(resultStack);
    }

    /**
     * Converts a string representing a number into a stack of its digits.
     *
     * @param number The string representation of the number.
     * @return A stack of integers where each element is a digit of the number.
     */
    private Stack<Integer> createStackFromString(String number) {
        Stack<Integer> stack = new Stack<>();
        for (char c : number.toCharArray()) {
            stack.push(c - '0');  // Convert char to its integer value
        }
        return stack;
    }

    /**
     * Converts a stack of digits back into a string representation of the number.
     *
     * @param stack A stack containing the digits of the number.
     * @return The string representation of the number.
     */
    private String stackToString(Stack<Integer> stack) {
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * Checks if the first operand is numerically smaller than the second.
     *
     * @param operand1 The first operand as a string.
     * @param operand2 The second operand as a string.
     * @return True if operand1 is smaller than operand2, false otherwise.
     */
    private boolean isSmaller(String operand1, String operand2) {
        if (operand1.length() < operand2.length()) return true;
        if (operand1.length() > operand2.length()) return false;
        return operand1.compareTo(operand2) < 0;
    }
}
