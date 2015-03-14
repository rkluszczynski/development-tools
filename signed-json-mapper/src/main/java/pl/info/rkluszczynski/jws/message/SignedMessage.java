package pl.info.rkluszczynski.jws.message;

import com.google.common.base.Preconditions;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.util.Base64URL;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;

public class SignedMessage extends SigningInput {
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String sign(JWSSigner signer, ObjectMapper objectMapper)
            throws IOException, ParseException, JOSEException {
        byte[] dataBytes = getSigningInputBytes(objectMapper);
        signature = signer.sign(getJWSHeader(), dataBytes).toString();
        return signature;
    }

    public boolean verify(JWSVerifier verifier, ObjectMapper objectMapper)
            throws IOException, ParseException, JOSEException {
        Preconditions.checkNotNull(getSignature(), MISSING_SIGNATURE_MESSAGE);
        byte[] dataBytes = getSigningInputBytes(objectMapper);
        return verifier.verify(getJWSHeader(), dataBytes, new Base64URL(getSignature()));
    }

    @Override
    public String toString() {
        return String.format("SignedMessage{header='%s', content='%s', signature='%s'}", header, content, signature);
    }

    private byte[] getSigningInputBytes(ObjectMapper objectMapper) throws IOException {
        SigningInput signingInput = new SigningInput(getHeader(), getContent());
        String jsonString = objectMapper.writeValueAsString(signingInput);
        return jsonString.getBytes(Charset.forName("UTF-8"));
    }

    private JWSHeader getJWSHeader() throws ParseException {
        Preconditions.checkNotNull(getHeader(), MISSING_HEADER_MESSAGE);
        return JWSHeader.parse(new Base64URL(getHeader()));
    }

    private static final String MISSING_HEADER_MESSAGE = "Missing message header!";
    private static final String MISSING_SIGNATURE_MESSAGE = "Missing message signature!";
}
