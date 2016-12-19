package com.amruta.repository;

public class EmployeeData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int empId;
	private String empName;
	private String empEmail;
	private String empRole;
   
    /** default constructor */
    public EmployeeData() {
    }
    
    /** full constructor */
    public EmployeeData(final int empId,final String empName,final String empEmail,final String empRole){
		this.empId = empId;
		this.empName = empName;
		this.empEmail = empEmail;
		this.empRole = empRole;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
