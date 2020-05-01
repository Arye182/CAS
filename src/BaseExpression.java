import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The abstract Class Base expression.
 * <p>
 * the highest class in the expression tree hierarchy.
 * here you can find all the most general methods. that will be passed by
 * inheritance to the children of this class. which are the UnaryExpression,
 * BinaryExpression and all their children which are more concrete. the more
 * down you go through the tree of hierarchy the more specific and concrete
 * the methods will be.
 *
 * @author Arye Amsalem
 * @version 1.00 17 April 2019
 */
public abstract class BaseExpression {
    /**
     * Try evaluate method.
     * this method is a general purpose method that serves you anytime you want
     * to take an evaluate action of an expression without using a map. i use
     * this method mainly when i want to fold constants. it is sort of a
     * shortcut to the try catch mechanism. if the Exception has been thrown in
     * this case it means that one of the elements in the expression was not a
     * number but a variable - therefore it returns NaN which i treat here as
     * the "Null" of the numbers.
     *
     * @param tryE the Expression to evaluate.
     * @return the result (number) or NaN if not a number.
     */
    protected double tryEvaluate(Expression tryE) {
        try {
            // ideal case when the fold process succeeded
            return tryE.evaluate();
        } catch (Exception e) {
            // if the exception was dividing by zero i want to print this error
            if (e.toString().equals("java.lang.Exception: Illegal Math Action - Divide by 0")) {
                System.out.println(e.toString());
            }
            // if the exception came from log - cannot calculate log on negative or zero
            if (e.toString().equals("java.lang.Exception: Illegal Math Action - "
                    + "Log cannot be calculated on negative numbers or zero")) {
                System.out.println(e.toString());
            }
            // log error - base is <=0 or not different from 1
            if (e.toString().equals("java.lang.Exception: Illegal Math Action"
                    + " - Log base has to be > 0 and different from 1")) {
                System.out.println(e.toString());
            }
            // in case there was no number in expression
            return Double.NaN;
        }
    }

    /**
     * Evaluate double.
     * here i wrote only the signature of this method so BaseExpression will
     * know this function. i did this thanks to the fact that i putted the
     * map-free evaluate method here - to share code and save duplicates.
     * i will explain at the next method how this works.
     *
     * @param assignment the assignment map
     * @return the double (result after assign)
     * @throws Exception the exception
     */
    public abstract double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Evaluate (No Map) - one of the methods in Expression interface.
     * i chose to implement it here so all the children will have this.
     * the idea is simple. create an empty map and use the second evaluate
     * method to do the calculate. Num will return itself - easy. and Var will
     * throw an exception that the map is empty.
     *
     * @return the double (result)
     * @throws Exception the exception (if exist)
     */
    public double evaluate() throws Exception {
        // creating an empty map so we can actually call the second evaluate
        Map<String, Double> assignment = new TreeMap<String, Double>();
        // use the second evaluate to do regular evaluate with empty map
        return this.evaluate(assignment);
    }

    /**
     * List adder method.
     * this method is also general use only - it gives you the ability to add a
     * nun - null list to already existing list. that's all.
     *
     * @param listToAdd the list - to add - to the variables list
     * @param variables the variables list - the list that we add things to her
     * @return the list after updating (if there was)
     */
    protected List<String> listAdder(List<String> listToAdd, List<String> variables) {
        // check for nullity
        if (listToAdd != null) {
            variables.addAll(listToAdd); // adding...
        }
        // return updated list
        return variables;
    }
}
