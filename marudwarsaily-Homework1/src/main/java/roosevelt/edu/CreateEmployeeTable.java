/*
CST 467 - Assignment 1
Fall 2020
Part 1
*/
package roosevelt.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saily
 */
public class CreateEmployeeTable {

    public static void main(String args[]) {
        String driverName = "org.apache.derby.jdbc.ClientDriver";
        Connection conn = null;
        try {
            //1.1 Connection to the database
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
                //1.2 Dropping the table in try-catch block  
                stmt = conn.createStatement();
                query = "DROP TABLE EMPLOYEE";
                stmt.execute(query);
                System.out.println("Table EMPLOYEE dropped successfully");
            }
        } catch (SQLException ex) {
            System.out.println("Sql Exception occurred in dropping the EMPLOYEE table:" + ex.getMessage());
        }

        try {
            if (conn != null) {
                //1.3 Table creation in try-catch block 
                //1.3.a. All the table fields created as per the given datatypes
                query = "CREATE TABLE EMPLOYEE("
                        + " \"EMPLOYEE ID\" VARCHAR(20),"
                        + " NAME VARCHAR(64),"
                        + " \"YEARS OF SERVICE\" INT,"
                        + " POSITION VARCHAR(64),"
                        + " SALARY DOUBLE, "
                        + " PRIMARY KEY (\"EMPLOYEE ID\"))"; //1.3.b Employeeid made the primary key
                stmt = conn.createStatement();
                stmt.execute(query);
                System.out.println("Table EMPLOYEE Created successfully");
            }
        } catch (SQLException ex) {
            System.out.println("Sql Exception occurred in creating the EMPLOYEE table:" + ex.getMessage());
        }
        
        finally {
             try {
                 //Closing connection irrespective of anything
                 if(conn != null) {
                   conn.close(); 
                 }
                } catch (SQLException ex) {
                    System.out.println("Could not close DB connection");
                }
        }
    }
}
