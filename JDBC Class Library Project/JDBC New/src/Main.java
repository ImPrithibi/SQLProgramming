import java.sql.SQLException;
public class Main{
    public static void main(String[] args) {
        try {
        
        String connectionUrl = "jdbc:sqlserver://localhost:13001;databaseName=master;encrypt=true;trustServerCertificate=true;user=sa;password=PH@123456789";
        String username = "sa";
        String password = "PH@123456789";

            JDBC dbConnector = new JDBC(connectionUrl, username, password);

            // Execute the SQL Lookup Query
            String sqlQueryLookup = 
                
            """
                USE [TSQLV4];
                         SELECT
                           E1.empid, E1.firstname, E1.lastname,
                           E2.empid, E2.firstname, E2.lastname
                         FROM HR.Employees AS E1
                           INNER JOIN HR.Employees AS E2
                             ON E1.empid < E2.empid
                         ORDER BY E1.empid;
                                           
               """;
            dbConnector.executeQuery(sqlQueryLookup);

            // Execute the SQL Update Query
            String sqlQueryUpdate = 
            """
                UPDATE [Northwinds2022TSQLV7].[HumanResources].[Employee]
                SET EmployeeLastName = '999'
                WHERE EmployeeID = 1;
                """;
            dbConnector.executeUpdate(sqlQueryUpdate);

        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        }
    }
} 