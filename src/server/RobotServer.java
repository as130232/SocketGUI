package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//old version
public class RobotServer {
	//flag 控制是否執行socket連線之旗標
	private static Boolean isConnect = false;
	private static ServerSocket serverSocket = null;
	//連線池，同時間可能會有多個socket連線請求
	private static ExecutorService threadExecutor = null;
	public RobotServer(){
		System.out.println("init...");
		System.out.println("isConnect:" + isConnect);
		System.out.println("serverSocket:" + serverSocket);
		System.out.println("threadExecutor:" + threadExecutor);
		isConnect = true;
		//緩存型池子通常用於執行一些生存期很短的異步型任務
		threadExecutor = Executors.newCachedThreadPool();
		//創建固定數目線程的線程池。
		//threadExecutor = Executors.newFixedThreadPool(10);
	}
	
	
	public static int ConvertInt(byte[] raw_data)
	{
		int ans = 0;
		
		ans = (raw_data[0]&0xFF) + (raw_data[1]&0xFF)*256;//raw_data[0]<<8;
		
		return ans;
	}
	
	//監聽請求
	public void listenRequest(int LISTEN_PORT){
		
		try{
			//傳入port，啟動Socket
			serverSocket = new ServerSocket(LISTEN_PORT);
			System.out.println("Server listening requests..");
		
			//當開啟連線，監聽是否有請求進來
			while(isConnect){
				System.out.println("waiting for client to connect..");
				//clientSocket 監聽Client端，若有連線取得Cleint端的Socket
				Socket clientSocket = null;
				try{
					//Server端利用accept來取得和Client端連線的Socket物件
					clientSocket = serverSocket.accept();
				}catch(SocketException e){
					System.err.println("clickSocket hard closing.");
				} 
				catch (IOException e) {
	                System.err.println("Error on accept socket!");
	            }
				//這裡利用連線池的概念，每接受到一個Socket就建立一個執行緒來處理，再將Socket(client端的連線傳入負責處理Client端的請求執行緒)
				//好處是可以一個Server同時處理多個Client
				if(isConnect){
					System.out.println("connect success!");
					threadExecutor.execute(new RequestThread(clientSocket));
				}
			}
			System.out.println("關閉連線後的 A 當前執行緒名稱: " + Thread.currentThread().getName());
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			//執行最後，若threadExecutor沒有關掉就shutdown
			if(threadExecutor != null){
				threadExecutor.shutdownNow();
			}
			if(serverSocket != null){
				try{
					serverSocket.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	//內部類別只供Server使用，負責處理Client端的Request執行緒
	private class RequestThread implements Runnable{

		private Socket clientSocket;
		
		public RequestThread(Socket clientSocket){
			this.clientSocket = clientSocket;
			System.out.printf("偵測到 %s 連線！\n",clientSocket.getRemoteSocketAddress());
		}
		
		@Override
		public void run() {
			System.out.println("B 連線池中執行緒名稱: " + Thread.currentThread().getName());
			DataInputStream input = null;
			DataOutputStream output = null;
			
			try{
				input = new DataInputStream(this.clientSocket.getInputStream());
				System.out.println("isInputAvailable:" + input.available());
				while(input.available() != 0){
					//Getting command
					int length = 2;
					System.out.println("length:" + length);
					if(length > 0){
						byte[] result = new byte[length];
						input.readFully(result, 0, result.length);
						int command = ConvertInt(result);
						if(command != 0xFFEE)
							System.out.println("Unknown Command.");
						else
							System.out.println("Command: " + String.format("0x%02X", command));
						System.out.println(Arrays.toString(result));
						System.out.println("-------------");
					}
					
					//Getting length
					{
						byte[] result = new byte[length];
						input.readFully(result, 0, result.length);
						length = ConvertInt(result);
						
					}
					
					//Getting content
					{
						byte[] result = new byte[length];
						input.readFully(result, 0, result.length);
						String content = new String(result, "ASCII");
						System.out.println("to ASCII : " + content );
						
						//將訊息傳送給MES
						sendContentToMes(content);
					}
				}
				
			}catch(EOFException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					if(input != null){
						System.out.println("Input closed.");
						input.close();
					}
					if(output != null){
						System.out.println("Output closed.");
						output.close();
					}
					if(clientSocket != null || !clientSocket.isClosed()){
						System.out.println("Socket closed.");
						clientSocket.close();
					}
					
//					System.out.println("isBound:" + clientSocket.isBound()); 
//					System.out.println("isClosed:" + clientSocket.isClosed()); 
//					System.out.println("isConnected:" + clientSocket.isConnected());
//					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					System.out.println("The End Time：" + format.format(new Date()));  	
					
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}

		private void sendContentToMes(String content) {

			
		}
		
	}
	
	
	public static void bulidSocketServer(final int LISTEN_PORT){
		//bulid server when no connection
		if(!isConnect){
			//建立SocketServer並掌管連線池的 thread，讓主thread可以往下繼續執行
			Thread bulidServerThread = new Thread(){
				public void run(){
					RobotServer robotServer = new RobotServer();
					robotServer.listenRequest(LISTEN_PORT);
				}
				
			};
			bulidServerThread.start();
			System.out.println("建立完，執行緒數量: " + Thread.activeCount());
			System.out.println("A 執行緒名稱: " + bulidServerThread.getName());
		}else{
			System.out.println("address already in use");
		}
	}
	
	public static void shutDownServer(){
		System.out.println("RobotServer#shutDownServer");
		isConnect = false;
		if(threadExecutor != null){
			//停止接受任何新的任務且等待已經提交的任務執行完成
			threadExecutor.shutdown();
			threadExecutor = null;
		}
		try {
			//必須先將指標isConnect > false，再把serverSocket close
			//因為多執行續關係，若先關serverSocket，isConnect可能還在ture，造成將有機率執行到threadExecutor.execute(new RequestThread(socket))而跳出nullPointerException;
			if(serverSocket != null){
				serverSocket.close();
				serverSocket = null;
			}	
			System.out.println("當前執行緒名稱: " + Thread.currentThread().getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean getIsConnect(){
		return isConnect;
	}
}
