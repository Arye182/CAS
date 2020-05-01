import java.util.Map;

/**
 * The type Log.
 * Log is a BinaryExpression. it has all the fields and methods of Binary by
 * inheritance.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Log extends BinaryExpression implements Expression {
    /**
     * Instantiates a new Log such that left and right are Expressions.
     *
     * @param left  the left Expression.
     * @param right the right Expression.
     */
    Log(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Log such that right is an Expression and left is a Num.
     *
     * @param left  the left Expression.
     * @param right the right Num.
     */
    Log(Expression left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Log such that left is an Expression and right is a Var.
     *
     * @param left  the left Expression.
     * @param right the right Var.
     */
    Log(Expression left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Log such that left is a Num and right is an Expression.
     *
     * @param left  the left Num.
     * @param right the right Expression.
     */
    Log(double left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Log such that left and right are Nums.
     *
     * @param left  the left Num.
     * @param right the right Num.
     */
    Log(double left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Log such that left is a Num and right is a Var.
     *
     * @param left  the left Num.
     * @param right the right Var.
     */
    Log(double left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Log such that left is a Var and right is an Expression.
     *
     * @param left  the left Var.
     * @param right the right Expression.
     */
    Log(String left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Log such that left is a Var and right is a Num.
     *
     * @param left  the left Var.
     * @param right the right Num.
     */
    Log(String left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Log such that left and right are Vars.
     *
     * @param left  the left Var.
     * @param right the right Var.
     */
    Log(String left, String right) {
        super(left, right);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // exception 1
        if (super.tryEvaluate(super.getRight()) <= 0) {
            throw new Exception("Illegal Math Action - Log cannot be calculated on negative numbers or zero");
        }
        // exception 2
        if (super.tryEvaluate(super.getLeft()) <= 0 || super.tryEvaluate(super.getLeft()) == 1) {
            throw new Exception("Illegal Math Action - Log base has to be > 0 and different from 1");
        }
        // default
        return (Math.log(super.getRight().evaluate(assignment)) / Math.log(super.getLeft().evaluate(assignment)));
    }

    @Override
    public String toString() {
        // recursive on left and right
        String leftS = super.getLeft().toString();
        String rightS = super.getRight().toString();
        // return the final result
        return ("log(" + leftS + ", " + rightS + ")");
    }

    @Override
    public Expression copy() {
        // returns a new Log (Copy)
        return new Log(super.getLeft().copy(), super.getRight().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // (Log(a, u))' = (1 / u * ln(a)) * u'
        return new Mult(new Div(1, new Mult(super.getRight(), new Log("e", super.getLeft()))),
                super.getRight().differentiate(var));
    }

    @Override
    public Expression simplify() {
        // creating new Log expression
        BinaryExpression logSimplified = (BinaryExpression) this.copy();
        // recursion on sides
        logSimplified.setLeft(logSimplified.getLeft().simplify());
        logSimplified.setRight(logSimplified.getRight().simplify());
        // Log(x, x) => 1
        if (logSimplified.getLeft().toString().equals(logSimplified.getRight().toString())) {
            return new Num(1);
        }
        // if both sides are actual numbers - i want to calculate.
        if ((!Double.isNaN(logSimplified.tryEvaluate(getLeft()))
                && (!Double.isNaN(logSimplified.tryEvaluate(getRight()))))) {
            return new Num(tryEvaluate(this));
        }
        // return the final expression
        return (Expression) logSimplified;
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // recursion
        super.setLeft(super.getLeft().toCanonical());
        super.setRight(super.getRight().toCanonical());
        // Log(a, x^n) => n * (Log(a, x))
        if (getRight() instanceof Pow) {
            return new Mult(((Pow) getRight()).getRight(), new Log(getLeft(), ((Pow) getRight()).getLeft()));
        }
        return this;
    }

    @Override
    public Expression collectingLikeTerms() {
        return this;
    }

    @Override
    public Expression algebraMagic() {
        // recursion
        super.setLeft(super.getLeft().algebraMagic());
        super.setRight(super.getRight().algebraMagic());
        return this;
    }

    @Override
    public String finalString() {
        return toString();
    }
}
