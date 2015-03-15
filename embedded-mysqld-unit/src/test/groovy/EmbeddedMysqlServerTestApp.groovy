import pl.info.rkluszczynski.tools.mysql.EmbeddedMysqlServer

class EmbeddedMysqlServerTestApp {

    public static void main(String[] args) {
        def server = new EmbeddedMysqlServer.Builder().build()
        server.start()
        println "TRALA LALA"
        server.shutdown()
    }
}
