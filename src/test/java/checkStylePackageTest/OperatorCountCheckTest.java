/*
 * OperatorCountCheckTest.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package checkStylePackageTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OperatorCountCheckTest {

    @Test
    void defaultAndAcceptableTokensMatch() {
        OperatorCountCheck check = new OperatorCountCheck();

        int[] defaults = check.getDefaultTokens();
        int[] acceptable = check.getAcceptableTokens();

        // Should be identical arrays
        assertArrayEquals(defaults, acceptable);

        // Required tokens should be empty
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    void countsMultipleOperatorsAndResets() {
        OperatorCountCheck check = new OperatorCountCheck();

        DetailAST plus = mock(DetailAST.class);
        when(plus.getType()).thenReturn(TokenTypes.PLUS);

        DetailAST minus = mock(DetailAST.class);
        when(minus.getType()).thenReturn(TokenTypes.MINUS);

        DetailAST assign = mock(DetailAST.class);
        when(assign.getType()).thenReturn(TokenTypes.ASSIGN);

        check.beginTree(null);
        check.visitToken(plus);
        check.visitToken(minus);
        check.visitToken(assign);

        // 3 operators before finishTree reset
        assertEquals(3, check.getOperatorCount());

        check.finishTree(null);              // 🔴 changed

        // after finishTree, counter reset to 0
        assertEquals(0, check.getOperatorCount());
    }

    @Test
    void ignoresNonOperatorTokens() {
        OperatorCountCheck check = new OperatorCountCheck();

        DetailAST ident = mock(DetailAST.class);
        when(ident.getType()).thenReturn(TokenTypes.IDENT); // not in OPERATOR_SET

        check.beginTree(null);
        check.visitToken(ident);
        check.finishTree(null);              // 🔴 changed

        assertEquals(0, check.getOperatorCount());
    }
}



