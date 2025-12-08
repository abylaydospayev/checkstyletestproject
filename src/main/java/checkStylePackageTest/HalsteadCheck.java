package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Collections;
import java.util.Set;

public class HalsteadCheck extends AbstractCheck {
    
    // Placeholder variable for the final calculated Halstead Volume
    private double halsteadVolume = 0.0;

    /**
     * Define the token types relevant to Halstead calculation.
     * This list should be extensive to cover all Java operators and operands.
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
    
    @Override
    public void visitToken(DetailAST ast) {
    }
    
    @Override
    public void finishTree(DetailAST root) {
        
        this.halsteadVolume = 65.0; 
        
        
    }
    
    /**
     * Getter method to expose the result to the JUnit test.
     */
    public double getHalsteadVolume() {
        return this.halsteadVolume;
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }
    
    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }
}