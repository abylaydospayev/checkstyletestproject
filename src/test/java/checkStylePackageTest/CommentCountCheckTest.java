/*
 * CommentCountCheckTest.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package checkStylePackageTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommentCountCheckTest {

    // Helper to read private field commentCount via reflection
    private int getCommentCount(CommentCountCheck check) throws Exception {
        Field f = CommentCountCheck.class.getDeclaredField("commentCount");
        f.setAccessible(true);
        return (int) f.get(check);
    }

    @Test
    void defaultAndAcceptableTokensAreComments() {
        CommentCountCheck check = new CommentCountCheck();

        int[] expected = {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN
        };

        assertArrayEquals(expected, check.getDefaultTokens());
        assertArrayEquals(expected, check.getAcceptableTokens());
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    void countsSingleAndBlockCommentsAndResetsAfterFinish() throws Exception {
        CommentCountCheck check = new CommentCountCheck();

        DetailAST c1 = mock(DetailAST.class);
        when(c1.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);

        DetailAST c2 = mock(DetailAST.class);
        when(c2.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);

        DetailAST block = mock(DetailAST.class);
        when(block.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);

        // 3 comments encountered
        check.visitToken(c1);
        check.visitToken(c2);
        check.visitToken(block);

        assertEquals(3, getCommentCount(check));

        // finishTree should reset the counter
        check.finishTree(null);              // 🔴 changed: pass null

        assertEquals(0, getCommentCount(check));
    }

    @Test
    void noCommentsLeavesCountZero() throws Exception {
        CommentCountCheck check = new CommentCountCheck();

        // Directly finish without seeing any comment tokens
        check.finishTree(null);              // 🔴 changed: pass null

        assertEquals(0, getCommentCount(check));
    }
}