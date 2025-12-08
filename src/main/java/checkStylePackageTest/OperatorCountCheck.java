/*
 * OperatorCountCheck.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package checkStylePackageTest;


import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * A custom Checkstyle Check that counts the total number of operators in a file.
 * <p>
 * The set of counted tokens is defined by {@code OPERATOR_TOKENS} and is often
 * used for calculating code metrics like Halstead Complexity.
 * </p>
 */
public class OperatorCountCheck extends AbstractCheck {

    /**
     * Internal counter to track the number of operators found in the current file.
     */
    private int operatorCount;

    /**
     * Defines the array of token types that are treated as operators for this check.
     */
    private static final int[] OPERATOR_TOKENS = {
        TokenTypes.ASSIGN,       // =
        TokenTypes.PLUS,         // +
        TokenTypes.MINUS,        // -
        TokenTypes.STAR,         // *
        TokenTypes.DIV,          // /
        TokenTypes.MOD,          // %
        TokenTypes.LAND,         // && (Logical AND)
        TokenTypes.LOR,          // || (Logical OR)
        TokenTypes.BAND,         // & (Bitwise AND)
        TokenTypes.BOR,          // | (Bitwise OR)
        TokenTypes.BXOR,         // ^ (Bitwise XOR)
        TokenTypes.LT,           // < (Less Than)
        TokenTypes.GT,           // > (Greater Than)
        TokenTypes.LE,           // <= (Less Than or Equal)
        TokenTypes.GE,           // >= (Greater Than or Equal)
        TokenTypes.EQUAL,        // == (Equality)
        TokenTypes.NOT_EQUAL,    // != (Inequality)
        TokenTypes.PLUS_ASSIGN,  // +=
        TokenTypes.MINUS_ASSIGN, // -=
        TokenTypes.STAR_ASSIGN,  // *=
        TokenTypes.DIV_ASSIGN,   // /=
        TokenTypes.MOD_ASSIGN,   // %=
        TokenTypes.INC,          // ++ (Prefix or Postfix Increment)
        TokenTypes.DEC,          // -- (Prefix or Postfix Decrement)
        TokenTypes.QUESTION,     // ? (Ternary Operator)
        TokenTypes.COLON         // : (Ternary Operator)
    };

    /**
     * A set optimized for quick lookup of valid operator token types.
     */
    private static final Set<Integer> OPERATOR_SET = new HashSet<>();

    /**
     * Static block to populate the OPERATOR_SET upon class loading.
     */
    static {
        for (int t : OPERATOR_TOKENS) {
            OPERATOR_SET.add(t);
        }
    }

    /**
     * Specifies the token types this check is interested in visiting.
     *
     * @return a clone of the array containing all defined operator tokens.
     */
    @Override
    public int[] getDefaultTokens() {
        return OPERATOR_TOKENS.clone();
    }

    /**
     * Defines the acceptable tokens for configuration.
     *
     * @return the same array returned by getDefaultTokens().
     */
    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    /**
     * Defines the required tokens.
     *
     * @return an empty array, as no tokens are strictly required.
     */
    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    /**
     * Called at the beginning of processing a new Abstract Syntax Tree (file).
     *
     * @param rootAST the root node of the AST.
     */
    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset the counter to 0 for each new file being analyzed
        operatorCount = 0;
    }

    /**
     * Called each time one of the tokens defined in getDefaultTokens() is visited.
     *
     * @param ast the token being visited.
     */
    @Override
    public void visitToken(DetailAST ast) {
        // Check if the current token type is defined as an operator
        if (OPERATOR_SET.contains(ast.getType())) {
            operatorCount++;
        }
    }

    /**
     * Called after all tokens in the file have been visited.
     * Logs the final count of operators and resets the counter.
     *
     * @param rootAST the root of the Abstract Syntax Tree for the file.
     */
    @Override
    public void finishTree(DetailAST rootAST) {
        if (rootAST != null) {
            // Log the total count, typically at the first line of the file
            log(rootAST.getLineNo(),
                "Total number of operators: " + operatorCount);
        }
        // Reset counter to ensure accurate counts for subsequent files in the same run
        operatorCount = 0;
    }

    /**
     * Package-visible getter used by unit tests to read the internal counter.
     *
     * @return the current count of operators.
     */
    int getOperatorCount() {
        return operatorCount;
    }
}
