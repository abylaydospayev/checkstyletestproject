package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LoopCountCheck extends AbstractCheck {

    private int loopCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO
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
        // Reset for each file
        loopCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_FOR:
                // Count classic for and enhanced for-each
                loopCount++;
                break;

            case TokenTypes.LITERAL_WHILE:
                // If this WHILE is part of a DO-WHILE, we count it via LITERAL_DO instead.
                DetailAST parent = ast.getParent();
                if (parent == null || parent.getType() != TokenTypes.LITERAL_DO) {
                    loopCount++;
                }
                break;

            case TokenTypes.LITERAL_DO:
                // Count each do-while loop exactly once here
                loopCount++;
                break;

            default:
                // no-op
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        if (rootAST != null) {
            log(rootAST.getLineNo(), "Total number of loops: " + loopCount);
        }
        loopCount = 0;
    }
}
