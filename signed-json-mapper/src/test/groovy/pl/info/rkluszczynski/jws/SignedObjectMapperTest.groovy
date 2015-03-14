package pl.info.rkluszczynski.jws

import spock.lang.Specification
import spock.lang.Unroll

import java.security.KeyPair
import java.security.KeyPairGenerator

/**
 * Created by rkluszczynski on 07.03.15.
 */
class SignedObjectMapperTest extends Specification {
    private static KeyPair rsaKeyPair

    def setupSpec() {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(4096);
        rsaKeyPair = keyGenerator.genKeyPair();
    }

    @Unroll
    def 'should return the same data after mapping to and from using #description mapper'() {
        given:
        def inputMessageObject = new TestMessageObject(
                messageId: 17,
                messageDescription: "Cowabanga!",
                messageEnum: TestMessageObject.TestMessageEnum.VALUE_2
        )

        when:
        SignedObjectMapper mapper = signedObjectMapper
        def receivedMessageObject = mapper.readValue(
                mapper.writeValueAsString(inputMessageObject),
                TestMessageObject.class
        )

        then:
        inputMessageObject == receivedMessageObject

        where:
        signedObjectMapper                                               | description
        new SecretKeySignedObjectMapper(SHARED_SECRET_KEY.bytes)         | "Shared Key"
        new RSASignedObjectMapper(rsaKeyPair.private, rsaKeyPair.public) | "RSA"

    }

    private static final String SHARED_SECRET_KEY = 'SharedSecretKeyOfAtLeast256bits!'
}
