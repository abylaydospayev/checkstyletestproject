/*
 * CommentCountCheck.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommentCountCheck extends AbstractCheck {

    /**
     * Internal counters.
     */
    private int commentCount = 0;
    private int commentLineCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN};
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
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Increment total number of comments
        commentCount++;

        // Calculate number of lines
        if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            // A single line comment is always 1 line
            commentLineCount++;
        } else if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            // Calculate lines for block comment: (EndLine - StartLine) + 1
            int startLine = ast.getLineNo();
            
            // Checkstyle AST: The last child of BLOCK_COMMENT_BEGIN is usually BLOCK_COMMENT_END
            DetailAST endNode = ast.getLastChild();
            
            if (endNode != null) {
                int endLine = endNode.getLineNo();
                commentLineCount += (endLine - startLine + 1);
            } else {
                // Fallback if end node isn't found (rare), count as at least 1
                commentLineCount++;
            }
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        if (rootAST != null) {
            log(rootAST.getLineNo(), "Total number of comments: " + commentCount);
            log(rootAST.getLineNo(), "Total number of lines of comments: " + commentLineCount);
        }
        // Reset counters
        commentCount = 0;
        commentLineCount = 0;
    }
}