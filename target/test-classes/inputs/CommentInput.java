package inputs;

public class CommentInput {
    
    // This is a single line comment (Count: 1)
    
    void main() {
        /* This is a block comment
           (Count: 2) 
        */
        int x = 10;
        
        // The following lines are Strings, NOT comments. Your check should ignore them.
        String fake1 = "// This looks like a comment but is a string";
        String fake2 = "/* This looks like a block comment but is a string */";
    }
}