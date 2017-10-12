package client;

import java.util.HashMap;

import client.impl.ClientDelta;
import client.impl.ClientYcm;
import command.com.Company;

public class ClientFactory {
	
	public HashMap<Company, ClientBase> clientTemplateMap = new HashMap<Company, ClientBase>();
	
	ClientBase clientDelta = new ClientDelta();
	ClientBase clientYcm = new ClientYcm();
	
	public ClientFactory(){
		clientTemplateMap.put(Company.DELTA, clientDelta);
		clientTemplateMap.put(Company.YCM, clientYcm);
	}
	
	public ClientBase getClientTemplate(Company company){
		return clientTemplateMap.get(company);
	}
	
	public ClientBase getClientTemplate(String company){
		return getClientTemplate(Company.valueOf(company.toUpperCase()));
	}
}
