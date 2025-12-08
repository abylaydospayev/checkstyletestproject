package inputs;

public class OperatorInput {
    
    void math() {
        // Assignments and Arithmetic
        int x = 5 + 3;      // Operators: =, + (Total so far: 2)
        int y = x * 2;      // Operators: =, * (Total so far: 4)
        
        // Unary Operators
        x++;                // Operator: ++ (Total so far: 5)
        y--;                // Operator: -- (Total so far: 6)
        
        // Comparison
        if (x > y) {        // Operator: > (Total so far: 7)
            x = 0;          // Operator: = (Total so far: 8)
        }
        
        // "Fake" operators inside strings (Should NOT be counted)
        String s = "x + y = z"; 
    }
}