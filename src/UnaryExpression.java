import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * The abstract class UnaryExpression.
 * this is the abstract parent of all the unary operators:
 * Cos, Sin, Neg.
 * methods implemented from Expression interface:
 * getVariables, assign, simplify.
 * methods implemented from Simplification interface:
 * toLista.
 * it also has getter and setter since the field exp is private.
 * the constructors are protected because it is an abstract class so you cannot
 * instantiate an object from this class. only the children can do by super
 * usage.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public abstract class UnaryExpression extends BaseExpression {

    // fields
    private Expression exp;

    /**
     * Instantiates a new Unary expression such that field is an Expression.
     *
     * @param e the expression
     */
    protected UnaryExpression(Expression e) {
        this.exp = e;
    }

    /**
     * Instantiates a new Unary expression such that the field is a Var.
     *
     * @param var the Var.
     */
    protected UnaryExpression(String var) {
        this.exp = new Var(var);
    }

    /**
     * Instantiates a new Unary expression such that the field is a Num.
     *
     * @param num the Num.
     */
    protected UnaryExpression(double num) {
        this.exp = new Num(num);
    }

    /**
     * Gets variables.
     * this method is here due the fact it is mutual code for all unary
     * descendants. the method works recursively thanks the inheritance
     * tree and finally brings up back the list with the variables.
     * in order to avoid duplicated variables we use a nice trick here:
     * the linked hash set which take care of our duplicated variables.
     *
     * @return the variables
     */
    public List<String> getVariables() {
        // recursive call
        List<String> thisVars = exp.getVariables();
        // new list
        List<String> unaryVariables = new ArrayList<String>();
        // add the vars to the list
        unaryVariables = listAdder(thisVars, unaryVariables);
        // clean duplicates
        return new ArrayList<String>(new LinkedHashSet<String>(unaryVariables));
    }

    /**
     * Assign expression method.
     * this method is from the interface Expression - this class implements it
     * through its children - with no implementing the interface.
     * the idea is to save code and put shared code in this upper level classes.
     * for further info about this method u can read in the interface
     * Expression JavaDocs.
     *
     * @param var the variable to assign to.
     * @param expression the expression to be assigned.
     * @return the expression (new one) after assignment.
     */
    public Expression assign(String var, Expression expression) {
        // new expression
        UnaryExpression assign = (UnaryExpression) this.copy();
        // recursive call
        assign.exp = assign.exp.assign(var, expression);
        // return
        return (Expression) assign;
    }

    /**
     * Copy expression.
     * this is only the signature of this method so i can use it in the method
     * assign above.
     *
     * @return the expression
     */
    public abstract Expression copy();

    /**
     * Simplify expression.
     * i used the string thing here for Neg just to save implementing it in Sin,
     * Cos and Neg. I did not want to implement here for Sin and Cos and Neg as
     * Override i was afraid of decrease in points.
     * so - this is a "nice" way to verify if we are handling a Neg Expression.
     * i did not use any instanceof or reflexion things.
     *
     * this implementation here for simplify covers all the cases of the children.
     *
     * @return the expression after simplification.
     */
    public Expression simplify() {
        // new expression
        UnaryExpression unarySimplified = (UnaryExpression) this.copy();
        // recursive call
        unarySimplified.exp = unarySimplified.exp.simplify();
        // case its a Neg
        String[] unaryArr = this.toString().split("");
        if (unaryArr[1].equals("-")) {
            return (Expression) unarySimplified;
        }
        // case the expression in sin / cos is not a number
        if (Double.isNaN(tryEvaluate(unarySimplified.exp))) {
            return (Expression) unarySimplified;
        }
        // case its a number inside - we can evaluate the result but not for Neg!
        return new Num(tryEvaluate((Expression) unarySimplified));
    }

    /**
     * Gets inner Expression of this Expression.
     *
     * @return the inner Expression.
     */
    protected Expression getExp() {
        return exp;
    }

    /**
     * Sets inner exp.
     *
     * @param newExp the new Expression to set on the existing.
     */
    protected void setExp(Expression newExp) {
        this.exp = newExp;
    }

    // -------------------------------- bonus ----------------------------------
    /**
     * To lista method.
     * this method is used to retrieve a list of expressions from a given unary
     * expression. we use it mainly at the collecting like terms phase during
     * our advanced simplification algorithm.
     *
     * @return the list with expressions
     */
    public List<Expression> toLista() {
        // actually the unary expression is already an element so it returns itself!
        List<Expression> lista = new ArrayList<Expression>();
        lista.add((Expression) this);
        return lista;
    }
}
