package operatCmd.impl;

import command.delta.DeltaMajorCommand;
import command.delta.DeltaReturnStatus;
import operatCmd.OperatCmd;

public class OperatCmdForDelta implements OperatCmd{

	@Override
	public byte[] convertCommand(int command, int sub) throws Exception {
		byte[] ans = new byte [2];
		//verification
		if(command < 256 && command >=0 && sub < 256 && sub >=0)
		{		
			ans[0] = (byte)(command & 0xff);
			ans[1] = (byte)(sub & 0xff);
			//ans[1] = (byte)((command >> 8) & 0xff);
		}
		else
		{
			ans[0] = (byte)0xFE;
			ans[1] = (byte)0xFF;
		}
		return ans;
	}
	
	@Override
	public int getResultSizeByCommand(int command) throws Exception {
		int size = 1;
		DeltaMajorCommand deltaMajorCommand = (DeltaMajorCommand) DeltaMajorCommand.getCmdNameByMajor(command);
		switch(deltaMajorCommand){
		case GET_ALARM_CODE:
			size = 5;
			break;
		case GET_CURRENT_RUNNING:
			size = 3;
			break;
		}	
		return size;
	}
	
	@Override
	public boolean isQualified(int command, int sub) throws Exception {
		boolean isQualified = true;
		
		if(command < DeltaMajorCommand.NOT_A_COMMAND.getMajor() || command > 256){
			isQualified = false;
		}
		else if(command > DeltaMajorCommand.NOT_A_COMMAND.getMajor() && command <= DeltaMajorCommand.OP2_TO_MAG.getMajor()){
			if(sub < 1 || sub > 10){
				isQualified = false;
			}
		}//Read DO、Read DI
		else if(command == DeltaMajorCommand.READ_DO.getMajor() || command == DeltaMajorCommand.READ_DI.getMajor() 
				|| command == DeltaMajorCommand.READ_ROBOT_DO.getMajor() || command == DeltaMajorCommand.READ_ROBOT_DI.getMajor()){
			if(sub < 0 || sub > 47){
				isQualified = false;
			}
		}else{
			if(sub != 0){
				isQualified = false;
			}
		}
		return isQualified;
	}

	@Override
	public String getRevcResult(int command, int convertInt) throws Exception {
		String revcResult = "unknow";
		DeltaMajorCommand deltaMajorCommand = (DeltaMajorCommand) DeltaMajorCommand.getCmdNameByMajor(command);
		switch(deltaMajorCommand){
			case LOADING_TO_MAG:
			case MAG_TO_LOADING:
			case MAG_TO_OP1:	
			case OP1_TO_MAG:	
			case MAG_TO_OP2:	
			case OP2_TO_MAG:	
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.UNABLE_TO_WRITE_COMMAND.getStatusName();
				}else if(convertInt == -1 || convertInt == 255){
					revcResult = DeltaReturnStatus.INVALID_SUB.getStatusName();
				}
				break;
			case OP1_TO_LOADING:	
			case OP2_TO_LOADING:	
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.UNABLE_TO_WRITE_COMMAND.getStatusName();
				}else if(convertInt == -1 || convertInt == 255){
					revcResult = DeltaReturnStatus.FAIL.getStatusName();
				}
				break;
			case ROBOT_CONNECT:
			case ROBOT_DISCONNECT:
			case ROBOT_PROGRAM_RUN:
			case ROBOT_PROGRAM_PAUSE:
			case ROBOT_PROGRAM_STOP:
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.FAIL.getStatusName();
				}
				break;
			case ROBOT_CONNECTION_STATUS:
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.CONNECTED.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.NOT_CONNECTED.getStatusName();
				}
				break;
			case ROBOT_PROGRAM_RESET_NOT_IMP:
				revcResult = DeltaReturnStatus.NOT_IMPLEMENTED.getStatusName();
				break;
			case READ_DO:
			case READ_ROBOT_DO:	
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.DO_HIGH.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.DO_LOW.getStatusName();
				}else if(convertInt == -1 || convertInt == 255){
					revcResult = DeltaReturnStatus.READ_FAIL.getStatusName();
				}
				break;
			case READ_DI:
			case READ_ROBOT_DI:
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.DI_HIGH.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.DI_LOW.getStatusName();
				}else if(convertInt == -1 || convertInt == 255){
					revcResult = DeltaReturnStatus.READ_FAIL.getStatusName();
				}
				break;
			case HAS_ALARM:
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.HAS_ALARM.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.NO_ALARM.getStatusName();
				}
				break;
			//5 Byte	
			case GET_ALARM_CODE:
				break;
			case CLEAR_ALARM_STATE:
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.FAIL.getStatusName();
				}
				break;
			case GET_PROGRAM_STATUS:

				if(convertInt == 3){
					revcResult = DeltaReturnStatus.PAUSE.getStatusName();
				}else if(convertInt == 2){
					revcResult = DeltaReturnStatus.BREAK_POINT.getStatusName();
				}else if(convertInt == 1){
					revcResult = DeltaReturnStatus.RUNNING.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.CLOSE.getStatusName();
				}else if(convertInt == -1 || convertInt == 255){
					revcResult = DeltaReturnStatus.NOT_CONNECTED.getStatusName();
				}
				break;
			case IS_SAFE_TO_WRITE:
				if(convertInt == 1){
					revcResult = DeltaReturnStatus.READY.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.NOT_READY.getStatusName();
				}
				break;
			//3 Byte	
			case GET_CURRENT_RUNNING:
				break;
				
			case READ_ROBOT_CURRENT_AREA:
				if(convertInt == 3){
					revcResult = DeltaReturnStatus.AREA_C.getStatusName();
				}else if(convertInt == 2){
					revcResult = DeltaReturnStatus.AREA_B.getStatusName();
				}else if(convertInt == 1){
					revcResult = DeltaReturnStatus.AREA_A.getStatusName();
				}else if(convertInt == 0){
					revcResult = DeltaReturnStatus.UNDEFINED.getStatusName();
				}else if(convertInt == -1){
					revcResult = DeltaReturnStatus.READ_FAIL.getStatusName();
				}
				break;
		}
		return revcResult;
	}

}
