package pl.info.rkluszczynski.jws.exception;

public class MessageVerificationException extends SignedObjectException {
    public MessageVerificationException() {
        super(FAILED_VERIFICATION_MESSAGE);
    }

    private static final String FAILED_VERIFICATION_MESSAGE = "Message verification failed";
}
