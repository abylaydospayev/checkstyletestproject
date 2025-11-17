package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommentCountCheck extends AbstractCheck {
    private int commentCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN};
    }

    @Override
    public int[] getAcceptableTokens() { return getDefaultTokens(); }

    @Override
    public int[] getRequiredTokens() { return new int[0]; }

    @Override
    public void visitToken(DetailAST ast) {
        commentCount++;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        if (rootAST != null) {
            log(rootAST.getLineNo(), "Total number of comments: " + commentCount);
        }
        commentCount = 0;
    }
}