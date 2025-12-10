/*
 * JunitHalsteadTest.java
 * 
 */
package checkStylePackageTest;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

class JunitHalsteadTest {

    public void helper(AbstractCheck check, DetailAST ast) {
        while (ast != null) {
            check.visitToken(ast);
            helper(check, ast.getFirstChild());
            ast = ast.getNextSibling();
        }
    }

    @Test
    void testHalsteadMetrics() throws Exception {
        // Point to your valid input file
        String filePath = "src/test/resources/inputs/HalsteadInput.java";
        File file = new File(filePath);
        assertTrue(file.exists(), "Input file not found at " + file.getAbsolutePath());

        FileText ft = new FileText(file, "UTF-8");
        FileContents fc = new FileContents(ft);
        DetailAST root = JavaParser.parse(fc);

        HalsteadCheck check = new HalsteadCheck();
        check.configure(new DefaultConfiguration("Local"));
        check.contextualize(new DefaultContext());

        check.beginTree(root);
        helper(check, root);
        check.finishTree(root);

        // Retrieve all 5 metrics
        double length = check.getHalsteadLength();
        double vocab = check.getHalsteadVocabulary();
        double volume = check.getHalsteadVolume();
        double difficulty = check.getHalsteadDifficulty();
        double effort = check.getHalsteadEffort();

        System.out.println("----- Halstead Results -----");
        System.out.println("Length:     " + length);
        System.out.println("Vocabulary: " + vocab);
        System.out.println("Volume:     " + volume);
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Effort:     " + effort);

        // Assertions (Value > 0 proves the logic ran)
        assertTrue(length > 0, "Length > 0");
        assertTrue(vocab > 0, "Vocabulary > 0");
        assertTrue(volume > 0, "Volume > 0");
        assertTrue(difficulty > 0, "Difficulty > 0");
        assertTrue(effort > 0, "Effort > 0");
    }
}