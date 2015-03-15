package pl.info.rkluszczynski.tools.mysql

import com.mysql.management.MysqldResource
import com.mysql.management.MysqldResourceI
import org.apache.commons.io.FileUtils

class EmbeddedMysqlServer {
    private final String baseDatabaseDir = System.getProperty('java.io.tmpdir')
    private final String databaseName
    private final int port
    private final String username
    private final String password

    private MysqldResource mysqldResource

    private EmbeddedMysqlServer(builder) {
        this.databaseName = builder.databaseName
        this.port = builder.port
        this.username = builder.username
        this.password = builder.password
    }

//    EmbeddedMysqlDatabase createDatabase() {
//        if (!mysqldResource.isRunning()) {
//            println("MySQL instance not found... Terminating");
//            throw new RuntimeException("Cannot get Datasource, MySQL instance not started.");
//        }
//
//        EmbeddedMysqlDatabase database = new EmbeddedMysqlDatabase(mysqldResource);
//        database.setDriverClassName("com.mysql.jdbc.Driver");
//        database.setUsername(username);
//        database.setPassword(password);
//        String url = "jdbc:mysql://localhost:" + port + "/" + databaseName + "?" + "createDatabaseIfNotExist=true";
//
//        if (!foreignKeyCheck) {
//            url += "&sessionVariables=FOREIGN_KEY_CHECKS=0";
//        }
//        LOG.debug("database url: {}", url);
//        database.setUrl(url);
//        return database;
//    }

    synchronized def start() {
        println("=============== Starting Embedded MySQL using these parameters ===============");
        println("baseDatabaseDir : " + baseDatabaseDir);
        println("databaseName : " + databaseName);
        println("host : localhost (hardcoded)");
        println("port : " + port);
        println("username : root (hardcode)");
        println("password : (no password)");
        println("=============================================================================");

        Map<String, String> databaseOptions = new HashMap<String, String>();
        databaseOptions.put(MysqldResourceI.PORT, Integer.toString(port));

        mysqldResource = new MysqldResource(new File(baseDatabaseDir, databaseName));
        mysqldResource.start("embedded-mysqld-thread-" + System.currentTimeMillis(), databaseOptions);

        if (!mysqldResource.isRunning()) {
            throw new RuntimeException("MySQL did not start.");
        }

        println("MySQL started successfully @ {}" + System.currentTimeMillis());
    }

    synchronized def isRunning() {
        return mysqldResource?.isRunning();
    }

    synchronized def shutdown() {
        if (mysqldResource != null) {
            mysqldResource.shutdown();
            if (!mysqldResource.isRunning()) {
                println(">>>>>>>>>> DELETING MYSQL BASE DIR [{}] <<<<<<<<<<" + mysqldResource.getBaseDir());
                try {
                    FileUtils.forceDelete(mysqldResource.getBaseDir());
                } catch (IOException e) {
                    println(e.getMessage(), e);
                }
            }
            mysqldResource = null
        }
    }

    static class Builder {
        private String databaseName = 'test_db'
        private int port = 3306
        private String username = 'root'
        private String password = ''

        Builder withDatabaseName(databaseName) {
            this.databaseName = databaseName
            return this
        }

        Builder withPort(port) {
            this.port = port
            return this
        }

        Builder withUsername(username) {
            this.username = username
            return this
        }

        Builder withPassword(password) {
            this.password = password
            return this
        }

        EmbeddedMysqlServer build() {
            return new EmbeddedMysqlServer(this);
        }
    }
}
