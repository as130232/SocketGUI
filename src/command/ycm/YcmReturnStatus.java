package command.ycm;

public enum YcmReturnStatus {
	
	SUCCESS("Success"),
	UNABLE_TO_WRITE_COMMAND("Unable to write command"),
	INVALID_SUB("Invalid SUB"),
	UNABLE_TO_CONNECT("Unable to connect"),
	CONNECTED("Connected"),
	NOT_CONNECTED("Not Connected"),
	FAIL_TO_READ("Fail to read"),
	BUSY("Busy"),
	READY_FOR_COMMAND("Ready for Command"),
	READY("Ready"),
	RUNNING("Running"),
	FINISHED("Finished"),
	ALARM("Alarm"),
	NOT_IMPLEMENTED("Not Implemented");
	
	
	private String statusName;
	
	private YcmReturnStatus(String statusName){
		this.statusName = statusName;
	}
	
	public void setStausName(String statusName){
		this.statusName = statusName;
	}
	public String getStatusName(){
		return this.statusName;
	}
}
