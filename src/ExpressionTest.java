import java.util.Map;
import java.util.TreeMap;

/**
 * The Expression test class.
 * uses us for printing the assignment request at the
 * end part of the assignment.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class ExpressionTest {
    /**
     * The entry point of application.
     * this main will include the prints for the assignment as a test.
     * given the instructions from assignment:
     * -------------------------------------------------------------------------
     * 1. Create the expression (2x) + (sin(4y)) + (e^x).
     * 2. Print the expression.
     * 3. Print the value of the expression with (x=2,y=0.25,e=2.71).
     * 4. Print the differentiated expression according to x.
     * 5. Print the value of the differentiated expression according to
     * x with the assignment above.
     * 6. Print the simplified differentiated expression.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // create expression
        Expression e = new Plus(new Plus(new Mult(2, "x"), new Sin(new Mult(4, "y"))), new Pow("e", "x"));
        // create assignment Map
        Map<String, Double> assignment = new TreeMap<String, Double>();
        assignment.put("x", 2.00);
        assignment.put("y", 0.25);
        assignment.put("e", 2.71);
        // print expression
        System.out.println(e);
        //print the expression with values assignment
        try {
            System.out.println(e.evaluate(assignment));
        } catch (Exception exe) {
            System.out.println(exe.toString());
        }
        // print differentiated expression
        System.out.println(e.differentiate("x"));
        // print differentiated expression with assignment
        try {
            System.out.println(e.differentiate("x").evaluate(assignment));
        } catch (Exception exe) {
            System.out.println(exe.toString());
        }
        // print simplified differentiated expression
        System.out.println(e.differentiate("x").simplify());
    }
}
