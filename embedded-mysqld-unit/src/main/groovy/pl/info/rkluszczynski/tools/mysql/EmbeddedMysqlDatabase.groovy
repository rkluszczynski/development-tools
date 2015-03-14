package pl.info.rkluszczynski.tools.mysql

import com.mysql.management.MysqldResource
import org.apache.commons.io.FileUtils
import org.springframework.jdbc.datasource.DriverManagerDataSource

/**
 * Created by Rafal on 2015-03-14.
 */
class EmbeddedMysqlDatabase extends DriverManagerDataSource {
    private final MysqldResource mysqldResource;

    public EmbeddedMysqlDatabase(MysqldResource mysqldResource) {
        this.mysqldResource = mysqldResource;
    }

    public void shutdown() {
        if (mysqldResource != null) {
            mysqldResource.shutdown();
            if (!mysqldResource.isRunning()) {
                logger.info(">>>>>>>>>> DELETING MYSQL BASE DIR [{}] <<<<<<<<<<", mysqldResource.getBaseDir());
                try {
                    FileUtils.forceDelete(mysqldResource.getBaseDir());
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
