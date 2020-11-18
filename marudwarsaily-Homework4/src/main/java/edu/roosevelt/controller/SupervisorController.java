package edu.roosevelt.controller;

import java.util.List;


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
import org.springframework.web.bind.annotation.RestController;

import edu.roosevelt.Employee;
import edu.roosevelt.EmployeeRowMapper;
import edu.roosevelt.Supervisor;
import edu.roosevelt.SupervisorRowMapper;
/**
*
* @author smarudwar
*/

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SupervisorController {

	@Autowired
	JdbcTemplate db;

	//1.6 Get all supervisors (optional SID)
	@GetMapping(value = { "/supervisors" })
	public ResponseEntity<Supervisor> getAllSupervisors(@PathVariable(name = "sid", required = false) final String sid) {
		try {
			String sql = "SELECT * FROM SUPERVISORS";
			String appendStr = "";
			if (sid != null) {
				appendStr = " WHERE SID=" + sid.trim();
			}
			sql += appendStr;
			List<Supervisor> s = db.query(sql, new SupervisorRowMapper());
			if (s.isEmpty()) {
				return new ResponseEntity("Supervisor Could not be found with SID:" + sid, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(s, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity("Error in getting Supervisor\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//1.2 Get Supervisor with given EID
		@GetMapping(value = { "/supervisorBysid/{sid}" })
		public ResponseEntity<Supervisor> getSupervisor(@PathVariable("sid") final int sid) {

			Supervisor s = null;
			try {

				// query for Supervisor with the given SID
				s = db.queryForObject("SELECT * FROM SUPERVISORS WHERE SID=" + sid, new SupervisorRowMapper());
				if (s == null) {
					return new ResponseEntity("Supervisor with SID:" + sid + " could not be found", HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity(s, HttpStatus.OK);
				}

			} catch (Exception e) {
				return new ResponseEntity("Supervisor with SID:" + sid + " could not be found\n"+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

	//GRADUATE STUDENTS ONLY Create Supervisor (Post endpoint)
	@PostMapping(value = "/createSupervisor", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supervisor> createSupervisor(@RequestBody final Supervisor s) {
		try {
			String sql = "SELECT COUNT(*) FROM SUPERVISORS WHERE SID=" + s.getSID();
			int num = db.queryForObject(sql, Integer.class);

			if (num > 0) {
				return new ResponseEntity("Supervisor with SID:" + s.getSID() + " Already Exists", HttpStatus.CONFLICT);
			} else {
				sql = "INSERT INTO SUPERVISORS VALUES (" + s.getSID() + ",'" + s.getName() + "','" + s.getDepartment() + "')";
				db.execute(sql);
				return new ResponseEntity(s, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity("Error in creating Supervisor\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//GRADUATE STUDENTS ONLY Update Supervisor (Put endpoint)
	@PutMapping(value = "/updateSupervisor/{sid}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supervisor> updateSupervisor(@RequestBody final Supervisor s,@PathVariable("sid") final int sid) {
		try {
			String sql = "SELECT COUNT(*) FROM SUPERVISORS WHERE SID=" + sid;
			int num = db.queryForObject(sql, Integer.class);

			if (num > 0) {

				sql = "UPDATE SUPERVISORS SET SID=" + s.getSID() + ",NAME='" + s.getName() + "',DEPARTMENT='" + s.getDepartment() + " 'WHERE SID="
						+ sid;
				db.update(sql);
				Supervisor updatedSup = db.queryForObject("SELECT * FROM SUPERVISORS WHERE SID=" + s.getSID(), new SupervisorRowMapper());
				return new ResponseEntity(updatedSup, HttpStatus.OK);

			} else {
				return new ResponseEntity("Suoervisor with SID " + sid + " does not exist and hence it cannot be updated",
						HttpStatus.NOT_FOUND);

			}
		} catch (Exception e) {
			return new ResponseEntity("Error in updating Supervisor\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//GRADUATE STUDENTS ONLY Delete Supervisor (Delete endpoint)
	@DeleteMapping({ "/deleteSupervisor/{sid}" })
	public ResponseEntity<Supervisor> deleteSupervisor(@PathVariable("sid") final int SID) {
		try {
			String sql = "SELECT COUNT(*) FROM SUPERVISORS WHERE SID=" + SID;
			int num = db.queryForObject(sql, Integer.class);
			if (num > 0) {
				//Had to delete the employees with the sid too, because of the foreign key constraint
				sql = "DELETE FROM EMPLOYEES WHERE SID=" + SID;
				db.update(sql);
				sql = "DELETE FROM SUPERVISORS WHERE SID=" + SID;
				db.update(sql);
				return new ResponseEntity("Supervisor with SID " + SID + "and all employees under this supervisor deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity("Supervisor with SID " + SID + " does not exist", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity("Error in deleting Supervisor\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
