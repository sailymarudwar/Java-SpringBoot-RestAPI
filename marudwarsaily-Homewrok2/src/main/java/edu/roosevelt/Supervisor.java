package edu.roosevelt;

import java.io.Serializable;
import java.util.Objects;

public class Supervisor implements Serializable{
	private int SID;
    private String name;
    private String department;
	/**
	 * @return the sID
	 */
	public int getSID() {
		return SID;
	}
	/**
	 * @param sID the sID to set
	 */
	public void setSID(int sID) {
		SID = sID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public int hashCode() {
		return Objects.hash(SID, department, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supervisor other = (Supervisor) obj;
		return SID == other.SID && Objects.equals(department, other.department) && Objects.equals(name, other.name);
	}
	@Override
	public String toString() {
		return "Supervisor [SID=" + SID + ", name=" + name + ", department=" + department + "]";
	}   

}
