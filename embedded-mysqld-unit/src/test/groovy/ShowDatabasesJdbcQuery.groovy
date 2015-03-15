import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class ShowDatabasesJdbcQuery {

    static def getDatabasesList(server) {
        Class.forName("com.mysql.jdbc.Driver");
        def result = []

        Connection connection = DriverManager.getConnection(
                server.getJdbcUrl(),
                server.getUsername(),
                server.getPassword()
        )
        Statement statement = connection.createStatement()
        ResultSet resultSet = statement.executeQuery('SHOW DATABASES;')

        while (resultSet.next()) { result.add(resultSet.getString(1)) }

        resultSet.close();
        statement.close();
        connection.close();

        result
    }
}
