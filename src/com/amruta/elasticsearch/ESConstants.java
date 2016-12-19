package com.amruta.elasticsearch;

public interface ESConstants {
	
	public final String HOST = "127.0.0.1";
	public final int TRANSPORT_SOCKET = 9300;
	public final String CLUSTER_NAME = "elasticsearch";
	
	public final String INDEX_NAME = "employee_details";
	
	public final String EMPLOYEE_TYPE = "employee";

	public final String EMPLOYEE_MAPPING  = "{" + 
			"    \"employee\":{" + 
			"      \"properties\": {" + 
			"        \"emp_id\": {\"type\": \"integer\"}," + 
			"        \"emp_name\": { \"type\": \"string\", \"index\": \"not_analyzed\" }," + 
			"        \"emp_email\": { \"type\": \"string\", \"index\": \"not_analyzed\" }," + 
			"        \"emp_role\": {\"type\": \"string\",\"index\": \"not_analyzed\"}" + 
			"      }" + 
			"    }" + 
			"}";
	
	public final String NO_OF_SHARDS = "number_of_shards";
	public final String NO_OF_REPLICAS = "number_of_replicas"; 
}