/*
CST 467 - Assignment 1
Fall 2020
Part 2
*/
package roosevelt.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 *
 * @author Saily
 * 
 */
public class InitEmployeeTable {

    public static void main(String args[]) {
        String driverName = "org.apache.derby.jdbc.ClientDriver";
        Connection conn = null;
        Statement stmt;
        Random random = new Random();  //instance of random class

        int minSalary = 50000;
        int maxSalary = 150000;
        //FirstName array
        String[] employeeFNames = {"Monica", "Chandler", "Ross", "Rachel", "Pheobe", "Joey", "Sheldon", "Leonard", "Amy", "Penny"}; 
        //LastName array
        String[] employeeLNames = {"Geller", "Bing", "Willick", "Green", "Buffay", "Tribbiani", "Cooper", "Hofstadter", "Farrah", "Smith"}; 
        //Array for position randomization
        String[] employeePositions = {"Janitor", "Trainee", "Programmer", "Associate", "Director", "Senior Director", "Vice President", "Data Analyst", "Data Engineer", "Data Scientist"};
        String employeeId;
        String empName;
        String position;
        int yearsOfService;
        double salary;
        String query;

        try {
            //2.1 Connecting to the database 
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/marudwar", "marudwar", "marudwar");
        } catch (InstantiationException ex) {
            System.out.println("Could not Instantiate Derby:" + ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.out.println("Could not Access Derby Database:" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Derby SQL Exception Occured:" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Error in Referencing Derby Driver:" + ex.getMessage());
        } 
        try {
            if (conn != null) {
                for (int i = 1; i <= 5000; i++) {
                    //2.2 preparing data to be inserted
                    //2.2.a Randomizing EmpID
                    employeeId = String.valueOf(100000000 + random.nextInt(899999999)); 
                    //2.2.b Randomizing name from an array of full names
                    empName = employeeFNames[random.nextInt(10)] + " " + employeeLNames[random.nextInt(10)];
                    //2.2.c Randomizing Years of Service
                    yearsOfService = random.nextInt(26);
                    //2.2.d Randomizing position
                    position = employeePositions[random.nextInt(10)]; 
                    //2.2.e Randomizing Salary
                    salary = random.nextInt((maxSalary - minSalary) + 1) + minSalary; 
                    //Inserting the records
                    query = "INSERT INTO EMPLOYEE("
                            + "\"EMPLOYEE ID\", NAME, \"YEARS OF SERVICE\", POSITION, SALARY) "
                            + "VALUES ('" + employeeId + "','" + empName + "',"
                            + yearsOfService + ",'" + position + "'," + salary + ")";

                    stmt = conn.createStatement();
                    //System.out.println("-----------------------------------"+query);
                    stmt.execute(query);
                }
            }
            System.out.println("5000 Employee records inserted to DB");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error occurred in inserting records:" + ex.getMessage());
        }
        finally {
            
             try {
                 if(conn != null) {
                   conn.close(); 
                 }
                } catch (SQLException ex) {
                    System.out.println("Could not close DB connection");
                }
        }

    }

}
