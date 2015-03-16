package pl.info.rkluszczynski.tools.mysql;

import com.mysql.management.MysqldResource;
import com.mysql.management.MysqldResourceI;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmbeddedMysqlServer {
    private final String baseDir = System.getProperty("java.io.tmpdir");
    private final String database;
    private final int port;
    private final String username;
    private final String password;

    private MysqldResource mysqldResource;

    EmbeddedMysqlServer(EmbeddedMysqlServerBuilder builder) {
        this.database = builder.getDatabaseName();
        this.port = builder.getPort();
        this.username = builder.getUsername();
        this.password = builder.getPassword();
    }

    public synchronized void start() {
        if (logger.isDebugEnabled()) {
            logger.debug("=============== Starting Embedded MySQL ===============");
            logger.debug("         baseDir : {}", baseDir);
            logger.debug("        database : {}", database);
            logger.debug("            host : localhost");
            logger.debug("            port : {}", port);
            logger.debug("        username : {}", username);
            logger.debug("        password : {}", password.equals("") ? "(no password)" : password);
            logger.debug("=======================================================");
        }
        Map<String, String> databaseOptions = new HashMap<String, String>();
        databaseOptions.put(MysqldResourceI.PORT, Integer.toString(port));

        mysqldResource = new MysqldResource(new File(baseDir, database));
        mysqldResource.start("embedded-mysqld-thread-" + String.valueOf(System.currentTimeMillis()), databaseOptions);

        if (!isRunning()) {
            throw new RuntimeException("MySQL did not start.");
        }

        logger.info("MySQL started successfully @ {}", System.currentTimeMillis());
    }

    public synchronized boolean isRunning() {
        return mysqldResource != null && mysqldResource.isRunning();
    }

    public synchronized void shutdown() {
        mysqldResource.shutdown();
        if (!isRunning()) {
            logger.info("Deleting embedded MySQL base directory : {}", mysqldResource.getBaseDir());
            try {
                FileUtils.forceDelete(mysqldResource.getBaseDir());
            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }

        }

    }

    public String getJdbcUrl() {
        return "jdbc:mysql://localhost:" + String.valueOf(port);
    }

    public String getDatabaseName() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedMysqlServer.class);
}
