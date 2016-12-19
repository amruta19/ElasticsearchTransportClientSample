package com.amruta.elasticsearch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class ESUtils {
	public String getIndexName(){
	  	return ESConstants.INDEX_NAME;
	}
		
	public String getEmployeeDocToIndex(int empId,String empName,String empEmail,String empRole){
		JSONObject employeeObject = new JSONObject();
		try {
			employeeObject.put("emp_id", empId);
			employeeObject.put("emp_name", empName);
			employeeObject.put("emp_email", empEmail);
			employeeObject.put("emp_role", empRole);
			return employeeObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
		
	public boolean createEmployeeIndex(ElasticsearchTransportClient esInstance){
    	Map<String,String> typeMapping = new HashMap<String, String>();
    	typeMapping.put(ESConstants.EMPLOYEE_TYPE, ESConstants.EMPLOYEE_MAPPING);
    	//no.of shards = 3 and no. of replicas = 1
    	try{
	    	if(!esInstance.createIndex(ESConstants.INDEX_NAME, 3, 1, typeMapping)){
	    		return false;
	    	}
	    	return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
}
