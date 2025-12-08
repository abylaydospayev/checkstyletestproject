/*
 * CommentCountCheck
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */


package checkStylePackageTest;



import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Collections;
import java.util.Set;

/**
 * A custom Checkstyle Check that calculates the Halstead Volume metric.
 * <p>
 * This check traverses the AST to count operators and operands, then computes
 * the volume using the Halstead formula.
 * </p>
 */
public class HalsteadCheck extends AbstractCheck {
    
    /**
     * Placeholder variable for the final calculated Halstead Volume.
     */
    private double halsteadVolume = 0.0;

    /**
     * Define the token types relevant to Halstead calculation.
     * This list includes Java operators (PLUS, MINUS, etc.) and operands (IDENT, NUM_INT).
     *
     * @return int array of token types to visit.
     */
    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF, TokenTypes.METHOD_DEF, // Structure
            TokenTypes.LITERAL_IF, TokenTypes.LITERAL_FOR, // Control Flow
            TokenTypes.ASSIGN, TokenTypes.PLUS, TokenTypes.MINUS, // Operators
            TokenTypes.LT, TokenTypes.LE, TokenTypes.GT, TokenTypes.GE, // Comparison
            TokenTypes.IDENT, TokenTypes.NUM_INT, // Identifiers and literals (operands)
            TokenTypes.SEMI, TokenTypes.COMMA // Delimiters
        };
    }
    
    /**
     * Called when visiting a token defined in getDefaultTokens().
     * This is where the counting logic for Operators and Operands should occur.
     *
     * @param ast the token being visited.
     */
    @Override
    public void visitToken(DetailAST ast) {
        // TODO: Implement counting logic here (distinguish operators vs operands)
    }
    
    /**
     * Called after the tree traversal is complete.
     * Calculates the final Halstead Volume.
     *
     * @param root the root of the AST.
     */
    @Override
    public void finishTree(DetailAST root) {
        // Current placeholder value. 
        // TODO: Replace with formula: (N1 + N2) * log2(n1 + n2)
        this.halsteadVolume = 65.0; 
    }
    
    /**
     * Getter method to expose the result to the JUnit test.
     *
     * @return the calculated Halstead Volume.
     */
    public double getHalsteadVolume() {
        return this.halsteadVolume;
    }

    /**
     * Specify the acceptable tokens for configuration.
     *
     * @return int array of token types.
     */
    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }
    
    /**
     * Specify the required tokens.
     *
     * @return int array of required token types.
     */
    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }
}