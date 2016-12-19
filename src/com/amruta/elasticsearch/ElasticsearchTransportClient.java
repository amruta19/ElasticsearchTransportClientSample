package com.amruta.elasticsearch;

import java.net.InetSocketAddress;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;


public class ElasticsearchTransportClient  implements ESConstants{
	private static ElasticsearchTransportClient INSTANCE; 
	private static TransportClient client; 

 
	private ElasticsearchTransportClient() { 
		try{
			TransportAddress transportAddress = new InetSocketTransportAddress(new InetSocketAddress(ESConstants.HOST, ESConstants.TRANSPORT_SOCKET));
			Settings settings = Settings.builder().put("cluster.name", ESConstants.CLUSTER_NAME).build();
			client = TransportClient.builder().settings(settings).build().addTransportAddress(transportAddress);
		}catch(Exception ex){
			ex.printStackTrace();
			client = null;
		}
	} 
  
	public static ElasticsearchTransportClient getInstance() { 
		try{
				INSTANCE = new ElasticsearchTransportClient();
		}catch(Exception e){
				e.printStackTrace();
				INSTANCE = null;
		}	
		
		if(client.connectedNodes().isEmpty()){
			System.out.println("Please check Elasticsearch status");
			INSTANCE = null;
		}
		return INSTANCE; 
	} 
  
	public void close() { 
		if (client != null) { 
			client.close(); 
		} 
	} 
  
	public TransportClient getClient(){ 
		return this.client; 
	} 
	
	public boolean createIndex(String indexName,int noOfShards,int noOfReplicas,Map<String,String> mapping){		
		try{
			CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
			createIndexRequestBuilder.setSettings(Settings.builder().put(ESConstants.NO_OF_SHARDS, noOfShards).put(ESConstants.NO_OF_REPLICAS, noOfReplicas));
			if(mapping.size()>0 && mapping!=null){
				for (Map.Entry<String, String> entry : mapping.entrySet()){
					createIndexRequestBuilder.addMapping(entry.getKey(),entry.getValue());
				}
			}
			createIndexRequestBuilder.execute().actionGet();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error occurred while creating Index !!!");
			return false;
		}
		return true;
	}
	
	public boolean addDocumentToIndex(String indexName,String indexType,String dataToIndex,String parent,String routing,String id){
		//Add document to index
		if(indexType!=null && !indexType.isEmpty()){
			IndexRequest indexRequest = new IndexRequest(indexName,indexType);
			indexRequest.source(dataToIndex);
			if(id!=null && !id.isEmpty()){
				indexRequest.id(id);
			}
			if(parent!=null && !parent.isEmpty()){
				indexRequest.parent(parent);
			}
			if(routing!=null && !routing.isEmpty()){
				indexRequest.routing(routing);
			}
			System.out.println(indexRequest.toString());
			try{
				IndexResponse response = client.index(indexRequest).actionGet();
				String idFromResponse =response.getId();
				if(!idFromResponse.isEmpty() && idFromResponse!=null){
					return true;
				}else{
					return false;
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
				return false;
			}
		}else{
			System.out.println("Index type not found");
			return false;
		}
	}
	
	public SearchResponse getDocumentFromIndexByQuery(String indexName,String indexType,String inputToRequest){
		if(indexType!=null && !indexType.isEmpty()){
			//Elasticsearch Response
			try{
				SearchResponse response = getClient().prepareSearch(indexName).setTypes(indexType).setSource(inputToRequest).execute().actionGet();
				return response;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}else{
			System.out.println("Index type not found");
			return null;
		}
	}
	
	public GetResponse getDocumentFromIndexById(String indexName,String indexType,String id){
		if(indexType!=null && !indexType.isEmpty()){
			//Elasticsearch Response
			try{
				GetResponse response = getClient().prepareGet(indexName, indexType, id).execute().actionGet();
				return response;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}else{
			System.out.println("Index type not found");
			return null;
		}
	}
	
	public boolean indexExists(String indexName){
		/*final ClusterHealthRequest clusterHealthRequest=new ClusterHealthRequest(indexName).timeout(TimeValue.timeValueSeconds(60)).waitForYellowStatus();
		final ClusterHealthResponse clusterHealth=client.admin().cluster().health(clusterHealthRequest).actionGet();
		if (clusterHealth.isTimedOut()) {
			System.out.println("ElasticSearch cluster health timed out");
		}
		else {
			System.out.println("ElasticSearch cluster health: Status " + clusterHealth.status().name() + "; "+ clusterHealth.getNumberOfNodes()+ " nodes; "+ clusterHealth.getActiveShards()+ " active shards.");
		}*/
		try{
			final IndicesExistsResponse exists = this.client.admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet();
			return exists.isExists();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
}
