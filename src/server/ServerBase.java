package server;

public interface ServerBase {
	
	public void bulidServer(int LISTEN_PORT) throws Exception;
	
	public void shutDownServer() throws Exception;
}
