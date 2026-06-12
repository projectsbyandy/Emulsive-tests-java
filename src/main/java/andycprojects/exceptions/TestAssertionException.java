package andycprojects.exceptions;

public class TestAssertionException extends AssertionError  {

    public TestAssertionException(String message) {
        super(message);
    }

    public TestAssertionException(String message, Throwable cause) {
        super(message, cause);
    }
}
