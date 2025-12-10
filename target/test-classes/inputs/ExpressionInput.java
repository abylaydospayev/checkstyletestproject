// ExpressionInput.java

public class ExpressionInput {
    private int field = 0;

    public void exampleMethod() {
        int a;
        a = 10;                     // 1. Assignment expression
        a += 5;                     // 2. Assignment expression
        int b = a * 2 + 1;          // 3. Assignment and an arithmetic expression
        field++;                    // 4. Post-increment expression
        field = new Integer(1).intValue(); // 5. Object creation and method call expressions
        
        if (a > 10) {
            System.out.println("Result: " + (a > 20 ? "High" : "Low")); // 6. Method call and 7. Conditional expression
        }
    }
}