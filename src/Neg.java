import java.util.Map;

/**
 * The type Neg.
 * Neg is an UnaryExpression. it has all the fields and methods of Unary by
 * inheritance.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Neg extends UnaryExpression implements Expression {
    /**
     * Instantiates a new Neg such that field inside is an Expression.
     *
     * @param e the Expression.
     */
    Neg(Expression e) {
        super(e);
    }

    /**
     * Instantiates a new Neg such that field inside is a Var.
     *
     * @param var the Var.
     */
    Neg(String var) {
        super(var);
    }

    /**
     * Instantiates a new Neg such that the field inside is a Num.
     *
     * @param num the Num.
     */
    Neg(double num) {
        super(num);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // returns -1 * expression.
        return (super.getExp().evaluate(assignment) * (-1));
    }

    @Override
    public String toString() {
        // according to the rules of string representation: (-x)
        return ("(-" + super.getExp().toString() + ")");
    }

    @Override
    public Expression copy() {
        // returns a new Neg (copy)
        return new Neg(super.getExp().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // (-f)' = -(f')
        return new Neg(super.getExp().differentiate(var));
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // (-0) => 0
        if (tryEvaluate(getExp()) == 0) {
            return new Num(0);
        }
        // - (Num * Exp) => ((-1*Num) * Exp)
        if (super.getExp() instanceof Mult) {
            if (((Mult) getExp()).getLeft() instanceof Num) {
                return new Mult(new Num(-1 * tryEvaluate(((Mult) getExp()).getLeft())),
                        ((Mult) getExp()).getRight());
            }
        }
        // - (Exp) => (-1 * Exp.....)
        if (!(getExp() instanceof Var)) {
            return new Mult(new Num(-1), super.getExp().toCanonical());
        }
        // default: turns into multiplication: -(Var) ====> -(x) => (-1 * x)
        return new Mult(new Num(-1), super.getExp());
    }

    @Override
    public Expression collectingLikeTerms() {
        return this;
    }

    @Override
    public Expression algebraMagic() {
        super.setExp(super.getExp().algebraMagic());
        return this;
    }

    @Override
    public String finalString() {
        return ("-" + super.getExp().finalString());
    }
}
