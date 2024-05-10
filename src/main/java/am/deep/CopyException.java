package am.deep;

public class CopyException extends RuntimeException{
    public CopyException(Exception exception) {
        super("Failed to copy object " + exception.getMessage());
    }
}
