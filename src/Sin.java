import java.util.Map;

/**
 * this class is for the Sin type - is an unary Expression.
 * it has the unary expression methods and also field of expression.
 * it also implements the Expression interface.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Sin extends UnaryExpression implements Expression {
    /**
     * Instantiates a new Sin such that the Expression inside is an Expression.
     *
     * @param e the Expression.
     */
    Sin(Expression e) {
        super(e);
    }

    /**
     * Instantiates a new Sin such that the Expression inside is a Var.
     *
     * @param var the Var.
     */
    Sin(String var) {
        super(var);
    }

    /**
     * Instantiates a new Sin such that the Expression inside is a Num.
     *
     * @param num the Num.
     */
    Sin(double num) {
        super(num);
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // return the sin result (math.sin)
        return (Math.sin(super.getExp().evaluate(assignment)));
    }

    @Override
    public String toString() {
        // recursion on the inner expression
        String s = super.getExp().toString();
        return ("sin(" + s + ")");
    }

    @Override
    public Expression copy() {
        // returns a new sin expression (copy)
        return new Sin(super.getExp().copy());
    }

    @Override
    public Expression differentiate(String var) {
        // (sin(u))' = u' * cos (u)
        return new Mult(new Cos(super.getExp()), super.getExp().differentiate(var));
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // sin(-x) => -sin(x)
        if (getExp() instanceof Neg) {
            return new Mult(-1, new Sin(((Neg) getExp()).getExp()));
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
        super.setExp(super.getExp().algebraMagic());
        return this;
    }

    @Override
    public String finalString() {
        String s = super.getExp().finalString();
        return ("sin(" + s + ")");
    }
}
