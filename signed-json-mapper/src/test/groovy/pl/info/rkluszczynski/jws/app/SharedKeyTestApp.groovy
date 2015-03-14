package pl.info.rkluszczynski.jws.app

import pl.info.rkluszczynski.jws.SecretKeySignedObjectMapper
import pl.info.rkluszczynski.jws.TestMessageObject


class SharedKeyTestApp {

    static SecretKeySignedObjectMapper signedObjectMapper = new SecretKeySignedObjectMapper(
            '01234567890123456789012345678901'.bytes
    );

    public static void main(String[] args) {
        TestMessageObject data = new TestMessageObject(
                messageId: 123L,
                messageDescription: "Cowabanga!",
                messageEnum: TestMessageObject.TestMessageEnum.VALUE_1
        )
        System.out.println("DATA: " + data);

        String json = signedObjectMapper.writeValueAsString(data);
        System.out.println("JSON: " + json);

        TestMessageObject read = signedObjectMapper.readValue(json, TestMessageObject.class);
        System.out.println("READ: " + read)
    }
}
