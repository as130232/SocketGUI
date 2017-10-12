package operatCmd.impl;

import command.ycm.YcmMajorCommand;
import command.ycm.YcmReturnStatus;
import operatCmd.OperatCmd;

public class OperatCmdForYcm implements OperatCmd{

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
		YcmMajorCommand ycmMajorCommand = (YcmMajorCommand) YcmMajorCommand.getCmdNameByMajor(command);
		switch(ycmMajorCommand){
		case READ_ALARM_CODE:
			size = 2;
			break;
		case READ_STATUS_CODE:
			size = 2;
			break;
		case READ_CURRENT_COMMAND:
			size = 3;
			break;
		}	
		return size;
	}

	@Override
	public boolean isQualified(int command, int sub) throws Exception {
		boolean isQualified = true;
		
		if(command < YcmMajorCommand.NOT_A_COMMAND.getMajor() || command > 256){
			isQualified = false;
		}
		else if(command > YcmMajorCommand.NOT_A_COMMAND.getMajor() && command <= YcmMajorCommand.CMM_TO_MAG.getMajor() 
				&& command != YcmMajorCommand.MC1_TO_LOADING.getMajor() && command != YcmMajorCommand.MC2_TO_LOADING.getMajor() && command != YcmMajorCommand.CMM_TO_LOADING.getMajor()){
			if(sub < 1 || sub > 12){
				isQualified = false;
			}
		}
		else if(command == YcmMajorCommand.READ_ALARM_CODE.getMajor() || command == YcmMajorCommand.READ_STATUS_CODE.getMajor()){
			if(sub < 0 || sub > 3){
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
		YcmMajorCommand ycmMajorCommand = (YcmMajorCommand) YcmMajorCommand.getCmdNameByMajor(command);
		switch(ycmMajorCommand){
			case LOADING_TO_MAG:
			case MAG_TO_LOADING:
			case MAG_TO_MC1:
			case MC1_TO_MAG:
			case MAG_TO_MC2:
			case MC2_TO_MAG:
			case MAG_TO_CMM:
			case CMM_TO_MAG:	
				if(convertInt == 1){
					revcResult = YcmReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == 0){
					revcResult = YcmReturnStatus.UNABLE_TO_WRITE_COMMAND.getStatusName();
				}else if(convertInt == -1){
					revcResult = YcmReturnStatus.INVALID_SUB.getStatusName();
				}
				break;
			case MC1_TO_LOADING:
			case MC2_TO_LOADING:
			case CMM_TO_LOADING:
			case MC1_TO_CMM:
			case MC2_TO_CMM:
			case LOADING_TO_MC1:
			case LOADING_TO_MC2:
			case LOADING_TO_CMM:	
				if(convertInt == 1){
					revcResult = YcmReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == 0){
					revcResult = YcmReturnStatus.UNABLE_TO_WRITE_COMMAND.getStatusName();
				}else if(convertInt == -1){
					revcResult = YcmReturnStatus.UNABLE_TO_CONNECT.getStatusName();
				}
				break;
				
				//not implemented
//			case MC1_CYCLE_START:
//			case MC2_CYCLE_START:
//			case CMM_CYCLE_START:
				
			
			case ROBOT_CONNECT_STATUS:
				if(convertInt == 1){
					revcResult = YcmReturnStatus.CONNECTED.getStatusName();
				}else if(convertInt == 0){
					revcResult = YcmReturnStatus.NOT_CONNECTED.getStatusName();
				}else if(convertInt == -1){
					revcResult = YcmReturnStatus.FAIL_TO_READ.getStatusName();
				}
				break;
			case RESUME_ROBOT:
			case RAUSE_ROBOT:
			case RESET_ROBOT:
			case CLEAR_ALARM:
				if(convertInt == 0){
					revcResult = YcmReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == -1){
					revcResult = YcmReturnStatus.FAIL_TO_READ.getStatusName();
				}
				break;
			case ROBOT_MACRO_STATUS:
				if(convertInt == -1){
					revcResult = YcmReturnStatus.FAIL_TO_READ.getStatusName();
				}
				break;
			
			//2 Byte
			case READ_ALARM_CODE:
			case READ_STATUS_CODE:
				if(convertInt == 1){
					revcResult = YcmReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == 0){
					revcResult = YcmReturnStatus.UNABLE_TO_WRITE_COMMAND.getStatusName();
				}else if(convertInt == -1){
					revcResult = YcmReturnStatus.INVALID_SUB.getStatusName();
				}
				break;
			case READ_COMMAND_RETURN:
				if(convertInt == 1){
					revcResult = YcmReturnStatus.BUSY.getStatusName();
				}else if(convertInt == 0){
					revcResult = YcmReturnStatus.READY_FOR_COMMAND.getStatusName();
				}else if(convertInt == -1){
					revcResult = YcmReturnStatus.FAIL_TO_READ.getStatusName();
				}
				break;
			//3 Byte
			case READ_CURRENT_COMMAND:
				if(convertInt == 1){
					revcResult = YcmReturnStatus.SUCCESS.getStatusName();
				}else if(convertInt == 0){
					revcResult = YcmReturnStatus.UNABLE_TO_WRITE_COMMAND.getStatusName();
				}else if(convertInt == -1){
					revcResult = YcmReturnStatus.FAIL_TO_READ.getStatusName();
				}
				break;
			case SAFE_TO_WRITE:
				if(convertInt == 1){
					revcResult = YcmReturnStatus.READY_FOR_COMMAND.getStatusName();
				}else if(convertInt == 0){
					revcResult = YcmReturnStatus.BUSY.getStatusName();
				}else if(convertInt == -1){
					revcResult = YcmReturnStatus.FAIL_TO_READ.getStatusName();
				}
				break;
		}
		return revcResult;
	}

}
