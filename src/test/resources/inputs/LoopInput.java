/*
 * LoopInput.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package inputs;

public class LoopInput {
    
    void loopMethod() {
        // Standard For Loop (Count: 1)
        for (int i = 0; i < 10; i++) {
            
            // Nested While Loop (Count: 2)
            while (true) {
                break;
            }
        }

        // Do-While Loop (Count: 3)
        do {
            System.out.println("Run");
        } while (false);
        
        // Enhanced For-Loop (Count: 4)
        int[] numbers = {1, 2, 3};
        for (int n : numbers) {
            System.out.println(n);
        }
    }
}