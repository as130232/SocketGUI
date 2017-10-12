package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import command.BaseMajorCommand;
import command.Module;
import model.ConnectInfo;
import operatCmd.OperatCmd;

public abstract class ClientBase {
	
	//operational Command
	public OperatCmd operatCmd;
	
//	public Socket connect(String host, int port) throws Exception;
//	public void disConnect(Socket socket) throws Exception;
	
	/**
	 * 開啟連線
	 * @author charles.chen
	 * @date 2017年1月11日 下午3:22:58
	 * @return Socket
	 */
	public Socket connect(ConnectInfo connectInfo) throws Exception {
		String host = null;
		int port = 0;
		try{
			host = connectInfo.getHost();
			port = connectInfo.getPort();
		}
		catch(NullPointerException e){
		}
		
		Socket socket = null;
		try {
			socket = new Socket( host, port);
		} catch (ConnectException e) {
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
	
	/**
	 * 關閉連線
	 * @author charles.chen
	 * @para socket
	 * @date 2017年1月11日 下午3:22:58
	 */
	public void disConnect(Socket socket) throws Exception {
		try{
			if(socket != null){
				socket.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 建立連線、傳送指令、關閉連線，並接受機械手臂回傳結果
	 * @author charles.chen
	 * @date 2017年1月11日 下午4:09:49
	 * @param command int
	 * @param sub int
	 * @return int 機械手回傳指令
	 */
	public int send(Module source, Module target, int sub, ConnectInfo connectInfo)throws Exception{
		Socket socket = connect(connectInfo);
		int command = getCommandLine(source, target);
		int result = checkAndSendCommand(socket, command, sub);
		disConnect(socket);
		return result;
	}
	
	/**
	 * 不根據source、target 直接下達指令，檢查若有該指令，建立連線、傳送指令、關閉連線，並接受機械手臂回傳結果
	 * @author charles.chen
	 * @date 2017年1月11日 下午4:09:49
	 * @param command int
	 * @param sub int
	 * @return int 機械手回傳指令
	 */
	public int send(int command, int sub, ConnectInfo connectInfo)throws Exception{
		int result = -1;
		Boolean isCommandLineExist = isCommandLineExist(command);
		if(isCommandLineExist){
			Socket socket = connect(connectInfo);
			result = checkAndSendCommand(socket, command, sub);
			disConnect(socket);
		}
		return result;
	}
	
	/**
	 * 根據各家廠商實作各自 檢測指令合格 後 傳送指令
	 * @author charles.chen
	 * @date 2017年1月11日 下午4:09:49
	 * @param socket Socket
	 * @param command int
	 * @param sub int
	 * @return int 機械手回傳指令
	 */
	public int checkAndSendCommand(Socket socket, int command, int sub) throws Exception {
		int convertInt = -1;
		
		if(socket == null){
			System.out.println("Socket is not connected.");
			return convertInt;
		}
		
		boolean isQualified = operatCmd.isQualified(command, sub);
		System.out.println("is Comand Qualified:" + isQualified);
		
		if(isQualified){
			try{
				DataInputStream input = null;
				DataOutputStream output = null;
				try{
					//set timeout
					input = new DataInputStream(socket.getInputStream());
					output = new DataOutputStream(socket.getOutputStream());
					//commandInt	
					byte[] convertCommand = operatCmd.convertCommand(command, sub);
					output.write(convertCommand);
					
					System.out.println("Send command success!");
					//if send fail will be repeat
					//while(input.available() == 0)
					//output.write(ConvertCommand(command, sub));
					{
						//if send success will be return 1
						int size = operatCmd.getResultSizeByCommand(command);
						byte[] result = new byte[size];
						result[0] = (byte) 0xFF;
						input.readFully(result, 0, result.length);
						convertInt = (result[0]&0xFF) ;
						System.out.println("convertInt: " + convertInt + " , Recv success!");
					}
				
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					try{
						if(input != null){
							System.out.println("Client Input Shutdown.");
							input.close();
						}
						if(output != null){
							System.out.println("Client Output Shutdown.");
							output.close();
						}
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}catch(NullPointerException e) {
				System.out.println("Socket is not connected.");
			}catch(SocketException e){
				System.out.println("Send Message fail! Socket is closed.");
			}finally{
				if(socket != null){
					System.out.println("Client Socket Close.");
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			//unQualified
			System.out.println("Input is unQualified.Please re-enter.");
			disConnect(socket);
		}
		
		return convertInt;
	}
	
	/**
	 * 機械手回傳指令對應字串狀態名稱
	 * @author charles.chen
	 * @date 2017年1月11日 下午4:09:49
	 * @param command int 
	 * @param convertInt int 機械手回傳指令
	 * @return String 機械手回傳指令名稱
	 */
	public String getRevcResult(int command, int convertInt) throws Exception {
		return operatCmd.getRevcResult(command, convertInt);
	}
	
	/**
	 * 檢查指令庫裡是否有該指令
	 * @author charles.chen
	 * @date 2017年1月19日 下午5:16:53
	 * @param command int
	 * @param param2 type 描述
	 * @return Boolean
	 */
	public abstract Boolean isCommandLineExist(int command) throws Exception;

	/**
	 * 根據來源、目標，取得對應指令
	 * @author charles.chen
	 * @date 2017年1月11日 下午4:09:49
	 * @param source Module
	 * @param target Module
	 * @return int 機械手指令
	 */
	public abstract int getCommandLine(Module source, Module target) throws Exception;
	
	/**
	 * 取得指令碼對應指令名稱
	 * @author charles.chen
	 * @date 2017年1月25日 上午10:23:33
	 * @param major int
	 * @return BaseMajorCommand 機械手指令
	 */
	public abstract BaseMajorCommand getCmdNameByMajor(int major) throws Exception;
	
	/**
	 * 取得指令碼對應指令名稱
	 * @author charles.chen
	 * @date 2017年1月25日 上午10:23:33
	 * @param major int
	 * @return BaseMajorCommand 機械手指令
	 */
	public abstract Integer getMajorByCmdName(String commandName) throws Exception;
	
	/**
	 * 取得該廠商所有實作 移動 指令
	 * @author charles.chen
	 * @date 2017年1月11日 下午4:09:49
	 * @return List<T> 機械手指令
	 */
	public abstract List<BaseMajorCommand> getMoveCommands() throws Exception;
	
	/**
	 * 取得該廠商所有實作 其他 指令
	 * @author charles.chen
	 * @date 2017年1月11日 下午4:09:49
	 * @return List<T> 機械手指令
	 */
	public abstract List<BaseMajorCommand> getOtherCommands() throws Exception;
}
