package pl.info.rkluszczynski.tools.mysql

import com.mysql.management.MysqldResource
import com.mysql.management.MysqldResourceI

/**
 * Created by Rafal on 2015-03-14.
 */
class EmbeddedMysqlServer {
    private String baseDatabaseDir
    private String databaseName
    private Integer port
    private String username
    private String password

    private MysqldResource mysqldResource

    EmbeddedMysqlDatabase createDatabase() {
        if (!mysqldResource.isRunning()) {
            println("MySQL instance not found... Terminating");
            throw new RuntimeException("Cannot get Datasource, MySQL instance not started.");
        }

        EmbeddedMysqlDatabase database = new EmbeddedMysqlDatabase(mysqldResource);
        database.setDriverClassName("com.mysql.jdbc.Driver");
        database.setUsername(username);
        database.setPassword(password);
        String url = "jdbc:mysql://localhost:" + port + "/" + databaseName + "?" + "createDatabaseIfNotExist=true";

        if (!foreignKeyCheck) {
            url += "&sessionVariables=FOREIGN_KEY_CHECKS=0";
        }
        LOG.debug("database url: {}", url);
        database.setUrl(url);
        return database;
    }

    def start() {
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

        println("MySQL started successfully @ {}", System.currentTimeMillis());
    }
}
