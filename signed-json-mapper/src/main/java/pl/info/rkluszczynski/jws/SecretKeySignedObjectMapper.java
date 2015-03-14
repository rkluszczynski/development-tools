package pl.info.rkluszczynski.jws;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.codehaus.jackson.map.ObjectMapper;

public class SecretKeySignedObjectMapper extends SignedObjectMapper {
    public SecretKeySignedObjectMapper(byte[] sharedSecret) {
        this(sharedSecret, null);
    }

    public SecretKeySignedObjectMapper(byte[] sharedSecret, ObjectMapper objectMapper) {
        super(
                new JWSHeader(JWSAlgorithm.HS256),
                new MACSigner(sharedSecret),
                new MACVerifier(sharedSecret),
                objectMapper
        );
    }
}
