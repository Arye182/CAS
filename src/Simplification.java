import java.util.List;

/**
 * The interface Simplification.
 * this is a very important interface. most of our advanced simplification
 * algorithm is based on the methods in this interface.
 * the methods will be implemented in all the classes that implemented
 * the Expression interface, as a matter of fact the interface Expression
 * is extending this one so we will have all the functionality under the same
 * roof.
 *
 * i will give below in the javadoc description all the important details and
 * responsibility of each method in this interface but in order to understand
 * the full idea and flow of the algorithm you must read the README file
 * attached in this assignment.
 */
public interface Simplification {

    /**
     * To Canonical method:
     * this is Phase 2 in the algorithm which i call "Mechanism"
     * this phase is maybe the most important one in the process.
     * in this phase the target is to bring the Expression to it's Canonical
     * form.
     * all the rules are described in the README file but the main idea is to
     * use the following math principles to achieve our goal:
     * 1. Commutativity.
     * 2. Distributivity.
     * 3. Associativity.
     * for example if we have the expression: ((x * 5) + x) * 2
     * we expect to get this result after the "toCanonical" phase:
     * 2*x^1 + 10*x^1.
     * of course we use any other math and algebra rules in order to simplify in
     * a more advanced way (powers, logs etc.).
     *
     * @return the expression after forcing all the list of rules on the
     * expression.
     */
    Expression toCanonical();

    /**
     * Collecting like terms method:
     * this method's purpose is to combine together expressions that can be
     * folded together. like 2x + 5x => 7x.
     * in order to achieve that i chose to shift the expression to another data
     * structure - a list of expressions. i find it more convenient to iterate
     * a list than the recursive tree.
     * the steps are:
     * 1. taking the expression and put it in a list
     * 2. iterate the list with loop inside a loop to compare every element with
     *    any other element of the expression.
     * 3. if a combine can be done it puts null instead of the two elements and
     *    create a new element in the list.
     * 4. kicking out all the nulls
     * 5. switch back to the expression data structure of a tree.
     * important REMARK: the assumption is that all the expressions are related
     * by addition as a result from the canonical process. in division the
     * assumption that we have multiplication.
     * in this version we dont support rational functions addition yet.
     *
     * @return the expression after combining like terms
     */
    Expression collectingLikeTerms();

    /**
     * Algebra magic method:
     * here we force the rules of math in a more advanced level - after doing
     * the basic simplification steps - its time to have some fun - you can
     * see what i chose to implement here at the README file and DEMO.
     * the sky is the limit of course.
     *
     * @return the expression after advanced simplification
     */
    Expression algebraMagic();

    /**
     * Final string method:
     * this is "the desert" and also the final Phase in the "Mechanism".
     * i chose to implement this kind of a method in order
     * to get rid of unwanted operators and parenthesis.
     * for example: (5 * x) ----> 5x
     *
     * @return the final string representation of the expression.
     */
    String finalString();

    /**
     * To lista method:
     * this method usage is to take the expression and put it inside a list.
     *
     * @return the list of elements from the expression.
     */
    List<Expression> toLista();
}
