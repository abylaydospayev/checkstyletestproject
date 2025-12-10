/*
 * OperandCountCheck.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * A custom Checkstyle Check that counts the total number of operands in a file.
 * Operands include identifiers, literals (int, long, float, double, string, char), etc.
 */
public class OperandCountCheck extends AbstractCheck {

    /**
     * Internal counter to track the number of operands found in the current file.
     */
    private int operandCount = 0;

    /**
     * Specify the tokens this check is interested in.
     * These align with the definition of "operands" used in Halstead metrics.
     *
     * @return int array of token types to visit.
     */
    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.IDENT,           // Variable names, method names, class names
            TokenTypes.NUM_INT,         // Integer literals (e.g., 123)
            TokenTypes.NUM_LONG,        // Long literals (e.g., 123L)
            TokenTypes.NUM_FLOAT,       // Float literals (e.g., 12.3f)
            TokenTypes.NUM_DOUBLE,      // Double literals (e.g., 12.3d)
            TokenTypes.STRING_LITERAL,  // String literals (e.g., "hello")
            TokenTypes.CHAR_LITERAL     // Char literals (e.g., 'a')
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset counter for new file
        operandCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        operandCount++;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        if (rootAST != null) {
            log(rootAST.getLineNo(), "Total number of operands: " + operandCount);
        }
        operandCount = 0;
    }
    
    // Package-private getter for testing if needed
    int getOperandCount() {
        return operandCount;
    }
}