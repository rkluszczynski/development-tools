package pl.info.rkluszczynski.tools.mysql

class EmbeddedMysqlDatabase {
//class EmbeddedMysqlDatabase extends DriverManagerDataSource {
//    private final MysqldResource mysqldResource;
//
//    public EmbeddedMysqlDatabase(MysqldResource mysqldResource) {
//        this.mysqldResource = mysqldResource;
//    }
//
//    public void shutdown() {
//        if (mysqldResource != null) {
//            mysqldResource.shutdown();
//            if (!mysqldResource.isRunning()) {
//                logger.info(">>>>>>>>>> DELETING MYSQL BASE DIR [{}] <<<<<<<<<<", mysqldResource.getBaseDir());
//                try {
//                    FileUtils.forceDelete(mysqldResource.getBaseDir());
//                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//        }
//    }
}
