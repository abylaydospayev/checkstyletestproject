# Custom Checkstyle Metrics

This repository contains custom Checkstyle checks created for a class assignment. The checks are designed to run within the Eclipse Checkstyle plugin.

***

## Implemented Checks

### 1. CommentCountCheck

* **Purpose**: This check counts the total number of comments in a Java file and logs the result.
* **Assumptions**:
    * A "comment" is defined as either a single-line comment (`//`) or a block comment (`/* ... */`).
    * Javadoc comments (`/** ... */`) are counted as a single block comment.
    * The final count is reported as an informational message attached to the first line of the file.

### 2. LoopCountCheck

* **Purpose**: This check counts the total number of loops in a Java file and logs the result.
* **Assumptions**:
    * A "loop" is defined as a `for` statement (including enhanced for-each loops), a `while` statement, or a `do-while` statement.
    * The final count is reported as an informational message attached to the first line of the file.
