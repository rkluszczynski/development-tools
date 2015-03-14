package pl.info.rkluszczynski.jws;

import com.google.common.base.Preconditions;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import pl.info.rkluszczynski.jws.exception.MessageVerificationException;
import pl.info.rkluszczynski.jws.exception.SignedObjectException;
import pl.info.rkluszczynski.jws.message.SignedMessage;

import java.io.IOException;
import java.text.ParseException;

class SignedObjectMapper {
    private final JWSHeader header;
    private final JWSSigner signer;
    private final JWSVerifier verifier;
    private final ObjectMapper objectMapper;
    private final ObjectMapper internalObjectMapper;

    SignedObjectMapper(JWSHeader header, JWSSigner signer, JWSVerifier verifier, ObjectMapper objectMapper) {
        this.header = header;
        this.signer = signer;
        this.verifier = verifier;

        this.objectMapper = (objectMapper == null) ? createDefaultObjectMapper(false) : objectMapper;
        internalObjectMapper = createDefaultObjectMapper(true);
    }

    private ObjectMapper createDefaultObjectMapper(boolean indentOutput) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        if (indentOutput) {
            mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        }
        return mapper;
    }

    public String writeValueAsString(Object value) throws SignedObjectException {
        Preconditions.checkNotNull(signer);

        SignedMessage message = new SignedMessage();
        message.setHeader(header.toBase64URL().toString());
        try {
            message.setContent(
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value)
            );
            message.sign(signer, internalObjectMapper);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
        } catch (IOException | ParseException | JOSEException ex) {
            throw new SignedObjectException(ex);
        }
    }

    public <T> T readValue(String content, Class<T> valueType) throws SignedObjectException {
        Preconditions.checkNotNull(verifier);
        try {
            SignedMessage receivedMessage = objectMapper.readValue(content, SignedMessage.class);
            if (!receivedMessage.verify(verifier, internalObjectMapper)) {
                throw new MessageVerificationException();
            }
            return objectMapper.readValue(receivedMessage.getContent(), valueType);
        } catch (IOException | ParseException | JOSEException ex) {
            throw new SignedObjectException(ex);
        }
    }
}
