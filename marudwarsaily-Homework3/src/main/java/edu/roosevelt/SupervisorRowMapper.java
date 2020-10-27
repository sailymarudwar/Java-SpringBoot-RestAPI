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
public class SupervisorRowMapper implements RowMapper<Supervisor>{

    @Override
    public Supervisor mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Supervisor s = new Supervisor();
        s.setSID(rs.getInt("SID"));
        s.setName(rs.getString("NAME"));
        s.setDepartment(rs.getString("DEPARTMENT"));
        
        return s;
    }
    
}
