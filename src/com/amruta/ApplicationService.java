package com.amruta;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.amruta.dao.EmployeeDao;
import com.amruta.elasticsearch.ElasticsearchTransportClient;
import com.amruta.repository.EmployeeData;

public class ApplicationService{
	
	public int addAllEmployeesToES(){
		ApplicationContext context = new ClassPathXmlApplicationContext("com/amruta/resources/applicationContext.xml");
	    EmployeeDao employeeDao =(EmployeeDao)context.getBean("employeeDao");
	   	    
	    List<EmployeeData> employees = employeeDao.getAllEmployees();
	   
	    ElasticsearchTransportClient esInstance = ElasticsearchTransportClient.getInstance();
	    for(int i=0;i<employees.size();i++){
	    	EmployeeData empData = employees.get(i);
	    	int empId = empData.getEmpId();
	    	String empName = empData.getEmpName();
	    	String empEmail = empData.getEmpEmail();
	    	String empRole = empData.getEmpRole();;
	    	boolean status = employeeDao.addEmployeesToES(esInstance, empId, empName, empEmail, empRole);
	    	if(!status){
	    		System.out.println("Something went wrong");
	    		return 0;
	    	}
	    	long endTime = System.currentTimeMillis();
	    }
	    esInstance.close();
	    return 1;
	}
}