package pl.info.rkluszczynski.jws.message;

class SigningInput {
    protected String header;
    protected String content;

    public SigningInput() {
    }

    public SigningInput(String header, String content) {
        this.header = header;
        this.content = content;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
