package com.amruta.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.amruta.dao.AppDaoSupport;
import com.amruta.elasticsearch.ESConstants;
import com.amruta.elasticsearch.ESUtils;
import com.amruta.elasticsearch.ElasticsearchTransportClient;
import com.amruta.repository.EmployeeData;;


public class EmployeeDao extends AppDaoSupport {
	protected JdbcTemplate jdbcTemplate;
	protected ESUtils ESUtils;
	
	public ESUtils getESUtils() {
		return ESUtils;
	}

	public void setESUtils(ESUtils ESUtils) {
		this.ESUtils = ESUtils;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
		
	
	//for specific user 
	public List<EmployeeData> getAllEmployees() {
		List<EmployeeData> employees = new ArrayList<EmployeeData>();
		try {
			final String queryName = "get_all_employees";
			final String[] queryParams = new String[] {};
			final Object[] queryParamValues = new Object[] {};
			List<EmployeeData> responseEmployee = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, queryParams, queryParamValues);
			if(responseEmployee != null){
				employees = responseEmployee;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}
	
	public boolean addEmployeesToES(ElasticsearchTransportClient esInstance,final int empId, final String empName, final String empEmail, final String empRole)
	{
    	boolean indexAliveFlag = false;
    	String indexName = ESUtils.getIndexName();
    	System.out.println("Index name is: "+indexName);
    	
    	if(esInstance == null){
        	System.out.println("No Service");
        	return false;
        }
    	else{
    		try{
		    	if(esInstance.indexExists(indexName)){
		    		System.out.println("Index Exists");
		    		indexAliveFlag = true;
		    	}else{
		    		boolean indexCreateStatus = ESUtils.createEmployeeIndex(esInstance);
		    		if(indexCreateStatus){
		    			System.out.println("Index created successfully");
		    			indexAliveFlag = true;
		    		}else{
		    			System.out.println("Error while creating index");
		    			indexAliveFlag = false;
		    		}	
		    	}
		    	if(indexAliveFlag){
		    		String employeeToIndex = ESUtils.getEmployeeDocToIndex(empId,empName,empEmail,empRole);
					System.out.println("Employee :"+employeeToIndex);
			    	boolean docAddStaus = esInstance.addDocumentToIndex(indexName,ESConstants.EMPLOYEE_TYPE, employeeToIndex, "", "", "");
		    		if(docAddStaus){
		    			System.out.println("Employee added successfully to index");
		    			return true;
		    		}else{
		    			System.out.println("Error while adding employee to index");
		    			return false;
		    		}
		    	}
		    	return false;
	    	//esInstance.close();
    		}catch(Exception ex){
    			System.out.println("Error while adding employee to index");
    			return false;
    		}
    	}
	}
	
}
