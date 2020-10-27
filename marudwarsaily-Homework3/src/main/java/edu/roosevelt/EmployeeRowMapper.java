/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.roosevelt;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author smarudwar
 */
public class EmployeeRowMapper implements RowMapper<Employee>{

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee s = new Employee();
        s.setEID(rs.getInt("EID"));
        s.setSID(rs.getInt("SID"));
        s.setName(rs.getString("NAME"));
        s.setPosition(rs.getString("POSITION"));
        s.setYOS(rs.getInt("YOS"));
        s.setSalary(rs.getDouble("SALARY"));
        return s;
    }
    
}
