# Custom Checkstyle Metrics

This repo contains custom Checkstyle checks for CptS 422.  
They run as an Eclipse Checkstyle plugin and are tested with JUnit 5 + Mockito.

---

## Implemented Checks

### 1. CommentCountCheck

**What it does**

Counts how many comments are in a Java file and logs the total.

**What we count as a comment**

- Single-line comments: `// ...`
- Block comments: `/* ... */` (including Javadoc `/** ... */`, counted as one block)
- At the end of the file, it logs:  
  `Total number of comments: X` on the first line.

---

### 2. LoopCountCheck

**What it does**

Counts how many loops are in a Java file and logs the total.

**What we count as a loop**

- `for` loops (classic and enhanced for-each)
- `while` loops
- `do { ... } while (...);` loops

**Special rule**

- A `do { ... } while (...);` is counted **once**:
  - `LITERAL_DO` counts +1
  - the matching `LITERAL_WHILE` is **not** counted again  
- Logs: `Total number of loops: X` on the first line.

---

### 3. OperatorCountCheck

**What it does**

Counts how many operators appear in the file and logs the total.

**What we count as an operator**

We count common operators used in Halstead metrics, including:

- Assignment / compound assignment: `=`, `+=`, `-=`, `*=`, `/=`, `%=`
- Arithmetic: `+`, `-`, `*`, `/`, `%`
- Logical: `&&`, `||`
- Bitwise: `&`, `|`, `^`
- Relational / equality: `<`, `>`, `<=`, `>=`, `==`, `!=`
- Increment / decrement: `++`, `--`
- Ternary: `?`, `:`

Anything else (like identifiers) is ignored.  
Logs: `Total number of operators: X` on the first line.

---

## Testing & Coverage

- Tests: `CommentCountCheckTest`, `LoopCountCheckTest`, `OperatorCountCheckTest`
- Tools: **JUnit 5** and **Mockito**
- Each test class checks:
  - Token registration (`getDefaultTokens`, etc.)
  - Counter behavior in `beginTree`, `visitToken`, `finishTree`
  - That non-relevant tokens do **not** change the counters

**Coverage (Eclipse Coverage Tool)**

- Whole project: **96.5%**
- `checkStylePackageTest` package: **91.1%**
  - `CommentCountCheck.java`: **80.0%**
  - `LoopCountCheck.java`: **88.6%**
  - `OperatorCountCheck.java`: **95.0%**

Remaining uncovered lines are mostly in `finishTree(...)` around logging / defensive paths that are hard to trigger in unit tests, but all existing tests pass.
