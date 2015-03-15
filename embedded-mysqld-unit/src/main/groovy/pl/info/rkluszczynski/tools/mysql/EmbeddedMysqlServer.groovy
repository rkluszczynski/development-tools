package pl.info.rkluszczynski.tools.mysql

import com.mysql.management.MysqldResource
import com.mysql.management.MysqldResourceI
import groovy.transform.PackageScope
import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EmbeddedMysqlServer {
    private final def baseDir = System.getProperty('java.io.tmpdir')
    private final def database
    private final def port
    private final def username
    private final def password

    private MysqldResource mysqldResource

    @PackageScope EmbeddedMysqlServer(builder) {
        this.database = builder.databaseName
        this.port = builder.port
        this.username = builder.username
        this.password = builder.password
    }

    synchronized def start() {
        if (logger.isDebugEnabled()) {
            logger.debug('=============== Starting Embedded MySQL ===============')
            logger.debug('         baseDir : {}', baseDir)
            logger.debug('        database : {}', database)
            logger.debug('            host : localhost')
            logger.debug('            port : {}', port)
            logger.debug('        username : {}', username)
            logger.debug('        password : {}', password == '' ? '(no password)' : password)
            logger.debug('=======================================================')
        }
        Map<String, String> databaseOptions = new HashMap<String, String>()
        databaseOptions.put(MysqldResourceI.PORT, Integer.toString(port))

        mysqldResource = new MysqldResource(new File(baseDir, database))
        mysqldResource.start("embedded-mysqld-thread-${System.currentTimeMillis()}", databaseOptions)

        if (!isRunning()) {
            throw new RuntimeException('MySQL did not start.')
        }
        logger.info('MySQL started successfully @ {}', System.currentTimeMillis())
    }

    synchronized def isRunning() {
        return mysqldResource != null && mysqldResource.isRunning()
    }

    synchronized def shutdown() {
        mysqldResource?.shutdown()
        if (!isRunning()) {
            logger.info('Deleting embedded MySQL base directory : {}', mysqldResource.getBaseDir())
            try {
                FileUtils.forceDelete(mysqldResource.getBaseDir())
            } catch (IOException e) {
                logger.warn(e.getMessage(), e)
            }
        }
    }

    def getJdbcUrl() {
        return "jdbc:mysql://localhost:$port"
    }

    def getDatabaseName() {
        return database
    }

    def getUsername() {
        return username
    }

    def getPassword() {
        return password
    }

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedMysqlServer.class)
}
