import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Var.
 * this class implements the Expression interface, and does not inherit anything
 * from anyone.
 * in most cases it will be a termination condition for the recursion in our
 * Expression tree.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public class Var implements Expression {
    // field - the Var (string)
    private String varChar;

    /**
     * Instantiates a new Var.
     *
     * @param strVar the Var String.
     */
    public Var(String strVar) {
        this.varChar = strVar;
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // map has the var as a key
        if (assignment.containsKey(this.varChar)) {
            return assignment.get(varChar);
            // cannot assign empty map
        } else if (assignment.isEmpty()) {
            throw new Exception("Assignment is empty");
        } else {
            // map does not include the var as a key
            throw new Exception("Assignment and Expression mismatch variables");
        }
    }

    @Override
    public double evaluate() throws Exception {
        // var cannot be evaluated without a map
        throw new Exception("Illegal Assignment! - empty assignment cannot be assigned to variable");
    }

    @Override
    public List<String> getVariables() {
        // create a list
        List<String> var = new ArrayList<String>();
        // add this var to list
        var.add(varChar);
        // return the list with the variable (this) inside (termination)
        return var;
    }

    @Override
    public String toString() {
        return varChar;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        // case this var is the var user want to assign
        if (varChar.equals(var)) {
            return expression.copy();
        } else {
            return this.copy(); // default
        }
    }

    @Override
    public Expression copy() {
        // copy itself
        return new Var(varChar);
    }

    @Override
    public Expression differentiate(String var) {
        // x' = 1
        if (varChar.equals(var)) {
            return new Num(1);
            // partial derive - treat vars like constants => y' => 0
        } else {
            return new Num(0);
        }
    }

    @Override
    public Expression simplify() {
        return this;
    }

    // -------------------------------- bonus ----------------------------------
    @Override
    public Expression toCanonical() {
        // the canonical form of var is 1 * ((var)^1)
        return new Mult(1, new Pow(this.varChar, 1));
    }

    @Override
    public Expression collectingLikeTerms() {
        // cant collect anything else
        return this;
    }

    @Override
    public Expression algebraMagic() {
        // also - termination condition
        return this;
    }

    @Override
    public String finalString() {
        // same as before
        return this.toString();
    }

    @Override
    public List<Expression> toLista() {
        // adding the variable to the list
        List<Expression> varLista = new ArrayList<Expression>();
        varLista.add(this);
        // send it back
        return varLista;
    }
}
