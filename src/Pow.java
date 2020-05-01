import java.util.Map;

/**
 * The type Pow.
 * Pow is a BinaryExpression. it has all the fields and methods of Binary by
 * inheritance.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Pow extends BinaryExpression implements Expression {
    /**
     * Instantiates a new Pow suh that left and right are Expressions.
     *
     * @param left the left Expression.
     * @param right the right Expression.
     */
    Pow(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Pow such that left is an Expression and right is a Num.
     *
     * @param left the left Expression.
     * @param right the right Num.
     */
    Pow(Expression left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Pow such that left is an Expression and right is a Var.
     *
     * @param left the left Expression.
     * @param right the right Var.
     */
    Pow(Expression left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Pow such that left is a um and right is an Expression.
     *
     * @param left the left Num.
     * @param right the right Expression.
     */
    Pow(double left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Pow such that left and right are Nums.
     *
     * @param left the left Num.
     * @param right the right Num.
     */
    Pow(double left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Pow such that left is a Num and right is a Var.
     *
     * @param left the left Num.
     * @param right the right Var.
     */
    Pow(double left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Pow such that left is a Var and right is an Expression.
     *
     * @param left the left Var.
     * @param right the right Expression.
     */
    Pow(String left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Pow such that left is a Var and right is a Num.
     *
     * @param left the left Var.
     * @param right the right Num.
     */
    Pow(String left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Pow such that left and right are Vars.
     *
     * @param left the left Var.
     * @param right the right Var.
     */
    Pow(String left, String right) {
        super(left, right);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // returns the power math calculation of the expression.
        return (Math.pow(getLeft().evaluate(assignment), getRight().evaluate(assignment)));
    }

    @Override
    public String toString() {
        // recursion calls
        String leftS = getLeft().toString();
        String rightS = getRight().toString();
        // final string representation
        return ("(" + leftS + "^" + rightS + ")");
    }

    @Override
    public Expression copy() {
        // copy of this Expression
        return new Pow(getLeft().copy(), getRight().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // this is the formula of (f^g)' - taken from the link was provided in the assignment
        return new Mult(this, new Plus(new Mult(getLeft().differentiate(var), new Div(getRight(), getLeft())),
                new Mult(getRight().differentiate(var), new Log(new Var("e"), getLeft()))));
    }

    @Override
    public Expression simplify() {
        // creating new pow expression
        BinaryExpression powSimplified = (BinaryExpression) this.copy();
        // recursive simplification
        powSimplified.setLeft(powSimplified.getLeft().simplify());
        powSimplified.setRight(powSimplified.getRight().simplify());
        // if both sides are actual numbers - i want to calculate.
        if ((!Double.isNaN(powSimplified.tryEvaluate(getLeft()))
                && (!Double.isNaN(powSimplified.tryEvaluate(getRight()))))) {
            return new Num(powSimplified.tryEvaluate(this));
        }
        // returns the expression after simplification
        return (Expression) powSimplified;
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // important termination conditions - an expression of the form x^a
        // i dont wont it to keep bringing me (1 * x^a) back... it will not end!
        if (getLeft() instanceof Var && getRight() instanceof Num) {
            return this;
        }
        if (getLeft() instanceof Plus && getRight() instanceof Num) {
            return this;
        }
        // a^Log(a, x) => x
        if (getRight() instanceof Log) {
            if (getLeft().toString().equals(((Log) getRight()).getLeft().toString())) {
                return ((Log) getRight()).getRight();
            }
        }
        // recursion
        super.setLeft(super.getLeft().toCanonical());
        super.setRight(super.getRight().toCanonical());
        // 0^x = 0
        if (tryEvaluate(getLeft()) == 0) {
            return new Num(0);
        }
        // x^0 = 1
        if (tryEvaluate(getRight()) == 0) {
            return new Num(1);
        }
        // 1^x = 1
        if (tryEvaluate(getLeft()) == 1) {
            return new Num(1);
        }
        // (a^m)^n = a^(m * n)
        if (getLeft() instanceof Pow) {
            return new Pow(((Pow) getLeft()).getLeft(), new Mult(((Pow) getLeft()).getRight(), getRight()));
        }
        // (m * x)^n => ((m*n) * x^n)
        if (getLeft() instanceof Mult) {
            return new Mult(new Pow(((Mult) getLeft()).getLeft(), getRight()),
                    new Pow(((Mult) getLeft()).getRight(), getRight()));
        }
        // default
        return this;
    }

    @Override
    public Expression collectingLikeTerms() {
        // recursive collecting like terms
        super.setLeft(super.getLeft().collectingLikeTerms());
        super.setRight(super.getRight().collectingLikeTerms());
        return this;
    }

    @Override
    public Expression algebraMagic() {
        // recursion
        super.setLeft(super.getLeft().algebraMagic());
        super.setRight(super.getRight().algebraMagic());
        // x^1 = x
        if (tryEvaluate(getRight()) == 1) {
            return getLeft().copy();
        }
        return this;
    }

    @Override
    public String finalString() {
        // recursive act
        String leftS = getLeft().finalString();
        String rightS = getRight().finalString();
        // (a + b)^x -> stays as it is
        if (getLeft() instanceof Plus) {
            return ("(" + leftS + ")" + "^" + rightS);
        }
        // default: no parenthesis
        return (leftS + "^" + rightS);
    }
}
