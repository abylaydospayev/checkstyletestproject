package checkStylePackageTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;


class JunitHalsteadTest {

	/**
	 * Recursive helper method to manually traverse the AST, simulating the TreeWalker.
	 */
	public void helper(AbstractCheck b, DetailAST a) {
		while(a != null) {
			// Notify the check about the current token
			b.visitToken(a); 
			
			// Recurse into children
			helper(b,a.getFirstChild());
			
			// Move to the next sibling
			a = a.getNextSibling();
		}
	}

	@Test
	void testHalsteadMetrics() throws IOException, CheckstyleException {
		

		// NOTE: This path is relative to the project root.
		String filePath = "CheckStyleTests/src/MyTests/";
		File file = new File(filePath + "CS422Halstead.java");
		
		// Safety check: ensure the file exists at the expected location
		assertTrue(file.exists(), "Source file not found at " + file.getAbsolutePath() + 
				". Did you create the CheckStyleTests/src/MyTests folder?");
		
		FileText ft = new FileText(file,"UTF-8");
		FileContents fc = new FileContents(ft);
		
		// 2. Parse the FileContents into an Abstract Syntax Tree (AST)
		DetailAST root = JavaParser.parse(fc);
		
		// 3. Initialize Intended Check
		HalsteadCheck check = new HalsteadCheck(); 
		
		// 4. Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());
		
		// 5. Start the tree traversal and run the check logic
		check.beginTree(root);
		helper(check, root);
		check.finishTree(root); // Final calculations are performed here
		
		// 6. Verify Results
		

		final double EXPECTED_VOLUME = 394.20;
		final double ACTUAL_VOLUME = check.getHalsteadVolume();
		
		assertEquals(EXPECTED_VOLUME, ACTUAL_VOLUME, 0.001, 
		             "Halstead Volume mismatch. Check your HalsteadCheck implementation.");
		
		System.out.println("Halstead Check Blackbox Test Done! Volume: " + ACTUAL_VOLUME);
	}
}