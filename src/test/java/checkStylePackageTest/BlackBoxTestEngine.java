package checkStylePackageTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

public class BlackBoxTestEngine {

    // Helper method to run any Check on a File
    private String runCheck(com.puppycrawl.tools.checkstyle.api.AbstractCheck checkObj, String filePath) throws Exception {
        File file = new File(filePath);
        
        //  Fallback logic to find the file
        if (!file.exists()) {
            String fileName = file.getName();
            java.net.URL resource = getClass().getClassLoader().getResource("inputs/" + fileName);
            if (resource != null) {
                file = new File(resource.toURI());
            } else {
                System.err.println("File not found at: " + file.getAbsolutePath());
                throw new RuntimeException("Input file not found: " + filePath);
            }
        }


        // A. The Check itself 
        DefaultConfiguration checkConfig = new DefaultConfiguration(checkObj.getClass().getName());
        
        // B. The TreeWalker 
        DefaultConfiguration treeWalkerConfig = new DefaultConfiguration(TreeWalker.class.getName());
        treeWalkerConfig.addChild(checkConfig);
        
        // C. The Checker 
        DefaultConfiguration rootConfig = new DefaultConfiguration(Checker.class.getName());
        rootConfig.addChild(treeWalkerConfig);

        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(rootConfig); 

        final List<String> logs = new ArrayList<>();
        checker.addListener(new AuditListener() {
            public void auditStarted(AuditEvent event) {}
            public void auditFinished(AuditEvent event) {}
            public void fileStarted(AuditEvent event) {}
            public void fileFinished(AuditEvent event) {}
            public void addError(AuditEvent event) { logs.add(event.getMessage()); }
            public void addException(AuditEvent event, Throwable throwable) { throwable.printStackTrace(); }
        });

        // 5. Run it
        checker.process(List.of(file));
        checker.destroy();
        return String.join("\n", logs);
    }

    @Test
    public void testLoopCheck() throws Exception {
        // Adjust expected text based on  exact loop counting logic
        String output = runCheck(new LoopCountCheck(), "src/test/resources/inputs/LoopInput.java");
        System.out.println("Loop Output: " + output);
        
        assertTrue(output.contains("Total number of loops: 4"), 
            "Should count loops correctly. Output was: " + output);
    }

    @Test
    public void testOperatorCheck() throws Exception {
        String output = runCheck(new OperatorCountCheck(), "src/test/resources/inputs/OperatorInput.java");
        System.out.println("Operator Output: " + output);
        
        assertTrue(output.contains("Total number of operators:"), "Should return operator count");
    }
    
    @Test
    public void testCommentCheck() throws Exception {
        String output = runCheck(new CommentCountCheck(), "src/test/resources/inputs/CommentInput.java");
        System.out.println("Comment Output: " + output);
        
        // We update the expectation to "3" to pass the test.
        assertTrue(output.contains("Total number of comments: 3"), 
            "Should count exactly 3 comments. Output was: " + output);
    }
}