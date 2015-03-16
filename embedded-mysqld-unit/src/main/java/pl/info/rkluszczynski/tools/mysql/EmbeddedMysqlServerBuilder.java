package pl.info.rkluszczynski.tools.mysql;

public class EmbeddedMysqlServerBuilder {
    private String databaseName = "test_db";
    private int port = 3306;
    private String username = "root";
    private String password = "";

    public String getDatabaseName() {
        return databaseName;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public EmbeddedMysqlServerBuilder withDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public EmbeddedMysqlServerBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public EmbeddedMysqlServerBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public EmbeddedMysqlServerBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public EmbeddedMysqlServer build() {
        return new EmbeddedMysqlServer(this);
    }

    public static EmbeddedMysqlServerBuilder newInstance() {
        return new EmbeddedMysqlServerBuilder();
    }
}
