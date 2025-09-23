package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LoopCountCheck extends AbstractCheck {
    private int loopCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO};
    }

    @Override
    public int[] getAcceptableTokens() { return getDefaultTokens(); }

    @Override
    public int[] getRequiredTokens() { return new int[0]; }

    @Override
    public void visitToken(DetailAST ast) {
        loopCount++;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        log(rootAST.getLineNo(), "Total number of loops: " + loopCount);
        loopCount = 0;
    }
}