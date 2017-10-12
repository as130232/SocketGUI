package command.delta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import command.BaseMajorCommand;
public enum DeltaMajorCommand implements BaseMajorCommand{
	
	UNQUALIFIED(-1),
	
	//moveCommand
	NOT_A_COMMAND(0),
	LOADING_TO_MAG(1),
	MAG_TO_LOADING(2),
	MAG_TO_OP1(3),
	OP1_TO_MAG(4),
	MAG_TO_OP2(5),
	OP2_TO_MAG(6),
	OP1_TO_LOADING(7),
	OP2_TO_LOADING(8),
	
	

	AREAA_TO_AREAB(9),
	AREAB_TO_AREAA(10),
	AREAB_TO_AREAC(11),
	AREAC_TO_AREAB(12),
	AREAA_TO_AREAC(13),
	AREAC_TO_AREAA(14),
	
	
	//otherCommand
	ROBOT_CONNECT(224),
	ROBOT_DISCONNECT(225),
	ROBOT_CONNECTION_STATUS(226),
	ROBOT_PROGRAM_RUN(227),
	ROBOT_PROGRAM_PAUSE(228),
	ROBOT_PROGRAM_STOP(229),
	ROBOT_PROGRAM_RESET_NOT_IMP(230),

	READ_DO(231),
	READ_DI(232),
	
	HAS_ALARM(233),
	GET_ALARM_CODE(234),
	CLEAR_ALARM_STATE(235),
	GET_PROGRAM_STATUS(236),
	IS_SAFE_TO_WRITE(237),
	GET_CURRENT_RUNNING(238),
	READ_ROBOT_DO(239),
	READ_ROBOT_DI(240),
	READ_ROBOT_CURRENT_AREA(241);
	
	private int major;
	
	private DeltaMajorCommand(int major){
		this.major = major;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}
	
	private static Map<Integer, DeltaMajorCommand> allCommandMap = new HashMap<>();
//	private static DeltaMajorCommand[] moveCommandArray; 
//	private static DeltaMajorCommand[] otherCommandArray; 
	
	private static List<BaseMajorCommand> moveCommandArray = new ArrayList<>();
	private static List<BaseMajorCommand> otherCommandArray = new ArrayList<>();
	
	
	//初始化時，先將DeltaMajorCommand存進Map，不需每次呼叫都要重新loop
	static{
		
		for(DeltaMajorCommand majorCommand: DeltaMajorCommand.values()){
			int major = majorCommand.getMajor();
			//key > value
			allCommandMap.put(major, majorCommand);
		}
		
		//put the moveCommand
		int moveCommandArraySize = DeltaMajorCommand.AREAC_TO_AREAA.getMajor() + 1;
		for(int i  = 0 ; i < moveCommandArraySize; i++){
			//allMap.get()取得的key 而非index
			DeltaMajorCommand moveCommand = allCommandMap.get(i);
			moveCommandArray.add(moveCommand);
		}
		
		//get final Member
//		DeltaMajorCommand finalMember = DeltaMajorCommand.values()[DeltaMajorCommand.values().length-1];
		
		//put the otherCommand
//		int otherCommandArraySize = finalMember.getMajor() - DeltaMajorCommand.ROBOT_CONNECT.getMajor() + 1;
//		otherCommandArray = new DeltaMajorCommand[otherCommandArraySize];
//		int otherIndex = DeltaMajorCommand.ROBOT_CONNECT.getMajor();
//		for(int j  = 0 ; j < otherCommandArraySize; j++){
//			DeltaMajorCommand otherCommand = allCommandMap.get(otherIndex);
//			otherCommandArray[j] = otherCommand;
//			otherIndex = otherIndex + 1;
//		}
		
		//more than or equal to ROBOT_CONNECT Command's major all is otherCmd.
		for(DeltaMajorCommand deltaMajorCommand : DeltaMajorCommand.values()){
			if(deltaMajorCommand.getMajor() >= DeltaMajorCommand.ROBOT_CONNECT.getMajor()){
				otherCommandArray.add(deltaMajorCommand);
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
