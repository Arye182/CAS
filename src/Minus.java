import java.util.Map;

/**
 * The type Minus.
 * Minus is a BinaryExpression. it has all the fields and methods of Binary by
 * inheritance.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Minus extends BinaryExpression implements Expression {
    /**
     * Instantiates a new Minus such that left and right are Expressions.
     *
     * @param left  the left Expression.
     * @param right the right Expression.
     */
    Minus(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Minus such that left is an Expression and right is a Num.
     *
     * @param left  the left Expression.
     * @param right the right Num.
     */
    Minus(Expression left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Minus such that left is an Expression and right is a Var.
     *
     * @param left  the left Expression.
     * @param right the right Var.
     */
    Minus(Expression left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Minus such that left is a Num and right is an Expression.
     *
     * @param left  the left Num.
     * @param right the right Expression.
     */
    Minus(double left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Minus such that left and right are Nums.
     *
     * @param left  the left Num.
     * @param right the right Num.
     */
    Minus(double left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Minus such that left is a Num and right is a Var.
     *
     * @param left  the left Num.
     * @param right the right Var.
     */
    Minus(double left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Minus such that left is a Var and right is an Expression.
     *
     * @param left  the left Var.
     * @param right the right Expression.
     */
    Minus(String left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Minus such that left is a Var and right is a Num.
     *
     * @param left  the left Var.
     * @param right the right Num.
     */
    Minus(String left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Minus such that left and right are Vars.
     *
     * @param left  the left Var.
     * @param right the right Var.
     */
    Minus(String left, String right) {
        super(left, right);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // returns a calculation (-)
        return (super.getLeft().evaluate(assignment) - super.getRight().evaluate(assignment));
    }

    @Override
    public String toString() {
        // recursion
        String leftS = super.getLeft().toString();
        String rightS = super.getRight().toString();
        // final string...
        return ("(" + leftS + " - " + rightS + ")");
    }

    @Override
    public Expression copy() {
        // new Minus (copy)
        return new Minus(super.getLeft().copy(), super.getRight().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // just like Plus derive of two functions
        return new Minus(super.getLeft().differentiate(var), super.getRight().differentiate(var));
    }

    @Override
    public Expression simplify() {
        // creating new div expression
        BinaryExpression minusSimplified = (BinaryExpression) this.copy();
        // recursive call left and right
        minusSimplified.setLeft(minusSimplified.getLeft().simplify());
        minusSimplified.setRight(minusSimplified.getRight().simplify());
        // 0 - X = -X
        if (minusSimplified.tryEvaluate(minusSimplified.getLeft()) == 0) {
            return new Neg(minusSimplified.getRight().simplify());
        }
        // X - 0 = X
        if (minusSimplified.tryEvaluate(minusSimplified.getRight()) == 0) {
            return minusSimplified.getLeft().simplify();
        }
        // X - X = 0
        if (minusSimplified.getLeft().toString().equals(minusSimplified.getRight().toString())) {
            return new Num(0);
        }
        // if both sides are actual numbers - i want to calculate.
        if ((!Double.isNaN(minusSimplified.tryEvaluate(getLeft()))
                && (!Double.isNaN(minusSimplified.tryEvaluate(getRight()))))) {
            return new Num(minusSimplified.tryEvaluate(getLeft()) - minusSimplified.tryEvaluate(getRight()));
        }
        // return the final simplified expression
        return (Expression) minusSimplified;
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // Log(a, x) - Log(a, y) => Log(a, (x/y))
        if (getLeft() instanceof Log && getRight() instanceof Log) {
            if (((Log) getLeft()).getLeft().toString().equals(((Log) getRight()).getLeft().toString())) {
                return new Log(((Log) getLeft()).getLeft(),
                        new Div(((Log) getLeft()).getRight(), ((Log) getRight()).getRight()));
            }
        }
        // default
        return new Plus(super.getLeft(), new Neg(super.getRight()));
    }

    @Override
    public Expression collectingLikeTerms() {
        return this; // no collecting like terms in minus
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
        // no really need for this... i will put it off next version
        String leftS = super.getLeft().finalString();
        String rightS = super.getRight().finalString();
        return (leftS + " - " + rightS);
    }
}
