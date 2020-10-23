/*
CST 467 - Assignment 1
Fall 2020
Part 3
*/

package roosevelt.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Saily
 */
public class UseEmployeeTable {

    public static void main(String args[]) {
        String driverName = "org.apache.derby.jdbc.ClientDriver";
        Connection conn = null;
        int count;
        try {
            //3.1 Connecting to the database
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/marudwar", "marudwar", "marudwar");
        } catch (InstantiationException ex) {
            System.out.println("Could not Instantiate Derby:" + ex.getMessage());
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            System.out.println("Could not Access Derby Database:" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Derby SQL Exception Occured:" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Error in Referencing Derby Driver:" + ex.getMessage());
            ex.printStackTrace();
        }
        String query = "";
        Statement stmt;
        try {
            if (conn != null) {
                //3.2 Check if there exists an employee with the id 555555555 
                stmt = conn.createStatement();
                query = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE WHERE \"EMPLOYEE ID\" = '555555555'";
                //query = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE WHERE \"EMPLOYEE ID\" = '814756247'";
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next() && rs.getInt("COUNT") > 0) {
                    System.out.println("Table contains employee with 555555555 ID");
                } else {
                    System.out.println("Table does not contain employee with 555555555 ID");
                }
                //3.3 Employee ID and Names of all employees who have been employed more than 10 years.
                query = "SELECT \"EMPLOYEE ID\", NAME FROM EMPLOYEE WHERE \"YEARS OF SERVICE\" > 10";
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    System.out.println("EMPLOYEEID : " + rs.getString("EMPLOYEE ID") + ""
                            + " NAME : " + rs.getString("NAME"));
                }

                //3.4 Updating all employees in position janitor to sanitation engineer
                query = "UPDATE EMPLOYEE SET POSITION = 'Sanitation Engineer'"
                        + "WHERE POSITION = 'Janitor'";
                int result = stmt.executeUpdate(query);
                System.out.println("Number of records updated to position Sanitation Engineer:" + result);

                //3.5 Remove all employees from the database who make more than $130,000
                query = "DELETE FROM EMPLOYEE WHERE SALARY > 130000";
                result = stmt.executeUpdate(query);
                System.out.println("Number of records deleted with salary greater than 130k:" + result);

                //3.6 Report the number of employees left in the database
                query = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE";
                rs = stmt.executeQuery(query);
                if (rs.next()) {
                    System.out.println("Number of records in left Employee Table: " + rs.getInt("COUNT"));
                }
            }

        } catch (SQLException ex) {
            System.out.println("Exception occurred performing the database operation:" + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("General Exception" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Could not close DB connection");
            }
        }
    }
}
