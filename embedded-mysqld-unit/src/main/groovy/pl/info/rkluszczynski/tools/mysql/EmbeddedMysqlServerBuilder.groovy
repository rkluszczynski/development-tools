package pl.info.rkluszczynski.tools.mysql

class EmbeddedMysqlServerBuilder {
    private String databaseName = 'test_db'
    private int port = 3306
    private String username = 'root'
    private String password = ''

    EmbeddedMysqlServerBuilder withDatabaseName(String databaseName) {
        this.databaseName = databaseName
        return this
    }

    EmbeddedMysqlServerBuilder withPort(int port) {
        this.port = port
        return this
    }

    EmbeddedMysqlServerBuilder withUsername(String username) {
        this.username = username
        return this
    }

    EmbeddedMysqlServerBuilder withPassword(String password) {
        this.password = password
        return this
    }

    EmbeddedMysqlServer build() {
        return new EmbeddedMysqlServer(this);
    }

    static EmbeddedMysqlServerBuilder newInstance() {
        return new EmbeddedMysqlServerBuilder()
    }
}
