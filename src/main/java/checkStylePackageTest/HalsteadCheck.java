/*
 * HalsteadCheck.java
 * Abylay Dospayev
 * Cpts 422
 * Fall 2025
 */

package checkStylePackageTest;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.HashSet;
import java.util.Set;

/**
 * A custom Checkstyle Check that calculates Halstead Metrics:
 * Length, Vocabulary, Volume, Difficulty, and Effort.
 */
public class HalsteadCheck extends AbstractCheck {

    // Unique operators and operands (for Vocabulary)
    private final Set<String> uniqueOperators = new HashSet<>();
    private final Set<String> uniqueOperands = new HashSet<>();

    // Total counts (for Length)
    private int totalOperators = 0;
    private int totalOperands = 0;

    // Computed metrics
    private double halsteadLength = 0.0;
    private double halsteadVocabulary = 0.0;
    private double halsteadVolume = 0.0;
    private double halsteadDifficulty = 0.0;
    private double halsteadEffort = 0.0;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            // Operands (Variables, Literals, Types)
            TokenTypes.IDENT, 
            TokenTypes.NUM_INT, 
            TokenTypes.NUM_LONG,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_DOUBLE,
            TokenTypes.STRING_LITERAL, 
            TokenTypes.CHAR_LITERAL,

            // Operators (Arithmetic, Logic, Assignment, Relational)
            TokenTypes.ASSIGN, TokenTypes.PLUS, TokenTypes.MINUS, 
            TokenTypes.STAR, TokenTypes.DIV, TokenTypes.MOD,
            TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.MOD_ASSIGN,
            TokenTypes.GT, TokenTypes.LT, TokenTypes.GE, TokenTypes.LE,
            TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
            TokenTypes.LAND, TokenTypes.LOR, TokenTypes.LNOT,
            TokenTypes.INC, TokenTypes.DEC,
            TokenTypes.QUESTION, // Ternary operator
            // Keywords often treated as operators
            TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO, TokenTypes.LITERAL_RETURN,
            // Structure operators
            TokenTypes.DOT, TokenTypes.SEMI, TokenTypes.COMMA
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        uniqueOperators.clear();
        uniqueOperands.clear();
        totalOperators = 0;
        totalOperands = 0;
        halsteadLength = 0;
        halsteadVocabulary = 0;
        halsteadVolume = 0;
        halsteadDifficulty = 0;
        halsteadEffort = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        int type = ast.getType();
        String text = ast.getText();
        String uniqueKey = (text != null) ? text : String.valueOf(type);

        if (isOperand(type)) {
            totalOperands++;
            uniqueOperands.add(uniqueKey);
        } else {
            totalOperators++;
            uniqueOperators.add(uniqueKey);
        }
    }

    private boolean isOperand(int type) {
        return type == TokenTypes.IDENT || 
               type == TokenTypes.NUM_INT || 
               type == TokenTypes.NUM_LONG ||
               type == TokenTypes.NUM_FLOAT ||
               type == TokenTypes.NUM_DOUBLE ||
               type == TokenTypes.STRING_LITERAL || 
               type == TokenTypes.CHAR_LITERAL;
    }

    @Override
    public void finishTree(DetailAST root) {
        // 1. Halstead Length (N) = N1 + N2
        halsteadLength = totalOperators + totalOperands;

        // 2. Halstead Vocabulary (n) = n1 + n2
        halsteadVocabulary = uniqueOperators.size() + uniqueOperands.size();

        // 3. Halstead Volume (V) = N * log2(n)
        if (halsteadVocabulary > 0) {
            halsteadVolume = halsteadLength * (Math.log(halsteadVocabulary) / Math.log(2));
        } else {
            halsteadVolume = 0;
        }

        // 4. Halstead Difficulty (D) = (n1 / 2) * (N2 / n2)
        // D = (Unique Operators / 2) * (Total Operands / Unique Operands)
        if (uniqueOperands.size() > 0) {
            halsteadDifficulty = (uniqueOperators.size() / 2.0) * ((double) totalOperands / uniqueOperands.size());
        } else {
            halsteadDifficulty = 0;
        }

        // 5. Halstead Effort (E) = D * V
        halsteadEffort = halsteadDifficulty * halsteadVolume;

        // Log results
        log(root.getLineNo(), String.format("Halstead Length: %.2f", halsteadLength));
        log(root.getLineNo(), String.format("Halstead Vocabulary: %.2f", halsteadVocabulary));
        log(root.getLineNo(), String.format("Halstead Volume: %.2f", halsteadVolume));
        log(root.getLineNo(), String.format("Halstead Difficulty: %.2f", halsteadDifficulty));
        log(root.getLineNo(), String.format("Halstead Effort: %.2f", halsteadEffort));
    }

    // Getters for Testing
    public double getHalsteadLength() { return halsteadLength; }
    public double getHalsteadVocabulary() { return halsteadVocabulary; }
    public double getHalsteadVolume() { return halsteadVolume; }
    public double getHalsteadDifficulty() { return halsteadDifficulty; }
    public double getHalsteadEffort() { return halsteadEffort; }
}