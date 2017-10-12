package command.delta;

public enum DeltaReturnStatus {
	
	SUCCESS("Success"),
	UNABLE_TO_WRITE_COMMAND("Unable to write command"),
	INVALID_SUB("Invalid SUB"),
	UNABLE_TO_CONNECT("Unable to connect"),
	FAIL("Fail"),
	CONNECTED("Connected"),
	NOT_CONNECTED("Not Connected"),
	READ_FAIL("Read fail"),
	DO_HIGH("DO-High"),
	DO_LOW("DO-Low"),
	DI_HIGH("DI-High"),
	DI_LOW("DI-Low"),
	HAS_ALARM("Has alarm"),
	NO_ALARM("No alarm"),
	
	CLOSE("Close"),
	RUNNING("Running"),
	BREAK_POINT("Break Point"),
	PAUSE("Pause"),
	READY("Ready"),
	NOT_READY("Not ready"),
	NOT_IMPLEMENTED("Not Implemented"),
	
	UNDEFINED("Undefined"),
	//area
	AREA_A("Area_A"),
	AREA_B("Area_B"),
	AREA_C("Area_C");
	
	private String statusName;
	
	private DeltaReturnStatus(String statusName){
		this.statusName = statusName;
	}
	
	public void setStausName(String statusName){
		this.statusName = statusName;
	}
	public String getStatusName(){
		return this.statusName;
	}
	
	public static DeltaReturnStatus convertByString(String statusName){
		return DeltaReturnStatus.valueOf(statusName);
	}
	
	public static DeltaReturnStatus getDeltaReturnStatusByStatusName(String statusName){
		for(DeltaReturnStatus deltaReturnStatus : DeltaReturnStatus.values()){
			if(statusName.equals(deltaReturnStatus.getStatusName())){
				return  deltaReturnStatus;
			}
		};
		return null;
	}
	
}
