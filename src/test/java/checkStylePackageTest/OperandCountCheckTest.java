/*
 * OperandCountCheckTest.java
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

public class OperandCountCheckTest {

    // Helper to read private field via reflection
    private int getOperandCount(OperandCountCheck check) throws Exception {
        Field f = OperandCountCheck.class.getDeclaredField("operandCount");
        f.setAccessible(true);
        return (int) f.get(check);
    }

    @Test
    void testOperandCounting() throws Exception {
        OperandCountCheck check = new OperandCountCheck();
        // Context is NOT fully initialized here, so we test the logic, not the logging.
        check.beginTree(null);

        // Mock different types of operands
        DetailAST ident = mock(DetailAST.class);
        when(ident.getType()).thenReturn(TokenTypes.IDENT);

        DetailAST num = mock(DetailAST.class);
        when(num.getType()).thenReturn(TokenTypes.NUM_INT);

        DetailAST str = mock(DetailAST.class);
        when(str.getType()).thenReturn(TokenTypes.STRING_LITERAL);

        // Visit them
        check.visitToken(ident); // +1
        check.visitToken(num);   // +1
        check.visitToken(str);   // +1

        // Verify the internal count is 3
        assertEquals(3, getOperandCount(check));
    }

    @Test
    void testEmpty() throws Exception {
        OperandCountCheck check = new OperandCountCheck();
        check.beginTree(null);
        assertEquals(0, getOperandCount(check));
    }
}