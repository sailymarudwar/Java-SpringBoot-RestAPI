/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.roosevelt;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

/**
 *
 * @author smarudwar
 * EID INTEGER PRIMARY KEY,";
NAME VARCHAR(30),";
POSITION VARCHAR(30),";
YOS INTEGER,";
SALARY DOUBLE,";
SID INTEGER,";
FOREIGN KEY (SID) ";
REFERENCES SUPERVISORS (SID))"

 */
public class Employee implements Serializable{
	
	@NotNull
	@Min(value=10000, message="Employee Must be 5 digit")  
    @Max(value=99999, message="Employee Must be 5 digit")  
    private int EID;
	@NotNull
	@Size(min=2, max=30)
    private String name;
    private String position;
    private int YOS;  
    private double salary;
    private int SID;

	public int getEID() {
		return EID;
	}

	public void setEID(int eID) {
		EID = eID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getYOS() {
		return YOS;
	}

	public void setYOS(int yOS) {
		YOS = yOS;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(EID, SID, YOS, name, position, salary);
	}
   
	 @Override
		public String toString() {
			return "Employee [EID=" + EID + ", name=" + name + ", position=" + position + ", YOS=" + YOS + ", salary="
					+ salary + ", SID=" + SID + "]";
		}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return EID == other.EID && SID == other.SID && YOS == other.YOS && Objects.equals(name, other.name)
				&& Objects.equals(position, other.position)
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary);
	}
    
}
