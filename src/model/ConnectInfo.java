package model;

public class ConnectInfo {
	
	private String host;
	private int port;
	private int timeout;
	
	
	public ConnectInfo(){
	}
	
	public ConnectInfo(String host, int port){
		this.host = host;
		this.port = port;
	}
	public void setHostAndPort(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	public String getHost(){
		return this.host;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPort(){
		return this.port;
	}
	
	public void setTimeout(int timeout){
		this.timeout = timeout;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
}
