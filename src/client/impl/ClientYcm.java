package client.impl;

import java.util.List;

import client.ClientBase;
import command.BaseMajorCommand;
import command.Module;
import command.ycm.YcmMajorCommand;
import operatCmd.impl.OperatCmdForYcm;

public class ClientYcm extends ClientBase {
	
	
	public ClientYcm(){
		//專for永進的操作指令類別
		this.operatCmd = new OperatCmdForYcm();
	}

	@Override
	public int getCommandLine(Module source, Module target) throws Exception {
		String sourceStr = source.toString().toUpperCase();
		String targetStr = target.toString().toUpperCase();
		
		YcmMajorCommand resultCommand = null;
		List<BaseMajorCommand> moveCommandArray = YcmMajorCommand.getMoveCommandArray();
		for(BaseMajorCommand ycmMajorCommand:moveCommandArray){
			String ycmMajorCommandStr = ycmMajorCommand.toString();
			int indexOfSource = ycmMajorCommandStr.indexOf(sourceStr);
			int indexOfTarget = ycmMajorCommandStr.indexOf(targetStr);
			
			int lastIndexOfBottomLine = ycmMajorCommandStr.lastIndexOf("_");
			// != -1表示該指令皆有source、target，且source的位置是在第0個、target的位置在最後一個底線之後
			if(indexOfSource != -1 && indexOfTarget != -1 
					&& indexOfSource == 0 && indexOfTarget == (lastIndexOfBottomLine + 1)){
				resultCommand = YcmMajorCommand.valueOf(ycmMajorCommandStr);
			}
		}
		if(resultCommand != null){
			return resultCommand.getMajor();
		}
		return (YcmMajorCommand.UNQUALIFIED).getMajor();
	}
	
	
	@Override
	public Boolean isCommandLineExist(int command) throws Exception {
		BaseMajorCommand ycmMajorCommand = YcmMajorCommand.getCmdNameByMajor(command);
		if(ycmMajorCommand != null){
			return true;
		}
		return false;
	}

	@Override
	public List<BaseMajorCommand> getMoveCommands() throws Exception {
		return YcmMajorCommand.getMoveCommandArray();
	}

	@Override
	public List<BaseMajorCommand> getOtherCommands() throws Exception {
		return YcmMajorCommand.getOtherCommandArray();
	}

	@Override
	public BaseMajorCommand getCmdNameByMajor(int major) throws Exception {
		return YcmMajorCommand.getCmdNameByMajor(major);
	}

	@Override
	public Integer getMajorByCmdName(String commandName) throws Exception {
		YcmMajorCommand ycmMajorCommand = YcmMajorCommand.valueOf(commandName);
		return ycmMajorCommand.getMajor();
	}
	
}
