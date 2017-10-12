package command.ycm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import command.BaseMajorCommand;

public enum YcmMajorCommand implements BaseMajorCommand{
	//OP1 -> MC1 
	//OP2 -> MC2
	//OP3 -> CMM
	UNQUALIFIED(-1),
	
	//moveCommand
	NOT_A_COMMAND(0),
	LOADING_TO_MAG(1),
	MAG_TO_LOADING(2),
	MAG_TO_MC1(3),
	MC1_TO_MAG(4),
	MAG_TO_MC2(5),
	MC2_TO_MAG(6),
	MC1_TO_LOADING(7),
	MC2_TO_LOADING(8),
	CMM_TO_LOADING(9),
	MAG_TO_CMM(10),
	CMM_TO_MAG(11),
	
	//not implemented
	MC1_CYCLE_START(12),
	MC2_CYCLE_START(13),
	CMM_CYCLE_START(14),
	
	//not in sop
	MC1_TO_CMM(15),
	MC2_TO_CMM(16),
	LOADING_TO_MC1(17),
	LOADING_TO_MC2(18),
	LOADING_TO_CMM(19),
	
	
	//robot status(Read Only)
	ROBOT_CONNECT_STATUS(20),
	RESUME_ROBOT(21),
	RAUSE_ROBOT(22),
	RESET_ROBOT(23),
	CLEAR_ALARM(24),
	ROBOT_MACRO_STATUS(25),
	
	
	READ_ALARM_CODE(208),
	READ_STATUS_CODE(209),
	READ_COMMAND_RETURN(210),
	READ_CURRENT_COMMAND(211),
	SAFE_TO_WRITE(212);
	
	private int major;
	
	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
		
	}

	private YcmMajorCommand(int major){
		this.major = major;
	}
	
	
	private static Map<Integer, YcmMajorCommand> allCommandMap = new HashMap<>();
	
	private static List<BaseMajorCommand> moveCommandArray = new ArrayList<>();
	private static List<BaseMajorCommand> otherCommandArray = new ArrayList<>();
	
	//init
	static{
		for(YcmMajorCommand ycmMajorCommand : YcmMajorCommand.values()){
			Integer major = ycmMajorCommand.getMajor();
			allCommandMap.put(major, ycmMajorCommand);
		};
		
		//put moveCommand in the Array
		
		int moveCommandSize = YcmMajorCommand.LOADING_TO_CMM.getMajor() + 1;
		
		for(int i = 0; i < moveCommandSize; i++){
			//allMap.get()取得的key 而非index
			YcmMajorCommand moveCommand = allCommandMap.get(i);
			moveCommandArray.add(moveCommand);
		}
		
		
		//more than or equal to LOADING_TO_CMM Command's major all is otherCmd.
		for(YcmMajorCommand ycmMajorCommand : YcmMajorCommand.values()){
			if(ycmMajorCommand.getMajor() >= YcmMajorCommand.ROBOT_CONNECT_STATUS.getMajor()){
				otherCommandArray.add(ycmMajorCommand);
			}
		};
		
	}
	
	public static BaseMajorCommand getCmdNameByMajor(int major){
		return allCommandMap.get(major);
	}
	
	public static List<BaseMajorCommand> getMoveCommandArray(){
		return moveCommandArray;
	}
	
	public static List<BaseMajorCommand> getOtherCommandArray(){
		return otherCommandArray;
	}

	
}
