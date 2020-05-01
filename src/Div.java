import java.util.List;
import java.util.Map;

/**
 * The type Div.
 * Div is a BinaryExpression. it has all the fields and methods of Binary by
 * inheritance.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Div extends BinaryExpression implements Expression {
    /**
     * Instantiates a new Div such that left and right are Expressions.
     *
     * @param left  the left Expression
     * @param right the right Expression
     */
    Div(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Div such that left is an Expression and right is a Num.
     *
     * @param left  the Expression.
     * @param right the Num.
     */
    Div(Expression left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Div such that left is an Expression and right is a Var.
     *
     * @param left  the Expression.
     * @param right the Var.
     */
    Div(Expression left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Div such that left is a Num and right is an Expression.
     *
     * @param left  the left Num.
     * @param right the right Expression.
     */
    Div(double left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Div such that left and right are Num.
     *
     * @param left  the left Num.
     * @param right the right Num.
     */
    Div(double left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Div such that left is a Num and right is a Var.
     *
     * @param left  the left Num.
     * @param right the right Var.
     */
    Div(double left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Div such that left is a Var and right is an Expression.
     *
     * @param left  the left Var.
     * @param right the right Expression.
     */
    Div(String left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Div such that left is a Var and right is a Num.
     *
     * @param left  the left Var.
     * @param right the right Num.
     */
    Div(String left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Div such that left and right are Vars.
     *
     * @param left  the left Var.
     * @param right the right Var.
     */
    Div(String left, String right) {
        super(left, right);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // Exception - dividing by 0
        if (super.tryEvaluate(super.getRight()) == 0) {
            throw new Exception("Illegal Math Action - Divide by 0");
        }
        // return evaluation recursively.
        return (super.getLeft().evaluate(assignment) / super.getRight().evaluate(assignment));
    }

    @Override
    public String toString() {
        // recursive bring the left and right strings
        String leftS = super.getLeft().toString();
        String rightS = super.getRight().toString();
        // bring back the final string...
        return ("(" + leftS + " / " + rightS + ")");
    }

    @Override
    public Expression copy() {
        // returning new Div with recursive copies
        return new Div(super.getLeft().copy(), super.getRight().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // (f/g)' = f'g - g'f / g^2
        return new Div(new Minus(new Mult(super.getLeft().differentiate(var), super.getRight()),
                new Mult(super.getLeft(), super.getRight().differentiate(var))), new Pow(super.getRight(), 2));
    }

    @Override
    public Expression simplify() {
        // creating new div expression
        BinaryExpression divSimplified = (BinaryExpression) this.copy();
        // recursive simplification on left and right
        divSimplified.setLeft(divSimplified.getLeft().simplify());
        divSimplified.setRight(divSimplified.getRight().simplify());
        // x / 1 = x
        if (divSimplified.tryEvaluate(divSimplified.getRight()) == 1) {
            return divSimplified.getLeft().simplify();
        }
        // 0 / x = 0
        if (divSimplified.tryEvaluate(divSimplified.getLeft()) == 0) {
            return new Num(0);
        }
        // x / x = 1
        if (divSimplified.getLeft().toString().equals(divSimplified.getRight().toString())) {
            return new Num(1);
        }
        // if both sides are actual numbers - i want to calculate.
        if ((!Double.isNaN(divSimplified.tryEvaluate(getLeft()))
                && (!Double.isNaN(divSimplified.tryEvaluate(getRight()))))) {
            return new Num(divSimplified.tryEvaluate((Expression) divSimplified));
        }
        // default
        return (Expression) divSimplified;
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // recursion
        super.setLeft(super.getLeft().toCanonical());
        super.setRight(super.getRight().toCanonical());
        // x / 5 => ((1 / 5) * x)
        if (getRight() instanceof Num) {
            return new Mult(new Div(1, getRight()), getLeft());
        }
        // (a / b) / (c / d) => (a * d) / (c * b)
        if (getRight() instanceof Div && getLeft() instanceof Div) {
            return new Div(new Mult(((Div) getLeft()).getLeft(), ((Div) getRight()).getRight()),
                    new Mult(((Div) getLeft()).getRight(), ((Div) getRight()).getLeft()));
        }
        // a^m / a^n => a^(m - n)
        if (getLeft() instanceof Pow && getRight() instanceof Pow) {
            if (((Pow) getLeft()).getLeft().toString().equals(((Pow) getRight()).getLeft().toString())) {
                return new Pow(((Pow) getLeft()).getLeft(),
                        new Minus(((Pow) getLeft()).getRight(), ((Pow) getRight()).getRight()));
            }
        }
        // 1 / a^m => a^(-m)
        if (tryEvaluate(getLeft()) == 1 && getRight() instanceof Pow) {
            return new Pow(((Pow) getRight()).getLeft(), new Neg(((Pow) getRight()).getRight()));
        }
        // default
        return this;
    }

    @Override
    public Expression collectingLikeTerms() {
        // recursion calculation
        super.setLeft(super.getLeft().collectingLikeTerms());
        super.setRight(super.getRight().collectingLikeTerms());
        // creating lists for left and right of Div
        List<Expression> numerator = getLeft().toLista();
        List<Expression> denumerator = getRight().toLista();
        // only if numerator is all * and denominator as well - in this part I
        // take this as obvious i don't support other cases in this version.
        for (int i = 0; i < numerator.size(); i++) {
            for (int j = 0; j < denumerator.size(); j++) {
                // setting up the runners
                Expression runnerI = numerator.get(i);
                Expression runnerJ = denumerator.get(j);
                // skip condition
                if (runnerI == null || runnerJ == null) {
                    continue;
                }
                // both are just powers
                if (runnerI instanceof Pow && runnerJ instanceof Pow) {
                    if (((Pow) runnerI).getLeft().toString().equals(((Pow) runnerJ).getLeft().toString())) {
                        // case the degree up is low then down
                        if (tryEvaluate(((Pow) runnerI).getRight()) < tryEvaluate(((Pow) runnerJ).getRight())) {
                            numerator.set(i, new Num(1));
                            denumerator.set(j, new Pow(((Pow) runnerJ).getLeft(),
                                    new Minus(((Pow) runnerJ).getRight(), ((Pow) runnerI).getRight())));
                        }
                        // in case degree down is lower then up
                        if (tryEvaluate(((Pow) runnerI).getRight()) > tryEvaluate(((Pow) runnerJ).getRight())) {
                            numerator.set(i, new Pow(((Pow) runnerI).getLeft(),
                                    new Minus(((Pow) runnerI).getRight(), ((Pow) runnerJ).getRight())));
                            denumerator.set(j, new Num(1));
                        }
                        // case there is the same degree and same base to power - it all goes away
                        if (tryEvaluate(((Pow) runnerI).getRight()) == tryEvaluate(((Pow) runnerJ).getRight())) {
                            numerator.set(i, new Num(1));
                            denumerator.set(j, new Num(1));
                        }
                    }
                }
                // down is pow and up is a mult such that the right side of mult is pow
                if (runnerI instanceof Mult && ((Mult) runnerI).getRight() instanceof Pow && runnerJ instanceof Pow) {
                    if (((Pow) ((Mult) runnerI).getRight()).getLeft().toString().equals(((Pow)
                            runnerJ).getLeft().toString())) {
                        if (tryEvaluate(((Pow) ((Mult) runnerI).getRight()).getRight())
                                < tryEvaluate(((Pow) runnerJ).getRight())) {
                            numerator.set(i, ((Mult) runnerI).getLeft());
                            denumerator.set(j, new Pow(((Pow) runnerJ).getLeft(),
                                    new Minus(((Pow) runnerJ).getRight(),
                                            (((Pow) ((Mult) runnerI).getRight()).getRight()))));
                        }
                        if (tryEvaluate(((Pow) ((Mult) runnerI).getRight()).getRight())
                                > tryEvaluate(((Pow) runnerJ).getRight())) {
                            numerator.set(i, new Pow(((Pow) runnerJ).getLeft(),
                                    new Minus((((Pow) ((Mult) runnerI).getRight()).getRight()),
                                            ((Pow) runnerJ).getRight())));
                            denumerator.set(j, new Num(1));
                        }
                        if (tryEvaluate(((Pow) ((Mult) runnerI).getRight()).getRight())
                                == tryEvaluate(((Pow) runnerJ).getRight())) {
                            numerator.set(i, ((Mult) runnerI).getLeft());
                            denumerator.set(j, new Num(1));
                        }
                    }
                }
                // both down and up are mults so the right side of them is pow
                if (runnerI instanceof Mult && runnerJ instanceof Mult) {
                    if (((Mult) runnerI).getRight() instanceof Pow && ((Mult) runnerJ).getRight() instanceof Pow) {
                        if (((Pow) ((Mult) runnerI).getRight()).getRight().toString().equals(((Pow)
                                ((Mult) runnerJ).getRight()).getRight().toString())) {
                            double degUp = tryEvaluate(((Pow) ((Mult) runnerI).getRight()).getRight());
                            double degDown = tryEvaluate(((Pow) ((Mult) runnerJ).getRight()).getRight());
                            // a * x^2 / b * x^2
                            if (degUp == degDown) {
                                numerator.set(i, ((Mult) runnerI).getLeft());
                                denumerator.set(j, ((Mult) runnerJ).getLeft());
                            }
                            // a * x^2 / b * x^4
                            if (degUp < degDown) {
                                numerator.set(i, ((Mult) runnerI).getLeft());
                                denumerator.set(j, new Mult(((Mult) runnerJ).getLeft(),
                                        new Pow(((Pow) ((Mult) runnerJ).getRight()).getLeft(),
                                        new Minus(((Pow) ((Mult) runnerI).getRight()).getRight(),
                                                ((Pow) ((Mult) runnerJ).getRight()).getRight()))));
                            }
                            // a * x^4 / b * x^2
                            if (degUp > degDown) {
                                denumerator.set(j, ((Mult) runnerJ).getLeft());
                                numerator.set(i, new Mult(((Mult) runnerI).getLeft(),
                                        new Pow(((Pow) ((Mult) runnerI).getRight()).getLeft(),
                                                new Minus(((Pow) ((Mult) runnerJ).getRight()).getRight(),
                                                        ((Pow) ((Mult) runnerI).getRight()).getRight()))));
                            }
                        }
                    }
                }
                // down is mult with right side pow and up is pow
                if (runnerI instanceof Pow && runnerJ instanceof Mult) {
                    if (((Mult) runnerJ).getRight() instanceof Pow) {
                        double degUp = tryEvaluate(((Pow) runnerI).getRight());
                        double degDown = tryEvaluate(((Pow) ((Mult) runnerJ).getRight()).getRight());
                        // a * x^2 / b * x^2
                        if (degUp == degDown) {
                            numerator.set(i, ((Mult) runnerI).getLeft());
                            denumerator.set(j, ((Mult) runnerJ).getLeft());
                        }
                        // a * x^2 / b * x^4
                        if (degUp < degDown) {
                            numerator.set(i, ((Mult) runnerI).getLeft());
                            denumerator.set(j, new Mult(((Mult) runnerJ).getLeft(),
                                    new Pow(((Pow) ((Mult) runnerJ).getRight()).getLeft(),
                                            new Minus(((Pow) runnerI).getRight(),
                                                    ((Pow) ((Mult) runnerJ).getRight()).getRight()))));
                        }
                        // a * x^4 / b * x^2
                        if (degUp > degDown) {
                            denumerator.set(j, ((Mult) runnerJ).getLeft());
                            numerator.set(i, new Pow(((Pow) runnerI).getLeft(),
                                    new Minus(((Pow) runnerI).getRight(),
                                            ((Pow) ((Mult) runnerJ).getRight()).getRight())));
                        }
                    }
                }
            }
        }
        // building the expression back from the list
        Expression newNumerator = numerator.get(0);
        Expression newDenumerator = denumerator.get(0);
        // building the Numerator
        if (numerator.size() >= 2) {
            newNumerator = new Mult(numerator.get(0), denumerator.get(1));
            for (int i = 2; i < numerator.size(); i++) {
                newNumerator = new Mult(newNumerator, numerator.get(i));
            }
        }
        // building the Denominator
        if (denumerator.size() >= 2) {
            newDenumerator = new Mult(denumerator.get(0), denumerator.get(1));
            for (int i = 2; i < denumerator.size(); i++) {
                newDenumerator = new Mult(newDenumerator, denumerator.get(i));
            }
        }
        // returning the Div Expression after "Collecting Like Terms" Phase
        return new Div(newNumerator, newDenumerator);
    }

    @Override
    public Expression algebraMagic() {
        // recursive calls left and right
        super.setLeft(super.getLeft().algebraMagic());
        super.setRight(super.getRight().algebraMagic());
        return this;
    }

    @Override
    public String finalString() {
        // recursive call left and right
        String leftS = super.getLeft().finalString();
        String rightS = super.getRight().finalString();
        // no need for parenthesis
        return (leftS + " / " + rightS);
    }
}
