package pl.info.rkluszczynski.jws;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import org.codehaus.jackson.map.ObjectMapper;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSASignedObjectMapper extends SignedObjectMapper {
    public RSASignedObjectMapper(RSAPrivateKey privateKey) {
        this(privateKey, null, null);
    }

    public RSASignedObjectMapper(RSAPrivateKey privateKey, ObjectMapper objectMapper) {
        this(privateKey, null, objectMapper);
    }

    public RSASignedObjectMapper(RSAPublicKey publicKey) {
        this(null, publicKey, null);
    }

    public RSASignedObjectMapper(RSAPublicKey publicKey, ObjectMapper objectMapper) {
        this(null, publicKey, objectMapper);
    }

    public RSASignedObjectMapper(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this(privateKey, publicKey, null);
    }

    public RSASignedObjectMapper(RSAPrivateKey privateKey, RSAPublicKey publicKey, ObjectMapper objectMapper) {
        super(
                new JWSHeader(JWSAlgorithm.RS256),
                privateKey == null ? null : new RSASSASigner(privateKey),
                publicKey == null ? null : new RSASSAVerifier(publicKey),
                objectMapper
        );
    }
}
