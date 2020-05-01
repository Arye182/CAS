import java.util.Map;

/**
 * The type Mult.
 * Mult is a BinaryExpression. it has all the fields and methods of Binary by
 * inheritance.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Mult extends BinaryExpression implements Expression {
    /**
     * Instantiates a new Mult such that left and right are Expressions.
     *
     * @param left the left Expression.
     * @param right the right Expression.
     */
    Mult(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Mult such that left is an Expression and right is a Num.
     *
     * @param left the left Expression.
     * @param right the right Num.
     */
    Mult(Expression left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Mult such that left is an Expression and right is a Var.
     *
     * @param left the left Expression.
     * @param right the right Var.
     */
    Mult(Expression left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Mult such that left is a Num and right is an Expression.
     *
     * @param left the left Num.
     * @param right the right Expression.
     */
    Mult(double left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Mult such that left and right are Nums.
     *
     * @param left the left Num.
     * @param right the right Num.
     */
    Mult(double left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Mult such that left is a Num and right is a Var.
     *
     * @param left the left Num.
     * @param right the right Var.
     */
    Mult(double left, String right) {
        super(left, right);
    }

    /**
     * Instantiates a new Mult such that left is a Var and right is an Expression.
     *
     * @param left the left Var.
     * @param right the right Expression.
     */
    Mult(String left, Expression right) {
        super(left, right);
    }

    /**
     * Instantiates a new Mult such that left is a Var and right is a Num.
     *
     * @param left the left Var.
     * @param right the right Num.
     */
    Mult(String left, double right) {
        super(left, right);
    }

    /**
     * Instantiates a new Mult such that left and right are Vars.
     *
     * @param left the left Var.
     * @param right the right Var.
     */
    Mult(String left, String right) {
        super(left, right);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // calculate recursively sides and return multiplication.
        return (super.getLeft().evaluate(assignment) * super.getRight().evaluate(assignment));
    }

    @Override
    public String toString() {
        // recursion
        String leftS = super.getLeft().toString();
        String rightS = super.getRight().toString();
        // the string
        return ("(" + leftS + " * " + rightS + ")");
    }

    @Override
    public Expression copy() {
        // return new Mult (Copy)
        return new Mult(getLeft().copy(), getRight().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // (f * g)' = f'g + g'f
        return new Plus(new Mult(super.getLeft(), super.getRight().differentiate(var)),
                new Mult(super.getLeft().differentiate(var), super.getRight()));
    }

    @Override
    public Expression simplify() {
        // creating new mult expression
        BinaryExpression multsSimplified = (BinaryExpression) this.copy();
        // recursion
        multsSimplified.setLeft(multsSimplified.getLeft().simplify());
        multsSimplified.setRight(multsSimplified.getRight().simplify());
        // 1 * x = x
        if (multsSimplified.tryEvaluate(multsSimplified.getLeft()) == 1) {
            return multsSimplified.getRight().simplify();
        }
        if (multsSimplified.tryEvaluate(multsSimplified.getRight()) == 1) {
            return multsSimplified.getLeft().simplify();
        }
        // x * 0 = 0
        if (multsSimplified.tryEvaluate(multsSimplified.getRight()) == 0
                || multsSimplified.tryEvaluate(multsSimplified.getLeft()) == 0) {
            return new Num(0);
        }
        // if both sides are actual numbers - i want to calculate.
        if ((!Double.isNaN(multsSimplified.tryEvaluate(getLeft()))
                && (!Double.isNaN(multsSimplified.tryEvaluate(getRight()))))) {
            return new Num(multsSimplified.tryEvaluate(getLeft()) * multsSimplified.tryEvaluate(getRight()));
        }
        return (Expression) multsSimplified;
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // important termination condition - an expression of the form a * x^b
        // i dont wont it to keep bringing me (1 * x^2) back... it will not end!
        if (getLeft() instanceof Num
                && getRight() instanceof Pow
                && !(((Pow) getRight()).getLeft() instanceof Pow)) {
            return this;
        }
        // recursive call
        super.setLeft(super.getLeft().toCanonical());
        super.setRight(super.getRight().toCanonical());
        // following are all the Mult advanced simplifications:
        // x * 4 => 4 * x
        if (getRight() instanceof Num) {
            return new Mult(getRight(), getLeft());
        }
        // 4 * (4 * x) => (4 * 4) * x
        if (getLeft() instanceof Num && getRight() instanceof Mult) {
            if (((Mult) getRight()).getLeft() instanceof Num) {
                return new Mult(new Mult(getLeft(), ((Mult) getRight()).getLeft()), ((Mult) getRight()).getRight());
            }
        }
        // (4 * 4) * x => (16) * x
        if (getLeft() instanceof Mult) {
            if (((Mult) getLeft()).getLeft() instanceof Num && ((Mult) getLeft()).getRight() instanceof Num) {
                Num calculation = new Num(tryEvaluate(getLeft()));
                return new Mult(calculation, getRight());
            }
        }
        // (a * b) * b => (a * b^2)
        if (getLeft() instanceof Mult) {
            if (getRight().toString().equals(((Mult) getLeft()).getRight().toString())) {
                return new Mult(((Mult) getLeft()).getLeft(), new Pow(getRight(), 2));
            }
            if (getRight().toString().equals(((Mult) getLeft()).getLeft().toString())) {
                return new Mult(((Mult) getLeft()).getRight(), new Pow(getRight(), 2));
            }
        }
        // b * (a * b) => (a * b^2)
        if (getRight() instanceof Mult) {
            if (getLeft().toString().equals(((Mult) getRight()).getRight().toString())) {
                return new Mult(((Mult) getRight()).getLeft(), new Pow(getLeft(), 2));
            }
            if (getLeft().toString().equals(((Mult) getRight()).getLeft().toString())) {
                return new Mult(((Mult) getRight()).getRight(), new Pow(getLeft(), 2));
            }
        }
        // (a * b^m) * b^n => (a * b^(m+n))
        if (getLeft() instanceof Mult && getRight() instanceof Pow) {
            if (((Mult) getLeft()).getRight() instanceof Pow) {
                if (((Pow) ((Mult) getLeft()).getRight()).getLeft().toString().equals(((Pow)
                        getRight()).getLeft().toString())) {
                    return new Mult(((Mult) getLeft()).getLeft(), new Pow(((Pow) getRight()).getLeft(),
                            new Plus(((Pow) ((Mult) getLeft()).getRight()).getRight(), ((Pow) getRight()).getRight())));
                }
            }
            // (b^m * a) * b^n => (a * b^(m+n))
            if (((Mult) getLeft()).getLeft() instanceof Pow) {
                if (((Pow) ((Mult) getLeft()).getLeft()).getLeft().toString().equals(((Pow)
                        getRight()).getLeft().toString())) {
                    return new Mult(((Mult) getLeft()).getRight(), new Pow(((Pow) getRight()).getLeft(),
                            new Plus(((Pow) ((Mult) getLeft()).getLeft()).getRight(), ((Pow) getRight()).getRight())));
                }
            }
        }
        // b^n * (a * b^m) => (a * b^(m+n))
        if (getRight() instanceof Mult && getLeft() instanceof Pow) {
            if (((Mult) getRight()).getRight() instanceof Pow) {
                if (((Pow) getLeft()).getLeft().toString().equals(((Pow)
                        ((Mult) getRight()).getRight()).getLeft().toString())) {
                    return new Mult(((Mult) getRight()).getLeft(), new Pow(((Pow) getLeft()).getLeft(),
                            new Plus(((Pow) getLeft()).getRight(), ((Pow) ((Mult) getRight()).getRight()).getRight())));
                }
            }
            // b^n * (b^m * a) => (a * b^(m+n))
            if (((Mult) getRight()).getLeft() instanceof Pow) {
                if (((Pow) getLeft()).getLeft().toString().equals(((Pow) ((Mult)
                        getRight()).getLeft()).getLeft().toString())) {
                    return new Mult(((Mult) getRight()).getRight(), new Pow(((Pow) getLeft()).getLeft(),
                            new Plus(((Pow) getLeft()).getRight(), ((Pow) ((Mult) getRight()).getLeft()).getRight())));
                }
            }
        }
        // (a * c^m) * (b * c^n) => ((a * b) * c^(m+n))
        if (getLeft() instanceof Mult && getRight() instanceof Mult) {
            if (((Mult) getLeft()).getRight() instanceof Pow && ((Mult) getRight()).getRight() instanceof Pow) {
                if (((Pow) ((Mult) getLeft()).getRight()).getLeft().toString().equals(((Pow)
                        ((Mult) getRight()).getRight()).getLeft().toString())) {
                    return new Mult(new Mult(((Mult) getLeft()).getLeft(), ((Mult) getRight()).getLeft()),
                            new Pow(((Pow) ((Mult) getLeft()).getRight()).getLeft(),
                                    new Plus(((Pow) ((Mult) getLeft()).getRight()).getRight(),
                                            ((Pow) ((Mult) getRight()).getRight()).getRight())));
                }
            }
        }
        // distributive laws
        // (Num1 + Exp1) * Exp2 = > (Num1 * Exp2) + (Exp1 * Exp2)
        if (getLeft() instanceof Plus) {
            return new Plus(new Mult(getRight(), ((Plus) getLeft()).getLeft()),
                    new Mult(getRight(), ((Plus) getLeft()).getRight()));
        }
        // same as above just Exp2 is on left.
        if (getRight() instanceof Plus) {
            return new Plus(new Mult(getLeft(), ((Plus) getRight()).getRight()),
                    new Mult(getLeft(), ((Plus) getRight()).getLeft()));
        }
        // (Num1 - Exp1) * Exp2 = > (Num1 * Exp2) - (Exp1 * Exp2)
        if (getLeft() instanceof Minus) {
            return new Minus(new Mult(getRight(), ((Minus) getLeft()).getLeft()),
                    new Mult(getRight(), ((Minus) getLeft()).getRight()));
        }
        // same as above just Exp2 is on left.
        if (getRight() instanceof Minus) {
            return new Minus(new Mult(getLeft(), ((Minus) getRight()).getRight()),
                    new Mult(getLeft(), ((Minus) getRight()).getLeft()));
        }
        // x * x => x^2
        if (getLeft().toString().equals(getRight().toString())) {
            return new Pow(getLeft(), 2);
        }
        // a^m * b^m => (ab)^m
        if (getLeft() instanceof Pow && getRight() instanceof Pow) {
            if (((Pow) getLeft()).getRight().toString().equals(((Pow) getRight()).getRight().toString())) {
                return new Pow(new Mult(((Pow) getLeft()).getLeft(), ((Pow)
                        getRight()).getLeft()), ((Pow) getLeft()).getRight());
            }
        }
        // -1 * x = -x
        if (tryEvaluate(super.getLeft()) == -1) {
            //return new Mult(new Num(-1), super.getRight().simplify());
            return new Neg(super.getRight().simplify());
        }
        // a^m * a^n => a^(m + n)
        if (getLeft() instanceof Pow && getRight() instanceof Pow) {
            if (((Pow) getLeft()).getLeft().toString().equals(((Pow) getRight()).getLeft().toString())) {
                return new Pow(((Pow) getLeft()).getLeft(), new Plus(((Pow)
                        getLeft()).getRight(), ((Pow) getRight()).getRight()));
            }
        }
        // default
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
        // 2sin(a)cos(a) => sin(2a)
        if (getLeft() instanceof Mult && getRight() instanceof Cos) {
            if (tryEvaluate(((Mult) getLeft()).getLeft()) == 2 && ((Mult) getLeft()).getRight() instanceof Sin) {
                if (((Sin) ((Mult) getLeft()).getRight()).getExp().toString().equals(((Cos)
                        getRight()).getExp().toString())) {
                    return new Sin(new Mult(2, ((Cos) getRight()).getExp()));
                }
            }
        }
        return this;
    }

    @Override
    public String finalString() {
        // recursion
        String left = getLeft().finalString();
        String right = getRight().finalString();
        // 4 * x => 4x
        if (getLeft() instanceof Num && getRight() instanceof Var) {
            return (left + right);
        }
        // default
        return (left + right);
    }
}
