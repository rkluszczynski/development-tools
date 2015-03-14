# embedded-mysqld-unit

## Objectives


## Example Bean Creation

    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
        return new EmbeddedMysqlDatabaseBuilder()
                .addSqlScript("mysql-test-scripts/create-evaluation-tables.sql")
                .addSqlScript("mysql-test-scripts/create-tables.sql")
                .addSqlScript("mysql-test-scripts/insert-data.sql")
                .build();
    }
