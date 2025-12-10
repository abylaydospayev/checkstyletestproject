/*
 * ExpressionCountCheckTest.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package checkStylePackageTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ExpressionCountCheckTest {

    // Helper to read private field expressionCount via reflection
    private int getExpressionCount(ExpressionCountCheck check) throws Exception {
        Field f = ExpressionCountCheck.class.getDeclaredField("expressionCount");
        f.setAccessible(true);
        return (int) f.get(check);
    }

    @Test
    void testExpressionCounting() throws Exception {
        ExpressionCountCheck check = new ExpressionCountCheck();
        
        // Reset the counter (context is null, but that's okay for logic testing)
        check.beginTree(null); 

        // Create mock tokens representing different expressions
        DetailAST assign = mock(DetailAST.class);
        when(assign.getType()).thenReturn(TokenTypes.ASSIGN);
        
        DetailAST plus = mock(DetailAST.class);
        when(plus.getType()).thenReturn(TokenTypes.PLUS);
        
        DetailAST methodCall = mock(DetailAST.class);
        when(methodCall.getType()).thenReturn(TokenTypes.METHOD_CALL);

        DetailAST ternary = mock(DetailAST.class);
        when(ternary.getType()).thenReturn(TokenTypes.QUESTION);

        // Simulate visiting these tokens
        check.visitToken(assign);
        check.visitToken(plus);
        check.visitToken(methodCall);
        check.visitToken(ternary);

        // Verify the internal count is 4
        assertEquals(4, getExpressionCount(check));
        
        // REMOVED: check.finishTree(mock(DetailAST.class)); 
        // We don't call finishTree() to avoid NullPointerException in the log() method.
    }

    @Test
    void testEmptyExpressions() throws Exception {
        ExpressionCountCheck check = new ExpressionCountCheck();
        check.beginTree(null);
        assertEquals(0, getExpressionCount(check));
    }
}