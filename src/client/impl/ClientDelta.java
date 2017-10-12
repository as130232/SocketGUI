package client.impl;

import java.util.List;

import client.ClientBase;
import command.BaseMajorCommand;
import command.Module;
import command.delta.DeltaMajorCommand;
import operatCmd.impl.OperatCmdForDelta;

public class ClientDelta extends ClientBase{

	public ClientDelta(){
		//專for台達電的操作指令類別
		this.operatCmd = new OperatCmdForDelta();
	}
	
	@Override
	public int getCommandLine(Module source, Module target) throws Exception {
		String sourceStr = source.toString().toUpperCase();
		String targetStr = target.toString().toUpperCase();
		
		DeltaMajorCommand resultCommand = null;
		List<BaseMajorCommand> moveCommandArray = DeltaMajorCommand.getMoveCommandArray();
		for(BaseMajorCommand deltaMajorCommand:moveCommandArray){
			String deltaMajorCommandStr = deltaMajorCommand.toString();
			int indexOfSource = deltaMajorCommandStr.indexOf(sourceStr);
			int indexOfTarget = deltaMajorCommandStr.indexOf(targetStr);
			
			int lastIndexOfBottomLine = deltaMajorCommandStr.lastIndexOf("_");
			// != -1表示該指令皆有source、target，且source的位置是在第0個、target的位置在最後一個底線之後
			if(indexOfSource != -1 && indexOfTarget != -1 
					&& indexOfSource == 0 && indexOfTarget == (lastIndexOfBottomLine + 1)){
				resultCommand = DeltaMajorCommand.valueOf(deltaMajorCommandStr);
			}
		}
		if(resultCommand != null){
			return resultCommand.getMajor();
		}
		return (DeltaMajorCommand.UNQUALIFIED).getMajor();
	}

	@Override
	public Boolean isCommandLineExist(int command) throws Exception {
		
		BaseMajorCommand deltaMajorCommand = DeltaMajorCommand.getCmdNameByMajor(command);
		if(deltaMajorCommand != null){
			return true;
		}
		return false;
	}

	@Override
	public BaseMajorCommand getCmdNameByMajor(int major) throws Exception {
		return DeltaMajorCommand.getCmdNameByMajor(major);
	}

	@Override
	public List<BaseMajorCommand> getMoveCommands() throws Exception {
		return DeltaMajorCommand.getMoveCommandArray();
	}

	@Override
	public List<BaseMajorCommand> getOtherCommands() throws Exception {
		return DeltaMajorCommand.getOtherCommandArray();
	}

	@Override
	public Integer getMajorByCmdName(String commandName) throws Exception {
		DeltaMajorCommand deltaMajorCommand = DeltaMajorCommand.valueOf(commandName);
		return  deltaMajorCommand.getMajor();
	}



}
