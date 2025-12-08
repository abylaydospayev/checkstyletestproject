/*
 * LoopCountCheckTest.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package checkStylePackageTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LoopCountCheckTest {

    // Helper to read private loopCount via reflection
    private int getLoopCount(LoopCountCheck check) throws Exception {
        Field f = LoopCountCheck.class.getDeclaredField("loopCount");
        f.setAccessible(true);
        return (int) f.get(check);
    }

    @Test
    void defaultAndAcceptableTokensAreLoops() {
        LoopCountCheck check = new LoopCountCheck();

        int[] expected = {
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO
        };

        assertArrayEquals(expected, check.getDefaultTokens());
        assertArrayEquals(expected, check.getAcceptableTokens());
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    void noLoopsKeepsCountZeroAndResetInFinishTree() throws Exception {
        LoopCountCheck check = new LoopCountCheck();

        check.beginTree(null);               // 🔴 null is fine here
        assertEquals(0, getLoopCount(check));

        check.finishTree(null);              // 🔴 changed
        assertEquals(0, getLoopCount(check));
    }

    @Test
    void countsForAndPlainWhile() throws Exception {
        LoopCountCheck check = new LoopCountCheck();

        DetailAST forAst = mock(DetailAST.class);
        when(forAst.getType()).thenReturn(TokenTypes.LITERAL_FOR);

        DetailAST whileAst = mock(DetailAST.class);
        when(whileAst.getType()).thenReturn(TokenTypes.LITERAL_WHILE);
        when(whileAst.getParent()).thenReturn(null); // plain while, not part of do-while

        check.beginTree(null);
        check.visitToken(forAst);
        check.visitToken(whileAst);

        // 2 loops counted before finishTree reset
        assertEquals(2, getLoopCount(check));

        check.finishTree(null);              // 🔴 changed
        assertEquals(0, getLoopCount(check));
    }

    @Test
    void countsDoWhileOnce() throws Exception {
        LoopCountCheck check = new LoopCountCheck();

        DetailAST doAst = mock(DetailAST.class);
        when(doAst.getType()).thenReturn(TokenTypes.LITERAL_DO);

        DetailAST whileAst = mock(DetailAST.class);
        when(whileAst.getType()).thenReturn(TokenTypes.LITERAL_WHILE);
        // while is child of DO ⇒ while branch should NOT increment
        when(whileAst.getParent()).thenReturn(doAst);

        check.beginTree(null);
        check.visitToken(doAst);     // +1
        check.visitToken(whileAst);  // +0 because part of do-while

        assertEquals(1, getLoopCount(check));

        check.finishTree(null);              // 🔴 changed
        assertEquals(0, getLoopCount(check));
    }

    @Test
    void ignoresNonLoopTokens() throws Exception {
        LoopCountCheck check = new LoopCountCheck();

        DetailAST ident = mock(DetailAST.class);
        when(ident.getType()).thenReturn(TokenTypes.IDENT); // default: no-op branch

        check.beginTree(null);
        check.visitToken(ident);

        assertEquals(0, getLoopCount(check));

        check.finishTree(null);              // 🔴 changed
        assertEquals(0, getLoopCount(check));
    }
}
