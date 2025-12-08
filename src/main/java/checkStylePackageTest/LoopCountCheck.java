/*
 * LoopCountCheck.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */


package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * A custom Checkstyle Check that counts the total number of looping statements
 * in a Java file (for, while, and do-while loops).
 * <p>
 * This check implements logic to ensure that a single do-while construct is
 * counted exactly once, based on the LITERAL_DO token.
 * </p>
 */
public class LoopCountCheck extends AbstractCheck {

    /**
     * Internal counter to track the number of loops found in the current file.
     */
    private int loopCount = 0;

    /**
     * Specifies the token types this check is interested in.
     *
     * @return an array containing the token types for 'for', 'while', and 'do' loops.
     */
    @Override
    public int[] getDefaultTokens() {
        // We care about for, while, and do-while
        return new int[] {
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO
        };
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
     * Called by Checkstyle at the beginning of processing a new Abstract Syntax Tree (file).
     *
     * @param rootAST the root node of the AST.
     */
    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset the counter to 0 for each new file being analyzed
        loopCount = 0;
    }

    /**
     * Called each time one of the tokens defined in getDefaultTokens() is visited.
     * Implements logic to count loops and prevent double-counting of do-while structures.
     *
     * @param ast the token being visited.
     */
    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_FOR:
                // Count classic 'for' and enhanced 'for-each' loops
                loopCount++;
                break;

            case TokenTypes.LITERAL_WHILE:
                // If this WHILE is part of a DO-WHILE, we count it via LITERAL_DO instead.
                DetailAST parent = ast.getParent();
                if (parent == null || parent.getType() != TokenTypes.LITERAL_DO) {
                    // This is a plain 'while' loop
                    loopCount++;
                }
                break;

            case TokenTypes.LITERAL_DO:
                // Count each do-while loop exactly once here
                loopCount++;
                break;

            default:
                // No-op for tokens that match getDefaultTokens() but aren't explicitly handled (shouldn't happen)
        }
    }

    /**
     * Called after all tokens in the file have been visited.
     * Logs the final count of loops and resets the counter.
     *
     * @param rootAST the root of the Abstract Syntax Tree for the file.
     */
    @Override
    public void finishTree(DetailAST rootAST) {
        if (rootAST != null) {
            // Log the total count, typically at the first line of the file
            log(rootAST.getLineNo(), "Total number of loops: " + loopCount);
        }
        // Reset counter to ensure accurate counts for subsequent files in the same run
        loopCount = 0;
    }
}
