import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * The abstract class Binary expression.
 * this is the abstract parent of all the binary operators:
 * Minus, Plus, Log, Pow, Mult, Div.
 * methods implemented from Expression interface:
 * getVariables, assign.
 * methods implemented from Simplification interface:
 * toLista.
 * it also has getters and setters since the fields left and right are private.
 * the constructors are protected because it is an abstract class so you cannot
 * instantiate an object from this class. only the children can do by super
 * usage.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public abstract class BinaryExpression extends BaseExpression {

    // fields are private - to keep privacy of data
    private Expression left; // the left part can be any expression
    private Expression right; // also the right

    /*
     * 9 constructors - for all possible combinations
     * REMARK: when i am writing instantiates a new binary expression the
     * meaning of course is that it is not really a BinaryExpression but one
     * of the children types (cannot instantiate abstract class).
     */

    /**
     * Instantiates a new "Binary expression" such that:
     * both left and right can be any expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     */
    protected BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Instantiates a new "Binary expression" such that:
     * left is an expression.
     * right is a double (Num).
     *
     * @param left  the Expression
     * @param right the Num
     */
    protected BinaryExpression(Expression left, double right) {
        this.left = left;
        this.right = new Num(right);
    }

    /**
     * Instantiates a new "Binary expression" such that:
     * left is an expression.
     * right is a string (Var).
     *
     * @param left  the Expression
     * @param right the Var
     */
    protected BinaryExpression(Expression left, String right) {
        this.left = left;
        this.right = new Var(right);
    }

    /**
     * Instantiates a new "Binary expression" such that:
     * left is a double (Num).
     * right is an expression.
     *
     * @param left  the Num
     * @param right the Expression
     */
    protected BinaryExpression(double left, Expression right) {
        this.left = new Num(left);
        this.right = right;
    }

    /**
     * Instantiates a new "Binary expression" such that:
     * left is a double (Num).
     * right is a double (Num).
     *
     * @param left  the Num
     * @param right the Num
     */
    protected BinaryExpression(double left, double right) {
        this.left = new Num(left);
        this.right = new Num(right);
    }

    /**
     * Instantiates a new "Binary expression" such that:
     * left is a double (Num).
     * right is a string (Var).
     *
     * @param left  the Num
     * @param right the Var
     */
    protected BinaryExpression(double left, String right) {
        this.left = new Num(left);
        this.right = new Var(right);
    }

    /**
     * Instantiates a new "Binary expression" such that:
     * left is a string (Var).
     * right is an expression.
     *
     * @param left  the Var
     * @param right the Expression
     */
    protected BinaryExpression(String left, Expression right) {
        this.left = new Var(left);
        this.right = right;
    }

    /**
     * Instantiates a new "Binary expression" such that:
     * left is a string (Var).
     * right is a double (Num).
     *
     * @param left  the Var
     * @param right the Num
     */
    protected BinaryExpression(String left, double right) {
        this.left = new Var(left);
        this.right = new Num(right);
    }

    /**
     * Instantiates a new "Binary expression" such that:
     * left is a string (Var).
     * right is a string (Var).
     *
     * @param left  the Var
     * @param right the Var
     */
    protected BinaryExpression(String left, String right) {
        this.left = new Var(left);
        this.right = new Var(right);
    }

    /**
     * Gets variables.
     * this method is here due the fact it is mutual code for all binary
     * descendants. the method works recursively thanks the inheritance
     * tree and finally brings up back the list with the variables.
     * in order to avoid duplicated variables we use a nice trick here:
     * the linked hash set which take care of our duplicated variables.
     *
     * @return the variables
     */
    public List<String> getVariables() {
        // recursively calls
        List<String> leftList = left.getVariables();
        List<String> rightList = right.getVariables();
        // creating new list of variables
        List<String> variables = new ArrayList<String>();
        // add the list the results from left and right
        variables = listAdder(leftList, variables);
        variables = listAdder(rightList, variables);
        // get rid of duplicates and return the list
        return new ArrayList<String>(new LinkedHashSet<String>(variables));
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
        // creating new expression (copy)
        BinaryExpression assign = (BinaryExpression) this.copy();
        // recursive calls
        assign.left = assign.left.assign(var, expression);
        assign.right = assign.right.assign(var, expression);
        // return the new expression after assignment
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
     * Gets left.
     *
     * @return the left expression
     */
    protected Expression getLeft() {
        return left;
    }

    /**
     * Gets right.
     *
     * @return the right expression
     */
    protected Expression getRight() {
        return right;
    }

    /**
     * Sets right expression.
     *
     * @param newRight the new right expression
     */
    protected void setRight(Expression newRight) {
        this.right = newRight;
    }

    /**
     * Sets left expression.
     *
     * @param newLeft the new left expression.
     */
    protected void setLeft(Expression newLeft) {
        this.left = newLeft;
    }

    // -------------------------------- bonus ----------------------------------
    /**
     * To lista method.
     * this method is used to retrieve a list of expressions from a given binary
     * expression. we use it mainly at the collecting like terms phase during
     * our advanced simplification algorithm.
     *
     * @return the list with expressions
     */
    public List<Expression> toLista() {
        // special termination conditions for entering this list
        // returns things like 5 * x
        if ((this instanceof Mult)) {
            List<Expression> varLista = new ArrayList<Expression>();
            varLista.add((Mult) this);
            return varLista;
        }
        // returns things like x^2
        if (this instanceof Pow) {
            List<Expression> varLista = new ArrayList<Expression>();
            varLista.add((Pow) this);
            return varLista;
        }
        // returns things like 1/x
        if (this instanceof Div) {
            List<Expression> varLista = new ArrayList<Expression>();
            varLista.add((Div) this);
            return varLista;
        }
        // recursive call for left and right
        List<Expression> leftListE = left.toLista();
        List<Expression> rightListE = right.toLista();
        List<Expression> lista = new ArrayList<Expression>();
        // check fo nullity
        if (leftListE != null) {
            lista.addAll(leftListE);
        }
        if (rightListE != null) {
            lista.addAll(rightListE);
        }
        // return the final list
        return lista;
    }
}
