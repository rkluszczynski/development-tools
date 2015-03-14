package pl.info.rkluszczynski.jws.message

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.crypto.RSASSAVerifier
import org.codehaus.jackson.map.ObjectMapper
import spock.lang.Specification
import spock.lang.Unroll

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom

/**
 * Created by Rafal on 2015-03-09.
 */
class SignedMessageTest extends Specification {
    private static KeyPair rsaKeyPair

    private ObjectMapper objectMapper
    private SignedMessage signedMessage

    def setupSpec() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, new SecureRandom('fixedSeed'.bytes));
        rsaKeyPair = keyPairGenerator.generateKeyPair()
    }

    def setup() {
        objectMapper = new ObjectMapper()
        signedMessage = new SignedMessage()
        signedMessage.content = objectMapper.writeValueAsString(data)

    }

    @Unroll
    def 'should sign message with correct signature for #description'() {
        when:
        signedMessage.header = new JWSHeader(algorithm).toBase64URL().toString()

        then:
        signedMessage.sign(jwsSigner, objectMapper) == signature

        where:
        jwsSigner                            | algorithm          | signature                    | description
        new MACSigner(SHARED_KEY.bytes)      | JWSAlgorithm.HS256 | SHARED_KEY_MESSAGE_SIGNATURE | 'Shared Key'
        new RSASSASigner(rsaKeyPair.private) | JWSAlgorithm.RS256 | RSA_MESSAGE_SIGNATURE        | 'RSA'
    }

    @Unroll
    def 'should verify correctly signed message for #description'() {
        when:
        signedMessage.header = new JWSHeader(algorithm).toBase64URL().toString()
        signedMessage.signature = signature

        then:
        signedMessage.verify(jwsVerifier, objectMapper)

        where:
        jwsVerifier                           | algorithm          | signature                    | description
        new MACVerifier(SHARED_KEY.bytes)     | JWSAlgorithm.HS256 | SHARED_KEY_MESSAGE_SIGNATURE | "Shared Key"
        new RSASSAVerifier(rsaKeyPair.public) | JWSAlgorithm.RS256 | RSA_MESSAGE_SIGNATURE        | 'RSA'
    }

    private class TestData {
        String id
    }
    private TestData data = new TestData(id: "uuid")

    private static final String SHARED_KEY = '01234567890123456789012345678901'
    private static final String SHARED_KEY_MESSAGE_SIGNATURE = 'Ex3AXSOet9K_-TVMUKdhd3fWvxuARHXU-y5RE1hYlUs'
    private static final String RSA_MESSAGE_SIGNATURE = 'iaRjWbSH5GTQvFM-Xstq8E45nuOsgX3DkF2wDVTGjfYEPJt6Bm2j' +
            '4WEdTzoWqkMVOn_jiyncK03AIEn58JUVQBiGAqLNPeNPHFVj_cb6ru-b_WlFfgLhnBPiUEduyKJEQcFMsAI0W0sg5dmXE8oo' +
            'RSGJo6SyyQNseXxN2MXImDgLru819diYE_rjDTxFMP045Ui0d9fnh91IuoU8j_jIRPZLLxFzn2KN6TZpFB5vuluRflArEVW_' +
            'XcTs3am3ymOSfEOarhLdVRcR5HVNjp6o9IdLPOlczkPSaaIru5Km6TYpTBBENF20a8Dz-AuSa_LaTHMpgyp4Euo7X9MMipA8cw'
}
