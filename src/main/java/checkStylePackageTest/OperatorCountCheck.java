package checkStylePackageTest;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Counts the total number of operators in a file.
 */
public class OperatorCountCheck extends AbstractCheck {

    private int operatorCount;

    // Define which token types we treat as operators
    private static final int[] OPERATOR_TOKENS = {
        TokenTypes.ASSIGN,
        TokenTypes.PLUS,
        TokenTypes.MINUS,
        TokenTypes.STAR,
        TokenTypes.DIV,
        TokenTypes.MOD,
        TokenTypes.LAND,
        TokenTypes.LOR,
        TokenTypes.BAND,
        TokenTypes.BOR,
        TokenTypes.BXOR,
        TokenTypes.LT,
        TokenTypes.GT,
        TokenTypes.LE,
        TokenTypes.GE,
        TokenTypes.EQUAL,
        TokenTypes.NOT_EQUAL,
        TokenTypes.PLUS_ASSIGN,
        TokenTypes.MINUS_ASSIGN,
        TokenTypes.STAR_ASSIGN,
        TokenTypes.DIV_ASSIGN,
        TokenTypes.MOD_ASSIGN,
        TokenTypes.INC,
        TokenTypes.DEC,
        TokenTypes.QUESTION,
        TokenTypes.COLON
        // add more if your README / spec defines more operators
    };

    private static final Set<Integer> OPERATOR_SET = new HashSet<>();

    static {
        for (int t : OPERATOR_TOKENS) {
            OPERATOR_SET.add(t);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return OPERATOR_TOKENS.clone();
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
        operatorCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (OPERATOR_SET.contains(ast.getType())) {
            operatorCount++;
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        if (rootAST != null) {
            log(rootAST.getLineNo(),
                "Total number of operators: " + operatorCount);
        }
        operatorCount = 0;
    }

    // package-visible getter for unit tests
    int getOperatorCount() {
        return operatorCount;
    }
}
