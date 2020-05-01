import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The type Plus.
 * Plus is a BinaryExpression. it has all the fields and methods of Binary by
 * inheritance.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Plus extends BinaryExpression implements Expression {
    /**
     * Instantiates a new Plus such that left and right are Expressions.
     *
     * @param left the left Expression.
     * @param right the right Expression.
     */
    Plus(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Plus such that left is an Expression and right is a Num.
     *
     * @param left the left Expression.
     * @param right the right Num.
     */
    Plus(Expression left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Plus such that left is an Expression and right is a Var.
     *
     * @param left the left Expression.
     * @param right the right Var.
     */
    Plus(Expression left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Plus such that left is a Num and right is an Expression.
     *
     * @param left the left Num.
     * @param right the right Expression.
     */
    Plus(double left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Plus such that left and right are Nums.
     *
     * @param left the left Num.
     * @param right the right Num.
     */
    Plus(double left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Plus such that left is a Num and right is a Var.
     *
     * @param left the left Num.
     * @param right the right Var.
     */
    Plus(double left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Plus such that left is a Var and right is an Expression.
     *
     * @param left the left Var.
     * @param right the right Expression.
     */
    Plus(String left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Plus such that left is a Var and right is a Num.
     *
     * @param left the left Var.
     * @param right the right Num.
     */
    Plus(String left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Plus such that left and right are Vars.
     *
     * @param left the left Var.
     * @param right the right Var.
     */
    Plus(String left, String right) {
        super(left, right);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // calculate recursively both sides and returns the addition
        return (super.getLeft().evaluate(assignment) + super.getRight().evaluate(assignment));
    }

    @Override
    public String toString() {
        // recursive strings
        String leftS = super.getLeft().toString();
        String rightS = super.getRight().toString();
        //returns the final string
        return ("(" + leftS + " + " + rightS + ")");
    }

    @Override
    public Expression copy() {
        // returns a new copy (new Plus) of this Expression
        return new Plus(getLeft().copy(), getRight().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // (f + g)' = f' + g"
        return new Plus(getLeft().differentiate(var), getRight().differentiate(var));
    }

    @Override
    public Expression simplify() {
        // creating new plus expression
        BinaryExpression plusSimplified = (BinaryExpression) this.copy();
        // recursive simplification of sides
        plusSimplified.setLeft(plusSimplified.getLeft().simplify());
        plusSimplified.setRight(plusSimplified.getRight().simplify());
        // 0 + x = x
        if (plusSimplified.tryEvaluate(plusSimplified.getLeft()) == 0) {
            return plusSimplified.getRight();
        }
        // x + 0 = x
        if (plusSimplified.tryEvaluate(plusSimplified.getRight()) == 0) {
            return plusSimplified.getLeft();
        }
        // if both sides are actual numbers - i want to calculate.
        if ((!Double.isNaN(plusSimplified.tryEvaluate(getLeft()))
                && (!Double.isNaN(plusSimplified.tryEvaluate(getRight()))))) {
            return new Num(plusSimplified.tryEvaluate(getLeft()) + plusSimplified.tryEvaluate(getRight()));
        }
        // default
        return (Expression) plusSimplified;
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // recursive act
        super.setLeft(super.getLeft().toCanonical());
        super.setRight(super.getRight().toCanonical());
        // ((Num + Exp) + Num) => (Num + Num) + Exp
        if (getLeft() instanceof Plus && getRight() instanceof Num) {
            if (((Plus) getLeft()).getLeft() instanceof Num) {
                return new Plus(new Plus(((Plus) getLeft()).getLeft(), getRight()), ((Plus) getLeft()).getRight());
            }
        }
        // (Num + (Num + Exp)) => (Num + Num) + Exp
        if (getRight() instanceof Plus && getLeft() instanceof Num) {
            if (((Plus) getRight()).getLeft() instanceof Num) {
                return new Plus(new Plus(((Plus) getRight()).getLeft(), getLeft()), ((Plus) getLeft()).getRight());
            }
        }
        // Log(a, x) + Log(a, y) => Log(a, (x*y))
        if (getLeft() instanceof Log && getRight() instanceof Log) {
            if (((Log) getLeft()).getLeft().toString().equals(((Log) getRight()).getLeft().toString())) {
                return new Log(((Log) getLeft()).getLeft(),
                        new Mult(((Log) getLeft()).getRight(), ((Log) getRight()).getRight()));
            }
        }
        // return Expression after "toCanonical" simplification
        return this;
    }

    @Override
    public Expression collectingLikeTerms() {
        // recursive collecting like terms
        super.setLeft(super.getLeft().collectingLikeTerms());
        super.setRight(super.getRight().collectingLikeTerms());
        // puts all "+" elements of the expression in a list
        List<Expression> expressionList = this.toLista();
        // double loop searching for each element other like terms to put together
        for (int i = 0; i < expressionList.size(); i++) {
            for (int j = 0; j < expressionList.size(); j++) {
                // representative from the list
                Expression runnerI = expressionList.get(i);
                // elements from list to compare to
                Expression runnerJ = expressionList.get(j);
                // skipping in case of null or if its the same element
                if ((j == i) || (runnerI == null) || (runnerJ == null)) {
                    continue;
                }
                // this condition take care of this cases: c1 * x + c2 * x => (c1 + c2) * x
                if (runnerI instanceof Mult && runnerJ instanceof Mult) {
                    if (((Mult) runnerI).getRight().toString().equals(((Mult) runnerJ).getRight().toString())) {
                        expressionList.add(new Mult(new Plus(((Mult) runnerJ).getLeft(),
                                ((Mult) runnerI).getLeft()), ((Mult) runnerI).getRight()));
                        expressionList.set(i, null);
                        expressionList.set(j, null);
                    }
                }
                // c * x^2 + x^2 => (c + 1) * x^2
                if (runnerI instanceof Mult && runnerJ instanceof Pow) {
                    if (((Mult) runnerI).getRight().toString().equals(runnerJ.toString())) {
                        expressionList.add(new Mult(new Plus(((Mult) runnerI).getLeft(), 1), runnerJ));
                        expressionList.set(i, null);
                        expressionList.set(j, null);
                    }
                }
                // x^2 + x^2 => 2x^2
                if (runnerI instanceof Pow && runnerJ instanceof Pow) {
                    if (runnerI.toString().equals(runnerJ.toString())) {
                        expressionList.add(new Mult(2, runnerI));
                        expressionList.set(i, null);
                        expressionList.set(j, null);
                    }
                }
                // x^2 + c * x^2 => (1 + c) * x^2
                if (runnerI instanceof Pow && runnerJ instanceof Mult) {
                    if (runnerI.toString().equals(((Mult) runnerJ).getRight().toString())) {
                        expressionList.add(new Mult(new Plus(1, ((Mult) runnerJ).getLeft()), runnerI));
                        expressionList.set(i, null);
                        expressionList.set(j, null);
                    }
                }
                // 2 + 5 => 7 both element are numbers - lets put them together
                if ((!Double.isNaN(tryEvaluate(runnerI)) && (!Double.isNaN(tryEvaluate(runnerJ))))) {
                    expressionList.add(new Plus(runnerI, runnerJ));
                    expressionList.set(i, null);
                    expressionList.set(j, null);
                }
            }
        }
        // remove only the null elements from list
        expressionList.removeAll(Collections.singleton(null));
        // this is the algorithm sort elements by degree of polynomials
        // i use bubble sort to to this...
        for (int i = 0; i < expressionList.size() - 1; i++) {
            for (int j = 0; j < expressionList.size() - i - 1; j++) {
                Expression runnerI = expressionList.get(j);
                Expression runnerJ = expressionList.get(j + 1);
                // initialization the doubles
                double powOne = Double.NaN;
                double powTwo = Double.NaN;
                // runners are both Mult types
                if (runnerI instanceof Mult && runnerJ instanceof Mult
                    && ((Mult) runnerI).getRight() instanceof Pow
                    && ((Mult) runnerJ).getRight() instanceof Pow) {
                    powOne = Double.parseDouble((((Pow) ((Mult) runnerI).getRight()).getRight()).toString());
                    powTwo = Double.parseDouble(((Pow) ((Mult) runnerJ).getRight()).getRight().toString());
                }
                // if current is pow and next mult
                if (runnerI instanceof Pow && runnerJ instanceof Mult && ((Mult) runnerJ).getRight() instanceof Pow) {
                    powOne = Double.parseDouble(((Pow) runnerI).getRight().toString());
                    powTwo = Double.parseDouble(((Pow) ((Mult) runnerJ).getRight()).getRight().toString());
                }
                // if current mult and next pow
                if (runnerJ instanceof Pow && runnerI instanceof Mult) {
                    powOne = Double.parseDouble(((Pow) ((Mult) runnerI).getRight()).getRight().toString());
                    powTwo = Double.parseDouble(((Pow) runnerJ).getRight().toString());
                }
                // if both are powers
                if (runnerI instanceof Pow && runnerJ instanceof Pow) {
                    powOne = Double.parseDouble(((Pow) runnerI).getRight().toString());
                    powTwo = Double.parseDouble(((Pow) runnerJ).getRight().toString());
                }
                // do the swap of degrees
                if ((!Double.isNaN(powOne)) && (!Double.isNaN(powTwo)) && (powOne < powTwo)) {
                    Collections.swap(expressionList, j, j + 1);
                }
                // pushing all the regular numbers to the end of the list
                if (runnerI instanceof Num) {
                    Collections.swap(expressionList, j, j + 1);
                }
            }
        }
        // creating the new expression after the whole process of collecting like terms
        Expression folded = expressionList.get(0);
        if (expressionList.size() >= 2) {
            folded = new Plus(expressionList.get(0), expressionList.get(1));
            for (int i = 2; i < expressionList.size(); i++) {
                folded = new Plus(folded, expressionList.get(i));
            }
        }
        // returning the expression after collecting like terms
        return folded;
    }

    @Override
    public Expression algebraMagic() {
        // recursion
        super.setLeft(super.getLeft().algebraMagic());
        super.setRight(super.getRight().algebraMagic());
        // a2 + 2ab + b2 = (a + b)^2
        if (getLeft() instanceof Plus) {
            if (((Plus) getLeft()).getLeft() instanceof Pow && getRight() instanceof Num
                && ((Plus) getLeft()).getRight() instanceof Mult) {
                double b = Math.sqrt(tryEvaluate(getRight()));
                double b2 = tryEvaluate(((Mult) ((Plus) getLeft()).getRight()).getLeft());
                Expression a = ((Pow) ((Plus) getLeft()).getLeft()).getLeft();
                Expression a2 = ((Mult) ((Plus) getLeft()).getRight()).getRight();
                if (a.toString().equals(a2.toString()) && b == b2 / 2) {
                    return new Pow(new Plus(a, b), 2);
                }
            }
        }
        // (sin(x))^2 + (cos(x))^2 => 1
        if (getLeft() instanceof Pow && getRight() instanceof Pow) {
            if (((Pow) getLeft()).getLeft() instanceof Sin && ((Pow) getRight()).getLeft() instanceof Cos) {
                if (((Sin) ((Pow) getLeft()).getLeft()).getExp().toString().equals(((Cos)
                        ((Pow) getRight()).getLeft()).getExp().toString())) {
                   if (tryEvaluate(((Pow) getLeft()).getRight()) == tryEvaluate(((Pow)
                           getRight()).getRight()) && tryEvaluate(((Pow) getRight()).getRight()) == 2) {
                       return new Num(1);
                   }
                }
            }
        }
        // sin(a)cos(b) + sin(b)cos(a) => sin(a + b)
        if (getLeft() instanceof Mult && getRight() instanceof Mult) {
            if (((Mult) getLeft()).getLeft() instanceof Sin
                    && ((Mult) getRight()).getLeft() instanceof Sin
                    && ((Mult) getLeft()).getRight() instanceof Cos
                    && ((Mult) getRight()).getRight() instanceof Cos) {
                if ((((Sin) ((Mult) getLeft()).getLeft()).getExp().toString().equals(((Cos)
                        ((Mult) getRight()).getRight()).getExp().toString()))
                        && ((Cos) ((Mult) getLeft()).getRight()).getExp().toString().equals(((Sin)
                        ((Mult) getRight()).getLeft()).getExp().toString())) {
                    return new Sin(new Plus(((Sin) ((Mult) getLeft()).getLeft()).getExp(),
                            ((Cos) ((Mult) getLeft()).getRight()).getExp()));
                }
            }
        }
        // sin(a)cos(b) + (-sin(b)cos(a)) => sin(a - b)
        if (getLeft() instanceof Mult && getRight() instanceof Neg && ((Neg) getRight()).getExp() instanceof Mult) {
            if (((Mult) getLeft()).getLeft() instanceof Sin
                    && ((Mult) getLeft()).getRight() instanceof Cos
                    && ((Mult) ((Neg) getRight()).getExp()).getLeft() instanceof Sin
                    && ((Mult) ((Neg) getRight()).getExp()).getRight() instanceof Cos) {
                if (((Sin) ((Mult) getLeft()).getLeft()).getExp().toString().equals(((Cos) ((Mult)
                        ((Neg) getRight()).getExp()).getRight()).getExp().toString())
                        && ((Cos) ((Mult) getLeft()).getRight()).getExp().toString().equals(((Sin) ((Mult)
                        ((Neg) getRight()).getExp()).getLeft()).getExp().toString())) {
                    return new Sin(new Minus(((Sin) ((Mult) getLeft()).getLeft()).getExp(),
                            ((Cos) ((Mult) getLeft()).getRight()).getExp()));
                }
            }
        }
        // cos(a)cos(b) + (-(sin(a)sin(b)) => cos(a + b)
        if (getLeft() instanceof Mult && getRight() instanceof Neg && ((Neg) getRight()).getExp() instanceof Mult) {
            if (((Mult) getLeft()).getLeft() instanceof Cos
                    && ((Mult) getLeft()).getRight() instanceof Cos
                    && ((Mult) ((Neg) getRight()).getExp()).getLeft() instanceof Sin
                    && ((Mult) ((Neg) getRight()).getExp()).getRight() instanceof Sin) {
                if (((Cos) ((Mult) getLeft()).getLeft()).getExp().toString().equals(((Sin) ((Mult)
                        ((Neg) getRight()).getExp()).getLeft()).getExp().toString())
                        && ((Cos) ((Mult) getLeft()).getRight()).getExp().toString().equals(((Sin) ((Mult)
                        ((Neg) getRight()).getExp()).getRight()).getExp().toString())) {
                    return new Cos(new Plus(((Cos) ((Mult) getLeft()).getLeft()).getExp(),
                            ((Cos) ((Mult) getLeft()).getRight()).getExp()));
                }
            }
        }
        // cos(a)cos(b) + sin(a)sin(b) => cos(a - b)
        if (getLeft() instanceof Mult && getRight() instanceof Mult) {
            if (((Mult) getLeft()).getLeft() instanceof Cos
                    && ((Mult) getRight()).getLeft() instanceof Sin
                    && ((Mult) getLeft()).getRight() instanceof Cos
                    && ((Mult) getRight()).getRight() instanceof Sin) {
                if ((((Sin) ((Mult) getLeft()).getLeft()).getExp().toString().equals(((Sin)
                        ((Mult) getRight()).getLeft()).getExp().toString()))
                        && ((Cos) ((Mult) getLeft()).getRight()).getExp().toString().equals(((Sin)
                        ((Mult) getRight()).getRight()).getExp().toString())) {
                    return new Cos(new Minus(((Cos) ((Mult) getLeft()).getLeft()).getExp(),
                            ((Sin) ((Mult) getRight()).getRight()).getExp()));
                }
            }
        }
        // (cos(x))^2 + (-(sin(x))^2) => cos(2x)
        if (getLeft() instanceof Pow
                && ((Pow) getLeft()).getLeft() instanceof Cos
                && tryEvaluate(((Pow) getLeft()).getRight()) == 2
                && getRight()instanceof Neg
                && ((Neg) getRight()).getExp() instanceof Pow
                && ((Pow) ((Neg) getRight()).getExp()).getLeft() instanceof Sin
                && tryEvaluate(((Pow) ((Neg) getRight()).getExp()).getRight()) == 2
                && ((Cos) ((Pow) getLeft()).getLeft()).getExp().toString().equals(((Sin)
                ((Pow) ((Neg) getRight()).getExp()).getLeft()).getExp().toString())) {
            return new Cos(new Mult(2, ((Cos) ((Pow) getLeft()).getLeft()).getExp()));
        }
        return this;
    }

    @Override
    public String finalString() {
        // recursion call
        String leftS = super.getLeft().finalString();
        String rightS = super.getRight().finalString();
        // x + (-5) => x - 5
        if (tryEvaluate(getRight()) < 0) {
            setRight(new Num(Math.abs(tryEvaluate(getRight()))));
            rightS = super.getRight().finalString();
            return (leftS + " - " + rightS);
        }
        // x + -5 => x - 5
        if (getRight() instanceof Mult) {
            if (tryEvaluate(((Mult) getRight()).getLeft()) < 0) {
                ((Mult) getRight()).setLeft(new Num(Math.abs(tryEvaluate(((Mult) getRight()).getLeft()))));
                rightS = super.getRight().finalString();
                return (leftS + " - " + rightS);
            }
        }
        // final string
        return (leftS + " + " + rightS);
    }
}
