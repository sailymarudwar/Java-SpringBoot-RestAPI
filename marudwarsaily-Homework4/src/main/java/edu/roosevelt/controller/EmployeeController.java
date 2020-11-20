/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.roosevelt.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import edu.roosevelt.Employee;
import edu.roosevelt.EmployeeRowMapper;

/**
 *
 * @author smarudwar
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController implements WebMvcConfigurer {

	@Autowired
	JdbcTemplate db;
	
	@Autowired
	public RequestMappingHandlerMapping requestMappingHandlerMapping;

	//Part 1.1 Get all employees (optional SID, optional POSITION)
	//This method handles all the 4 parts of 1.1
	@GetMapping({ "/employeesbySidAndPos" })
	public ResponseEntity<ArrayList<Employee>> getAllemployeesBySidAndPos(@RequestParam(name = "sid", required = false) String sid,
			@RequestParam(name = "pos", required = false) String pos) {
		try {

			String sql = "SELECT * FROM EMPLOYEES ";
			String appendStr = "";
			if (sid != null) {
				appendStr = " WHERE SID=" + sid.trim();
			}
			if (pos != null) {
				if ("".equals(appendStr)) {
					appendStr = " WHERE POSITION='" + pos.trim() + "'";
				} else {
					appendStr += "and POSITION ='" + pos.trim() + "'";
				}
			}
			sql += appendStr;
			List<Employee> roster = db.query(sql, new EmployeeRowMapper());

			if (roster.isEmpty()) {
				return new ResponseEntity("Employees Could not be found for SID:" + sid + " and Position:" + pos, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(roster, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity("Error in getting Employee By Supervisor Id and Position\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping({ "/employees" })
	public ResponseEntity<ArrayList<Employee>> getAllemployees(@RequestParam(name = "sid", required = false) String sid,
			@RequestParam(name = "pos", required = false) String pos) {
		try {

			String sql = "SELECT * FROM EMPLOYEES ";
			String appendStr = "";
			if (sid != null) {
				appendStr = " WHERE SID=" + sid.trim();
			}
			if (pos != null) {
				if ("".equals(appendStr)) {
					appendStr = " WHERE POSITION='" + pos.trim() + "'";
				} else {
					appendStr += "and POSITION ='" + pos.trim() + "'";
				}
			}
			sql += appendStr;
			List<Employee> roster = db.query(sql, new EmployeeRowMapper());

			if (roster.isEmpty()) {
				return new ResponseEntity("Employees Could not be found for SID:" + sid + " and Position:" + pos, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(roster, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity("Error in getting Employee By Supervisor Id and Position\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	//1.2 Get employee with given EID
	@GetMapping(value = { "/employeeByEid/{eid}" })
	public ResponseEntity<Employee> getEmployee(@PathVariable("eid") final int eid) {

		Employee s = null;
		try {

			// query for employee with the given SID
			s = db.queryForObject("SELECT * FROM EMPLOYEES WHERE EID=" + eid, new EmployeeRowMapper());
			if (s == null) {
				return new ResponseEntity("Employee with EID:" + eid + " could not be found", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(s, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity("Employee with EID:" + eid + " could not be found\n"+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	//1.3 Get all employees from given department
	@GetMapping("/employeesByDepartment/{department}")
	public ResponseEntity<Employee> getAllEmployeesByDepartment(@PathVariable("department") final String department) {
		try {

			List<Employee> s = db.query(
					"SELECT E.* FROM EMPLOYEES E LEFT JOIN SUPERVISORS S ON E.SID=S.SID WHERE S.DEPARTMENT='" + department.trim() + "'",
					new EmployeeRowMapper());

			if (s == null || s.size() == 0) {
				return new ResponseEntity("Employees could not be found for Department:" + department, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(s, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity("Error in getting Employee by Department\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//1.4 Get the number of employees given a supervisor ID
	@GetMapping({ "/numemployeesBySupervisor/{sid}" })
	public ResponseEntity<Integer> getEmployeeCountBySid(@PathVariable("sid") final int sid) {

		try {
			int employeeCount = db.queryForObject("SELECT count(EID) FROM EMPLOYEES WHERE SID=" + sid, Integer.class);
			System.out.println(employeeCount);
			if (employeeCount > 0)
				return new ResponseEntity(employeeCount, HttpStatus.OK);
			return new ResponseEntity("Employees Could not be found for Supervior with SID:" + sid, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity("Error in getting Employee by Supervisor\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//1.5 Get the employee names and EIDs for all employees with over 20 years of service
	@GetMapping({ "/employeesOver20YOS" })
	public List<Map<String, Object>> getEmployeesOver20YOS() {
		try {

			return db.queryForList("SELECT NAME,EID FROM EMPLOYEES WHERE YOS > 20");

		} catch (Exception e) {
			throw e;
		}
	}

	//2.1 Add an employee
	@PostMapping(value = "/createEmployee", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createEmployee(@RequestBody @Valid final Employee s) {
		try {
			
			String sql = "SELECT COUNT(*) FROM EMPLOYEES WHERE EID=" + s.getEID();
			int num = db.queryForObject(sql, Integer.class);

			if (num > 0) {
				return new ResponseEntity("Employee with EID:" + s.getEID() + " Already Exists", HttpStatus.CONFLICT);
			} else {
				sql = "INSERT INTO EMPLOYEES VALUES (" + s.getEID() + ",'" + s.getName() + "','" + s.getPosition() + "'," + s.getYOS() + ","
						+ s.getSalary() + "," + s.getSID() + ")";
				db.execute(sql);
				return new ResponseEntity(s, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity("Error in creatung Employee\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	//3.1 Update an employee
	@PutMapping(value = "/updateEmployee/{eid}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> updateEmployee(@RequestBody @Valid final Employee s,@PathVariable("eid") final int eid) {

		try {

			String sql = "SELECT COUNT(*) FROM EMPLOYEES WHERE EID=" + s.getEID();
			int num = db.queryForObject(sql, Integer.class);

			if (num == 0) {
				sql = "UPDATE EMPLOYEES SET EID=" + s.getEID() + ",NAME='" + s.getName() + "',POSITION='" + s.getPosition() + "',YOS=" + s.getYOS()
						+ ",SALARY=" + s.getSalary() + " WHERE EID=" + eid;

				db.update(sql);
				Employee updatedEmp = db.queryForObject("SELECT * FROM EMPLOYEES WHERE EID=" + s.getEID(), new EmployeeRowMapper());
				return new ResponseEntity(updatedEmp, HttpStatus.OK);

			} else {
				return new ResponseEntity("Employee with EID " + s.getEID() + " does not exist and hence it cannot be updated", HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			return new ResponseEntity("Error in updating Employee\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 4.1 Delete employee
	@DeleteMapping({ "/deleteEmployee/{eid}" })
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("eid") final int eid) {
		try {

			String sql = "SELECT COUNT(*) FROM EMPLOYEES WHERE EID=" + eid;
			int num = db.queryForObject(sql, Integer.class);
			if (num > 0) {
				sql = "DELETE FROM EMPLOYEES WHERE EID=" + eid;
				db.update(sql);
				return new ResponseEntity("Employee with EID " + eid + " deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity("Employee with EID " + eid + " does not exist", HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity("Error in deleting Employee\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Graduate students only - Create a summary of the salaries grouped by department
	@GetMapping({ "/employeeSalarySummary" })
	public List<Map<String, Object>> getSalarySummary() {
		try {

			return db.queryForList("SELECT DEPARTMENT AS DEPARTMENT_NAME,SUM(SALARY) AS SALARY_SUM," + "COUNT(SALARY) AS SALARY_COUNT, "
					+ "AVG(SALARY) AS SALARY_AVERAGE \n" + "FROM EMPLOYEES E JOIN SUPERVISORS S\n" + "ON (E.SID=S.SID)\n" + "GROUP BY S.DEPARTMENT");

		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping("/endpoints")
	public @ResponseBody
	Object showEndpointsAction() throws SQLException
	{
	        return requestMappingHandlerMapping.getHandlerMethods().keySet().stream().map(t ->
	               (t.getMethodsCondition().getMethods().size() == 0 ? "GET" : t.getMethodsCondition().getMethods().toArray()[0]) + " " +                    
	               t.getPatternsCondition().getPatterns().toArray()[0]
	        ).toArray();
	 }
}
