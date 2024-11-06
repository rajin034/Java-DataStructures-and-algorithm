import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BigIntCalculationTest {
    @Test
    public void testEvaluateExpression() {
        String expressions = "15 + 10\n" +
                             "25 - 8\n" +
                             "100 + 100\n" +
                             "500 - 600\n" +
                             "10 - 10\n" +
                             "12345678901234567890 + 9876543210987654321\n";

        String[] lines = expressions.split("\n");

        BigIntCalculation calculator = new BigIntCalculation();

        assertEquals("25", calculator.evaluateExpression(lines[0]));
        assertEquals("17", calculator.evaluateExpression(lines[1]));
        assertEquals("200", calculator.evaluateExpression(lines[2]));
        assertEquals("-100", calculator.evaluateExpression(lines[3]));
        assertEquals("0", calculator.evaluateExpression(lines[4]));
        assertEquals("22222222112222222211", calculator.evaluateExpression(lines[5]));
    }
}
