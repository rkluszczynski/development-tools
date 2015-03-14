package pl.info.rkluszczynski.jws

class TestMessageObject {
    Integer messageId
    String messageDescription
    TestMessageEnum messageEnum

    @Override
    public String toString() {
        return "TestMessage{messageId=$messageId, messageDescription='$messageDescription', messageEnum=$messageEnum}";
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        TestMessageObject that = (TestMessageObject) o

        if (messageDescription != that.messageDescription) return false
        if (messageEnum != that.messageEnum) return false
        if (messageId != that.messageId) return false

        return true
    }

    int hashCode() {
        int result
        result = (messageId != null ? messageId.hashCode() : 0)
        result = 31 * result + (messageDescription != null ? messageDescription.hashCode() : 0)
        result = 31 * result + (messageEnum != null ? messageEnum.hashCode() : 0)
        return result
    }

    enum TestMessageEnum {
        VALUE_1,
        VALUE_2
    }
}
