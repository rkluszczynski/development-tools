package pl.info.rkluszczynski.tools.mysql

class EmbeddedMysqlServerBuilder {
    private def databaseName = 'test_db'
    private def port = 3306
    private def username = 'root'
    private def password = ''

    def withDatabaseName(databaseName) {
        this.databaseName = databaseName
        return this
    }

    def withPort(port) {
        this.port = port
        return this
    }

    def withUsername(username) {
        this.username = username
        return this
    }

    def withPassword(password) {
        this.password = password
        return this
    }

    def build() {
        return new EmbeddedMysqlServer(this);
    }

    static def newInstance() {
        return new EmbeddedMysqlServerBuilder()
    }
}
