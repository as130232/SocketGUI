package operatCmd;

public interface OperatCmd {
	
	/**
	 * 將指令包裝成byte
	 * @author charles.chen
	 * @date 2017年1月10日 上午9:46:55
	 * @param command int 指令
	 * @param sub int
	 * @return byte[]
	 */
	public byte[] convertCommand(int command, int sub) throws Exception;
	
	/**
	 * 取得 設定回傳結果的陣列大小
	 * @author charles.chen
	 * @date 2017年1月10日 上午9:46:55
	 * @param command Integer 指令
	 * @param convertInt int 回傳值
	 * @return int
	 */
	
	public int getResultSizeByCommand(int command) throws Exception;
	
	/**
	 * 檢測指令是否合格
	 * @author charles.chen
	 * @date 2017年1月10日 上午9:46:55
	 * @param command int 指令
	 * @param sub int index
	 * @return boolean
	 */
	public boolean isQualified(int command, int sub) throws Exception;
	
	/**
	 * 取得該指令回傳值之狀態
	 * @author charles.chen
	 * @date 2017年1月10日 上午9:46:55
	 * @param command int 指令
	 * @param convertInt int 回傳值
	 * @return String
	 */
	public String getRevcResult(int command, int convertInt) throws Exception;
	
	
}
