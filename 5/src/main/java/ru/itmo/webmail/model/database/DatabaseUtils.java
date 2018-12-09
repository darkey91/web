package ru.itmo.webmail.model.database;

import javafx.util.Pair;
import org.mariadb.jdbc.MariaDbDataSource;
import ru.itmo.webmail.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class DatabaseUtils {
    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    private static final class DataSourceHolder {
        private static final DataSource INSTANCE;
        private static final Properties PROPERTIES = new Properties();

        static {
            try {
                PROPERTIES.load(DataSourceHolder.class.getResourceAsStream("/application.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Can't load application.properties.", e);
            }

            try {
                MariaDbDataSource dataSource = new MariaDbDataSource();
                dataSource.setUrl(PROPERTIES.getProperty("database.url"));
                dataSource.setUser(PROPERTIES.getProperty("database.user"));
                dataSource.setPassword(PROPERTIES.getProperty("database.password"));
                INSTANCE = dataSource;
            } catch (SQLException e) {
                throw new RuntimeException("Can't initialize DB.", e);
            }

            try (Connection connection = INSTANCE.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't get testing connection from DB.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't get testing connection from DB.", e);
            }
        }
    }

    public static Pair<ResultSet, ResultSetMetaData> executeQuery(String query, String errorMessage, Object... parameters) {
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    query)) {
                setParametersToStatement(statement, parameters);
                return new Pair<>(statement.executeQuery(), statement.getMetaData());
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
    }

    public static ResultSet executeUpdate(String query, String errorMessage, Object... parameters) {
        try (Connection connection = getDataSource().getConnection()) {
            if (query.startsWith("INSERT")) {
                try (PreparedStatement statement = connection.prepareStatement(
                        query, Statement.RETURN_GENERATED_KEYS)) {
                    setParametersToStatement(statement, parameters);
                    if (statement.executeUpdate() == 1) {
                        return statement.getGeneratedKeys();
                    } else {
                        return null;
                    }
                }
            } else if (query.startsWith("UPDATE")) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    setParametersToStatement(statement, parameters);
                    if (statement.executeUpdate() == 1) {
                        return statement.getResultSet();
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
        return null;
    }
    public static Date findCreationTime(long id, String query, String errorMessage) {
        try {
            Pair<ResultSet, ResultSetMetaData> answer = executeQuery(query, errorMessage, id);
            if (answer.getKey().next()) {
                return answer.getKey().getTimestamp(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.creationTime by id.", e);
        }
    }


    private static void setParametersToStatement(PreparedStatement statement, Object... parameters) {
        try {
            for (int i = 1; i <= parameters.length; i++) {
                switch (parameters[i - 1].getClass().getSimpleName()) {
                    case "String":
                        statement.setString(i, parameters[i - 1].toString());
                        break;
                    case "Boolean":
                        statement.setBoolean(i, (Boolean) parameters[i - 1]);
                        break;
                    case "Long":
                        statement.setLong(i, (Long) parameters[i - 1]);
                        break;
                    default:

                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't set parameters in query", e);
        }

    }
}
