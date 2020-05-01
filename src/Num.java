import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Num.
 * this class implements the Expression interface, and does not inherit anything
 * from anyone.
 * in most cases it will be a termination condition for the recursion in our
 * Expression tree.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Num implements Expression {
    // field - a double
    private double num;

    /**
     * Instantiates a new Num.
     * supports also ints (auto cast)
     *
     * @param value the number.
     */
    public Num(double value) {
        this.num = value;
     }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // number evaluation is just itself
        return this.evaluate();
    }

    @Override
    public double evaluate() throws Exception {
        // same as above
        return num;
    }

    @Override
    public List<String> getVariables() {
        // no variables - meaning null
        return null;
    }

    @Override
    public String toString() {
        // string representation of a number (double)
        return Double.toString(num);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        // cannot assign to a number - returning itself
        return this;
    }

    @Override
    public Expression copy() {
        // copy of the number
        return new Num(num);
    }

    @Override
    public Expression differentiate(String var) {
        // constant ' = 0
        return new Num(0);
    }

    @Override
    public Expression simplify() {
        // cant simplify anymore a number :)
        return this;
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // number is already simplified
        return this;
    }

    @Override
    public Expression collectingLikeTerms() {
        // number is already simplified
        return this;
    }

    @Override
    public Expression algebraMagic() {
        // number is already simplified
        return this;
    }

    @Override
    public String finalString() {
        // clever way to print numbers as doubles or ints :)
        // 3.0 => 3
        if (num - (int) num == 0) {
            return Integer.toString((int) num);
        } else {
            return this.toString();
        }
    }

    @Override
    public List<Expression> toLista() {
        // number is just a number when collecting like terms so we want to keep
        // it in our list and push it after to the end of expression.
        List<Expression> numLista = new ArrayList<Expression>();
        numLista.add(this);
        return numLista;
    }
}
