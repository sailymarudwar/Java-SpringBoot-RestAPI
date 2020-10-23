/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mruth
 */
public class PopulateDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/marudwar", "marudwar", "marudwar");

        boolean succeeds = false;

        while (!succeeds) {
            String sql = "CREATE TABLE SUPERVISORS (";
            sql = sql + "SID INTEGER PRIMARY KEY,";
            sql = sql + "NAME VARCHAR(30),";
            sql = sql + "DEPARTMENT VARCHAR(30))";

            try {

                conn.createStatement().execute(sql);
                System.out.println("Created SUPERVISORS");
                succeeds = true;

            } catch (SQLException sqle) {
                conn.createStatement().execute("DROP TABLE EMPLOYEES");
                conn.createStatement().execute("DROP TABLE SUPERVISORS");

            }
        }
        
        succeeds = false;

        while (!succeeds) {
            String sql = "CREATE TABLE EMPLOYEES (";
            sql = sql + "EID INTEGER PRIMARY KEY,";
            sql = sql + "NAME VARCHAR(30),";
            sql = sql + "POSITION VARCHAR(30),";
            sql = sql + "YOS INTEGER,";
            sql = sql + "SALARY DOUBLE,";
            sql = sql + "SID INTEGER,";
            sql = sql + "FOREIGN KEY (SID) ";
            sql = sql + "REFERENCES SUPERVISORS (SID))";

            try {

                conn.createStatement().execute(sql);
                System.out.println("Created EMPLOYEES");
                succeeds = true;

            } catch (SQLException sqle) {

                conn.createStatement().execute("DROP TABLE EMPLOYEES");

            }
        }

        //after succeeds, we need to add data
        String[] fnames = {"Michael", "Beignet", "Rhubarb", "Carter", "Fonzy", "Belle"};
        String[] lnames = {"Brees", "Rodgers", "Brady", "Trubisky", "Ruth"};

        //E-1 through E-9
        String[] depts = {"Navy", "Army", "Marines", "Air Force", "Space Force", "Coast Guard"};

        Random random = new Random();

        //we need to remember the SIDS
        ArrayList<Integer> sids = new ArrayList();
        //while we still have supes to add
        while (sids.size() < 100) {
            //get a name
            String name = lnames[random.nextInt(lnames.length)] + ", "
                    + fnames[random.nextInt(fnames.length)];
            //get a dept
            String dept = depts[random.nextInt(depts.length)];
            //get a sid
            int sid = random.nextInt(89999) + 10000;
            //sql string
            String isql = "INSERT INTO SUPERVISORS VALUES (" + sid + ",'" + name + "','"
                    + dept + "')";

            try {
                conn.createStatement().execute(isql);
                sids.add(sid);
            } catch (SQLException sQLException) {

            }

        }
        System.out.println("Populated SUPERVISORS");
        int employees = 5000;
        //create a bunch of employees
        while (employees > 0) {


            //get a name
            String name = lnames[random.nextInt(lnames.length)] + ", "
                    + fnames[random.nextInt(fnames.length)];
            //get a dept
            String dept = depts[random.nextInt(depts.length)];
            //get a sid
            int sid = sids.get(random.nextInt(sids.size()));
            //sql string
            String position = "E-" + random.nextInt(10);
            //yos
            int yos = random.nextInt(50);
            //eid
            int eid = random.nextInt(89999) + 10000;
            //sal
            double salary = random.nextDouble()*100000;
            
            //sql string
            String isql = "INSERT INTO EMPLOYEES VALUES (" + eid + ",'" + name + "','"
                    + position + "', "+ yos + "," + salary + "," +  sid + ")";

            try {
                conn.createStatement().execute(isql);
                employees--;
            } catch (SQLException sQLException) {

            }

        }
        
        System.out.println("Populated EMPLOYEES");
        
        

    }

}
