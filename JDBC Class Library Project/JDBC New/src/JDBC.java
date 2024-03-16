import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC  {
    private Connection connection;

    public JDBC(String connectionUrl, String username, String password) throws SQLException {
        try {
            // Load the SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC Driver not found.");
            e.printStackTrace();
            return;
        }
        this.connection = DriverManager.getConnection(connectionUrl, username, password);
    }

    public void executeUpdate(String sqlUpdateQuery) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(sqlUpdateQuery);
            System.out.println(rowsAffected + " row(s) affected");
        }
    }

    public void executeQuery(String sqlSelectQuery) throws SQLException {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery(sqlSelectQuery)) {
    
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
    
            // Determine proper column widths
            int[] columnWidths = new int[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnWidths[i - 1] = rsmd.getColumnName(i).length();
            }
    
            // Check the width of data in each column
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = resultSet.getString(i);
                    if (columnValue != null && columnValue.length() > columnWidths[i - 1]) {
                        columnWidths[i - 1] = columnValue.length();
                    }
                }
            }
    
            // Move cursor to the beginning of the ResultSet
            resultSet.beforeFirst();
    
            // Print headers with proper spacing
            for (int i = 0; i < columnCount; i++) {
                System.out.printf("%-" + (columnWidths[i] + 2) + "s", rsmd.getColumnName(i + 1));
            }
            System.out.println();
    
            // Print data with proper spacing
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-" + (columnWidths[i - 1] + 2) + "s", resultSet.getString(i));
                }
                System.out.println();
            }
        }
    }
    
}

