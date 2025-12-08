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


public class CommentCountCheck extends AbstractCheck {

    /**
     * Internal counter to track the number of comments found in the current file.
     */
    private int commentCount = 0;

    /**
     * Specify the tokens this check is interested in.
     * We register for single-line comments and the start of block comments.
     *
     * @return int array of token types to visit.
     */
    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN};
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
     * @return int array of required token types (empty in this case).
     */
    @Override
    public int[] getRequiredTokens() { 
        return new int[0]; 
    }

    /**
     * Called by Checkstyle infrastructure to determine if comment nodes
     * should be included in the AST. 
     * <p>
     * <b>Crucial:</b> By default, Checkstyle ignores comments for efficiency. 
     * We must return true here to receive comment tokens in visitToken().
     * </p>
     *
     * @return true, ensuring comment nodes are processed.
     */
    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    /**
     * Called each time one of the tokens defined in getDefaultTokens() is visited.
     * Increments the comment counter.
     *
     * @param ast the token being visited (either a single-line or block comment start).
     */
    @Override
    public void visitToken(DetailAST ast) {
        commentCount++;
    }

    /**
     * Called after all tokens in the file have been visited.
     * Logs the final count of comments and resets the counter for the next file.
     *
     * @param rootAST the root of the Abstract Syntax Tree for the file.
     */
    @Override
    public void finishTree(DetailAST rootAST) {
        if (rootAST != null) {
            // Log the total count at the first line of the file
            log(rootAST.getLineNo(), "Total number of comments: " + commentCount);
        }
        // Reset counter to ensure accurate counts for subsequent files in the same run
        commentCount = 0;
    }
}