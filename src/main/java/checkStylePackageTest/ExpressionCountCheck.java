package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Check that counts the number of expressions in a Java source file.
 */
public class ExpressionCountCheck extends AbstractCheck {

    private int expressionCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{
            TokenTypes.ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.LNOT,
            TokenTypes.BNOT,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.METHOD_CALL,
            TokenTypes.LITERAL_NEW,
            // FIXED: Use QUESTION for the ternary operator (?)
            TokenTypes.QUESTION,
            TokenTypes.PLUS,
            TokenTypes.MINUS,
            TokenTypes.STAR,
            TokenTypes.DIV,
            TokenTypes.MOD
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public void visitToken(DetailAST ast) {
        expressionCount++;
    }

    @Override
    public void beginTree(DetailAST root) {
        expressionCount = 0;
    }

    @Override
    public void finishTree(DetailAST ast) {
        log(ast.getLineNo(), "Number of expressions: " + expressionCount);
        expressionCount = 0;
    }
}