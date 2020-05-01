/**
 * The Simplification demo class.
 * here I will demonstrate 19 examples of the simplification
 * I chose to implement in this bonus part.
 * hope you liked it :)
 *
 * @author Arye Amsalem
 * @version 1.00 28 April 2019
 */
public class SimplificationDemo {
    // array of expressions - store all the Expressions in
    private Expression[] demoArray = new Expression[19];

    /**
     * Mechanism method:
     * this method implements the phases of the "Mechanism" algorithm which are:
     * 1. simplify and canonical X12 times in a row
     *    Remark: the reason for 12 times is to cover long parenthesis
     *    expressions that need more than 2-3 repeats (after checking).
     *    meaning - there are some actions that has to be done more then once.
     * 2. collecting like terms
     * 3. simplify
     * 4. algebra magic
     * 5. simplify
     * 6. final string (will be implemented at the demo print method.
     *
     * @param e the expression "before".
     * @return the expression "after".
     */
    public Expression mechanism(Expression e) {
        // iterating 12 times the basic algo simplificatzius for large parenthesis opening
        for (int i = 0; i < 12; i++) {
            e = e.simplify();
            // always remain in the "canonical-form"!!! it's extremely important
            e = e.toCanonical();
        }
        // continue the MECHANISM
        e = e.collectingLikeTerms();
        e = e.simplify();
        e = e.algebraMagic();
        return e.simplify();
    }

    /**
     * The Demo Print.
     * here we just print the final string of every expression after he went
     * through the Mechanism Algorithm.
     *
     * @param e the Expression to process.
     * @param num the number of demo (# out of 19)
     */
    public void demoPrint(Expression e, int num) {
        System.out.println("Demo # " + num + " Expression:");
        // the Expression before simplification
        System.out.println(e);
        System.out.println("Advanced Simplified Expression:");
        // the Expression After the Mechanism simplification
        System.out.println(mechanism(e).finalString());
        System.out.println();
    }

