import java.util.Map;

/**
 * The type Cos.
 * this class is for the Cos type - is an unary Expression.
 * it has the unary expression methods and also field of expression.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Cos extends UnaryExpression implements Expression {
    /**
     * Instantiates a new Cos such that the expression inside is an Expression.
     *
     * @param e the Expression.
     */
    Cos(Expression e) {
        super(e);
    }

    /**
     * Instantiates a new Cos such that the expression inside is a Var.
     *
     * @param exp the Var.
     */
    Cos(String exp) {
        super(exp);
    }

    /**
     * Instantiates a new Cos such that the expression inside is a Num.
     *
     * @param exp the Num (double will be converted to new Num).
     */
    Cos(double exp) {
        super(exp);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // returning the evaluated expression also with inner recursive call.
        return (Math.cos(super.getExp().evaluate(assignment)));
    }

    @Override
    public String toString() {
        // recursive call the string inside
        String s = super.getExp().toString();
        // returning final string
        return ("cos(" + s + ")");
    }

    @Override
    public Expression copy() {
        // returning a new copy of the expression (new Cos)
        return new Cos(super.getExp().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // (Sin(f(x))' = -Cos(f(x))*(f'(x)
        return new Mult(new Neg(new Sin(super.getExp())), super.getExp().differentiate(var));
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // cos(-x) => cos(x)
        if (getExp() instanceof Neg) {
            return new Cos(((Neg) getExp()).getExp());
        }
        // recursion
        super.setExp(super.getExp().toCanonical());
        return this;
    }

    @Override
    public Expression collectingLikeTerms() {
        // recursion
        super.setExp(super.getExp().collectingLikeTerms());
        return this;
    }

    @Override
    public Expression algebraMagic() {
        // recursion
        super.setExp(super.getExp().algebraMagic());
        return this;
    }

    @Override
    public String finalString() {
        // recursion
        String s = super.getExp().finalString();
        return ("cos(" + s + ")");
    }
}
