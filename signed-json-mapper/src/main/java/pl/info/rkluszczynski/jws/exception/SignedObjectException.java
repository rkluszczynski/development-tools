package pl.info.rkluszczynski.jws.exception;

public class SignedObjectException extends Exception {
    public SignedObjectException(Throwable cause) {
        super(cause);
    }

    public SignedObjectException(String message) {
        super(message);
    }
}
