package game;

public class TEST {
    /**
     * Asserts that two objects refer to the same object. If they are not, an
     * AssertionError is thrown.
     *
     * @param expected Expected object
     * @param actual   Actual object
     * @throws AssertionError if the two objects do not refer to the same object
     */
    public static void assertSame(Object expected, Object actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError is
     * thrown.
     *
     * @param expected Expected object
     * @param actual   Actual object
     * @throws AssertionError if the two objects are not equal
     */
    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    /**
     * Asserts that a condition is true. If it isn't, an AssertionError is thrown.
     *
     * @param condition Condition to be checked
     * @throws AssertionError if the condition is false
     */
    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected true but got false");
        }
    }

    /**
     * Asserts that a condition is false. If it isn't, an AssertionError is thrown.
     *
     * @param condition Condition to be checked
     * @throws AssertionError if the condition is true
     */
    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Expected false but got true");
        }
    }

    /**
     * Asserts that an object isn't null. If it is, an AssertionError is thrown.
     *
     * @param object Object to be checked
     * @throws AssertionError if the object is null
     */
    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Expected not null but got null");
        }
    }
}