    /**
     * Initialize expressions - just save all the expressins for Demo in an
     * array of Expressions.
     */
    public void initializeExpressions() {
        // (((((7.0 * x) + 2.0) - (4.0 * x)) + (78.0 - x)) + (34.0 + (-x)))
        demoArray[0] = new Plus(new Plus(new Minus(new Plus(new Mult(7, "x"), new Num(2)),
                new Mult(4, "x")), new Minus(78, "x")), new Plus(34, new Neg("x")));
        // (((x / 5.0) - (2.0 * (x - 5.0))) + (((-x) + 6.0) * 6.0))
        demoArray[1] = new Plus(new Minus(new Div("x", 5), new Mult(2, new Minus("x", 5))),
                new Mult(new Plus(new Neg("x"), 6), 6));
        // (((2.0^2.0) * (x^2.0)) + (((5.0 * (x^2.0)) + (((x^2.0) + (4.0 + (x^2.0))) + (x^2.0))) * 2.0))
        demoArray[2] = new Plus(new Mult(new Pow(2, 2), new Pow("x", 2)),
                new Mult(new Plus(new Mult(5, new Pow("x", 2)), new Plus(new Plus(new Pow("x", 2),
                        new Plus(4, new Pow("x", 2))), new Pow("x", 2))), 2));
        // ((1.0 + (2.0 * x)) - ((2.0 * x) + 1.0))
        demoArray[3] = new Minus(new Plus(1, new Mult(2, "x")), new Plus(new Mult(2, "x"), 1));
        // ((((((7.0 * x) + 2.0) - (4.0 * x)) + (78.0 - x)) + (34.0 + (-x)))
        // + (((x / 5.0) - (2.0 * (x - 5.0))) + (((-x) + 6.0) * 6.0)))
        demoArray[4] = new Plus(demoArray[0], demoArray[1]);
        // (((((x^2.0)^3.0)^2.0)^5.0)^1.0)
        demoArray[5] = new Pow(new Pow(new Pow(new Pow(new Pow("x", 2), 3), 2), 5), 1);
        // (((a / b) / (x / y)) / ((c / d) / (w / z)))
        demoArray[6] = new Div(new Div(new Div("a", "b"), new Div("x", "y")),
                new Div(new Div("c", "d"), new Div("w", "z")));
        // (((x^0.5)^4.0) + ((4.0 * (x^2.0)) / (x^2.0)))
        demoArray[7] = new Plus(new Pow(new Pow("x", 0.5), 4),
                new Div(new Mult(4, new Pow("x", 2)), new Pow("x", 2)));
        // (((2.0 * ((x^5.0) / (x^3.0))) + (((x^0.5)^4.0) + ((4.0 * (x^2.0)) / (x^2.0)))) + 2.0)
        demoArray[8] = new Plus(new Plus(new Mult(2, new Div(new Pow("x", 5), new Pow("x", 3))), demoArray[7]), 2);
        // ((5.0 * ((x + 5.0)^5.0)) / ((x + 5.0)^10.0))
        demoArray[9] = new Div(new Mult(5, new Pow(new Plus("x", 5), 5)), new Pow(new Plus("x", 5), 10));
        // (x * ((x + 1.0) * (x + 2.0)))
        demoArray[10] = new Mult("x", new Mult(new Plus("x", 1), new Plus("x", 2)));
        // ((((2.0^2.0) * (x^2.0)) + (((5.0 * (x^2.0)) + (((x^2.0) +
        // (4.0 + (x^2.0))) + (x^2.0))) * 2.0)) * (x * ((x + 1.0) * (x + 2.0))))
        demoArray[11] = new Mult(demoArray[2], demoArray[10]);
        // ((((2.0^2.0) * (x^2.0)) + (((5.0 * (x^2.0)) + (((x^2.0) + (4.0 + (x^2.0))) +
        // (x^2.0))) * 2.0)) * (((((7.0 * x) + 2.0) - (4.0 * x)) + (78.0 - x)) + (34.0 + (-x))))
        demoArray[12] = new Mult(demoArray[2], demoArray[0]);
        // ((a^log(a, (((x^0.5)^4.0) + ((4.0 * (x^2.0)) / (x^2.0))))) + (((x^0.5)^4.0) + ((4.0 * (x^2.0)) / (x^2.0))))
        demoArray[13] = new Plus(new Pow("a", new Log("a", demoArray[7])), demoArray[7]);
        // (x * log(x, (x^2.0)))
        demoArray[14] = new Mult("x", new Log("x", new Pow("x", 2)));
        // ((((x^(log(x, 2.0) + log(x, 3.0))) * (x * log(x, (x^2.0)))) / x) + 88.0)
        demoArray[15] = new Plus(new Div(new Mult(new Pow("x",
                new Plus(new Log("x", 2), new Log("x", 3))), demoArray[14]), "x"), 88);
        // ((sin((-x))^2.0) + (cos((-x))^2.0))
        demoArray[16] = new Plus(new Pow(new Sin(new Neg("x")), 2), new Pow(new Cos(new Neg("x")), 2));
        // ((sin(e) * cos(x)) + (sin(x) * cos(e)))
        demoArray[17] = new Plus(new Mult(new Sin("e"), new Cos("x")), new Mult(new Sin("x"), new Cos("e")));
        // ((((x^4.0) / (x^2.0)) + ((5.0 * x) + (5.0 * x))) + 25.0)
        demoArray[18] = new Plus(new Plus(new Div(new Pow("x", 4), new Pow("x", 2)),
                new Plus(new Mult(5, "x"), new Mult(5, "x"))), 25);
    }

    /**
     * The entry point of application.
     * create a new simplification demo and run it.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // creating new demo object
        SimplificationDemo demo = new SimplificationDemo();
        // initialize the expressions for demo
        demo.initializeExpressions();
        // run the demo
        for (int i = 0; i < 19; i++) {
            demo.demoPrint(demo.demoArray[i], i + 1);
        }
    }
}
