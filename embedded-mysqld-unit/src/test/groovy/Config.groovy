import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.sql.DataSource

@Configuration
class Config {

    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
        return null
//        new EmbeddedMysqlServerBuilder()
//                .addSqlScript("mysql-test-scripts/create-evaluation-tables.sql")
//                .addSqlScript("mysql-test-scripts/create-tables.sql")
//                .addSqlScript("mysql-test-scripts/insert-data.sql")
//                .build();
    }
}
