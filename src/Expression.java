import java.util.List;
import java.util.Map;

/**
 * The interface Expression.
 * this is our main interface in this exercise.
 * the classes that will implement this interface are all the binary and unary
 * descendants and also Var and Num classes.
 * some of the methods here will be implemented not at the descendants but at
 * their parents - in order to use inheritance to share the code. so although
 * BinaryExpression, BaseExpression and UnaryExpression do not implement this
 * interface, we actually get them to implement some of this methods.
 * <p>
 * Bonus Remark:
 * this interface extends another interface - Simplification - that is something
 * from the bonus - i want that all the methods will be under the same roof so
 * i did that extending.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public interface Expression extends Simplification {
    /**
     * Evaluate double.
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result. If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     * Exceptions can be thrown also when an illegal math operation is done
     * like: dividing by zero, illegal base of log etc.
     *
     * @param assignment the assignment
     * @return the double (result)
     * @throws Exception the exception
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Evaluate double.
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * we will actually use an empty Map and call the Evaluate above when using
     * this method - also to save code.
     * the implementation of this method will be in the BaseExpression abstract
     * Class.
     *
     * @return the double
     * @throws Exception the exception
     */
    double evaluate() throws Exception;

    /**
     * Gets variables.
     * Returns a list of the variables in the expression.
     * works recursively.
     * the implementation of this method will be in the parents (Binary, Unary).
     * its pretty simple code so no need for concrete evaluation or something,
     * the termination condition will be in the Var Class.
     *
     * @return the variables list
     */
    List<String> getVariables();

    /**
     * Returns a nice string representation of the expression.
     * works recursively and implemented at the children - every class has its
     * own representation rules, so its obvious it should be implemented at the
     * bottom of the tree.
     *
     * @return the string of the expression.
     */
    String toString();

    /**
     * Assign expression.
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * if there is no such variables - it will return the same expression.
     * termination condition is Var Class of curse.
     *
     * @param var        the var to do the assign
     * @param expression the expression to put instead of the Var
     * @return the expression after assignment
     */
    Expression assign(String var, Expression expression);

    /**
     * Copy expression.
     * returns a pure new Copy of the current expression.
     * implemented at all children.
     *
     * @return the expression (new).
     */
    Expression copy();

    /**
     * Differentiate expression.
     * Returns the expression tree resulting from differentiating.
     * the current expression relative to variable `var`.
     * other vars treated as constants.
     *
     * @param var the var to derive according to.
     * @return the expression after deriving.
     */
    Expression differentiate(String var);

    /**
     * Simplify expression.
     * Returned a simplified version of the current expression.
     * implemented at the children - for the binary. the unary in the
     * UnaryExpression.
     *
     * @return the expression
     */
    Expression simplify();
}
