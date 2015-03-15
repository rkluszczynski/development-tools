import pl.info.rkluszczynski.tools.mysql.EmbeddedMysqlServerBuilder

class EmbeddedMysqldTestApp {

    public static void main(String[] args) {
        def server = EmbeddedMysqlServerBuilder.newInstance().build()
        server.start()

        ShowDatabasesJdbcQuery.getDatabasesList(server).each {
            println ">>> $it"
        }

        server.shutdown()
    }
}
