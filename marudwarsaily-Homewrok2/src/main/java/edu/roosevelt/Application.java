package edu.roosevelt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	JdbcTemplate db;

	@GetMapping({ "/getAllemployees" })
	public ResponseEntity<ArrayList<Employee>> getAllemployees(@RequestParam(name = "SID",required = false) String sid,
			@RequestParam(name = "pos",required = false) String pos) {

		String sql = "SELECT * FROM EMPLOYEES";
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
		System.out.println("Number of Results for GetEmployees:"+roster.size());

		
		if (roster.isEmpty()) {
			return new ResponseEntity(roster, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity(roster, HttpStatus.OK);
		}

	}

	//@GetMapping({ "/employee/{eid}", "/{EID}" })
	@GetMapping(value={"/getEmployee/{EID}","/{EID}"})
	public ResponseEntity<Employee> getEmployee(@PathVariable("EID") final int eid) {

		Employee s = null;
		try {
			// query for employee with the given SID
			s = db.queryForObject("SELECT * FROM EMPLOYEES WHERE EID=" + eid, new EmployeeRowMapper());
			System.out.println(s);
			
			return new ResponseEntity(s, HttpStatus.OK);
			// does employee exist?
		} catch (EmptyResultDataAccessException empty) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/getAllEmployeesByDepartment/{department}")
	public ResponseEntity<Employee> getAllEmployeesByDepartment(@PathVariable("department") final String department) {

		List<Employee> s = db.query("SELECT E.* FROM EMPLOYEES E LEFT JOIN SUPERVISORS S ON E.SID=S.SID WHERE S.DEPARTMENT='" + department.trim()+"'", new EmployeeRowMapper());

		if (s == null || s.size() == 0) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity(s, HttpStatus.OK);
		}

	}

	@GetMapping({ "/getEmployeeCountBySid/{SID}"})
	public ResponseEntity<Integer> getEmployeeCountBySid(@PathVariable("SID") final int sid) {

		try {			
			int employeeCount = db.queryForObject("SELECT count(EID) FROM EMPLOYEES WHERE SID=" + sid, Integer.class);
			System.out.println(employeeCount);
			if(employeeCount>0)
				return new ResponseEntity(employeeCount, HttpStatus.OK);
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} catch (EmptyResultDataAccessException empty) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping({ "/getEmployeesOver20YOS"})
	public MappingJacksonValue getEmployeesOver20YOS() {
		
		List<Employee> s = db.query("SELECT * FROM EMPLOYEES WHERE YOS > 20", new EmployeeRowMapper());

		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("eid", "name");

	    FilterProvider filters = new SimpleFilterProvider().addFilter("EIDNAMEFILTER", filter);

	    MappingJacksonValue mapping = new MappingJacksonValue(s);

	    mapping.setFilters(filters);
	    
		return mapping;

	}
	
	@GetMapping(value={"/getAllSupervisors/{SID}","/getAllSupervisors"})
	public ResponseEntity<Supervisor> getAllSupervisors(@PathVariable(name="SID", required = false) final String SID) {
		
		String sql = "SELECT * FROM SUPERVISORS";
		String appendStr = "";
		if (SID != null) {
			appendStr = " WHERE SID=" + SID.trim();
		}
		sql += appendStr;
		List<Supervisor> s = db.query(sql, new SupervisorRowMapper());
		if (s.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity(s, HttpStatus.OK);
		}

	}
	

	@PostMapping(value = "/createEmployee", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createEmployee(@RequestBody final Employee s) {
	        //create query str
	        String sql = "SELECT COUNT(*) FROM EMPLOYEES WHERE EID=" + s.getEID();
	        int num = db.queryForObject(sql, Integer.class);
	        
	        if (num > 0) {
	            return new ResponseEntity("Employee with EID:"+s.getEID()+" Already Exists", HttpStatus.CONFLICT);
	        } else {
	           sql = "INSERT INTO EMPLOYEES VALUES (" + s.getEID() + ",'" + s.getName() + "','"
	                    + s.getPosition() + "'," + s.getYOS() + "," + s.getSalary() + ","+s.getSID()+ ")"; 
	           db.execute(sql);
	           return new ResponseEntity(s, HttpStatus.OK);
	           
	        }
	 }
	
	 @PutMapping(value = "/updateEmployee", consumes = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Employee> updateEmployee(@RequestBody final Employee s) {
	        //create query str
	        String sql = "SELECT COUNT(*) FROM EMPLOYEES WHERE EID=" + s.getEID();
	        int num = db.queryForObject(sql, Integer.class);
	    
	        if (num > 0) {
	        	sql = "UPDATE EMPLOYEES SET EID=" + s.getEID() + ",NAME='" + s.getName()
          		 + "',POSITION='" + s.getPosition()
                +  "',YOS=" + s.getYOS()+ ",SALARY=" + s.getSalary()
                + " WHERE EID=" + s.getEID();
                 
          db.update(sql);
          Employee updatedEmp = db.queryForObject("SELECT * FROM EMPLOYEES WHERE EID=" + s.getEID(), new EmployeeRowMapper());
          return new ResponseEntity(updatedEmp, HttpStatus.OK);

	        } else {
	                 	
	            return new ResponseEntity("Employee with EID "+s.getEID()+" does not exist and hence it cannot be updated", HttpStatus.NOT_FOUND);
	           
	        }
	 }
	 
	 @DeleteMapping({"/deleteEmployee/{eid}"})
	 public ResponseEntity<Employee> deleteEmployee(@PathVariable("eid") final int eid) {
	        String sql = "SELECT COUNT(*) FROM EMPLOYEES WHERE EID=" + eid;
	        int num = db.queryForObject(sql, Integer.class);
	        if (num > 0) {
		           sql = "DELETE FROM EMPLOYEES WHERE EID="+eid;
		           db.update(sql);
		           return new ResponseEntity("Employee with EID "+eid+" deleted successfully", HttpStatus.OK);	           
	        } else {
	           return new ResponseEntity("Employee with EID "+eid+" does not exist", HttpStatus.NOT_FOUND);	           
	        }
	 }
	 
		@GetMapping({ "/getSalarySummary"})
		public List<Map<String, Object>> getSalarySummary() {
				
				
			return db.queryForList("SELECT DEPARTMENT as Department_Name,SUM(SALARY) AS Salary_Sum,"
														+ "COUNT(SALARY) AS Salary_Count, "
														+ "AVG(SALARY) AS Salary_Average\n" + 
														"FROM EMPLOYEES E JOIN SUPERVISORS S\n" + 
														"ON (E.SID=S.SID)\n" + 
														"GROUP BY S.DEPARTMENT");			
		}
		
	 
	 @PostMapping(value = "/createSupervisor", consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Supervisor> createSupervisor(@RequestBody final Supervisor s) {
		        //create query str
		        String sql = "SELECT COUNT(*) FROM SUPERVISORS WHERE SID=" + s.getSID();
		        int num = db.queryForObject(sql, Integer.class);
		        
		        if (num > 0) {
		            return new ResponseEntity("Supervisor with SID:"+s.getSID()+" Already Exists", HttpStatus.CONFLICT);
		        } else {
		           sql = "INSERT INTO SUPERVISORS VALUES (" + s.getSID() + ",'" + s.getName() + "','"
		                    + s.getDepartment() + "')"; 
		           db.execute(sql);
		           return new ResponseEntity(s, HttpStatus.OK);
		           
		        }
		 }
		
		 @PutMapping(value = "/updateSupervisor", consumes = MediaType.APPLICATION_JSON_VALUE)
		 public ResponseEntity<Supervisor> updateSupervisor(@RequestBody final Supervisor s) {
		        
		        String sql = "SELECT COUNT(*) FROM SUPERVISORS WHERE SID=" + s.getSID();
		        int num = db.queryForObject(sql, Integer.class);
		    
		        if (num > 0) {
		            
		            sql = "UPDATE SUPERVISORS SET SID=" + s.getSID() + ",NAME='" + s.getName()		           	
	                 +  "',DEPARTMENT='" + s.getDepartment()
	                 +" 'WHERE SID=" + s.getSID();	                  
	           db.update(sql);
	           Supervisor updatedSup = db.queryForObject("SELECT * FROM SUPERVISORS WHERE SID=" + s.getSID(), new SupervisorRowMapper());
	           return new ResponseEntity(updatedSup, HttpStatus.OK);
	           
		        } else {
		        	return new ResponseEntity("Suoervisor with SID "+s.getSID()+" does not exist and hence it cannot be updated", HttpStatus.NOT_FOUND);
		        	//return new ResponseEntity(s, HttpStatus.NOT_FOUND);		           
		          
		        }
		 }
		 
		 @DeleteMapping({"/deleteSupervisor/{sid}"})
		 public ResponseEntity<Supervisor> deleteSupervisor(@PathVariable("sid") final int SID) {
		        String sql = "SELECT COUNT(*) FROM SUPERVISORS WHERE SID=" + SID;
		        int num = db.queryForObject(sql, Integer.class);
		        if (num > 0) {
			           sql = "DELETE FROM SUPERVISORS WHERE SID="+SID;
			           db.update(sql);
			           return new ResponseEntity("Supervisor with SID "+SID+" deleted successfully", HttpStatus.OK);	           
		        } else {
		           return new ResponseEntity("Supervisor with SID "+SID+" does not exist", HttpStatus.NOT_FOUND);	           
		        }
		 }
}
