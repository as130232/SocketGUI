package test;

import java.util.List;
import java.util.Scanner;

import client.ClientBase;
import client.ClientFactory;
import command.BaseMajorCommand;
import command.Module;
import command.com.Company;
import command.delta.DeltaMajorCommand;
import command.delta.DeltaReturnStatus;
import command.ycm.YcmMajorCommand;
import server.ServerBase;
import server.impl.ServerDelta;

public class Test {

	
	public static void main(String[] args) throws Exception {
		
		double aaa = 1 * (0.5);
		double bbb = 1/2;
		double ccc = 1d/2;
		System.out.println("aaa:" + aaa);
		System.out.println("bbb:" + bbb);
		System.out.println("ccc:" + ccc);
		
		ServerBase server = new ServerDelta();
		ClientFactory clientFactory= new ClientFactory();
		
		ClientBase clientBase = clientFactory.getClientTemplate(Company.DELTA);
		
		
		while(true){
			System.out.println("plz input command:");
			Scanner scanner = new Scanner(System.in);
			int cmd = scanner.nextInt();
			switch(cmd){
			case 0:
				System.out.println("----------test Server----------");
				System.out.println("主 前執行緒名稱: " + Thread.currentThread().getName());
				System.out.println("執行緒數量: " + Thread.activeCount());
				
				server.bulidServer(1313);
				
				break;
			case 1:
				server.shutDownServer();
				System.out.println("---");
				System.out.println("關閉後，執行緒數量: " + Thread.activeCount());
				break;
				
			case 2:
				//YcmMajorCommand ycmMajorCommand = YcmMajorCommand.getCmdNameByMajor(3);
				//System.out.println("ycmMajorCommand:" + ycmMajorCommand);
				List<BaseMajorCommand> ycmMajorCommandArr = YcmMajorCommand.getMoveCommandArray();
				System.out.println("----- YcmMajorCommand.getMoveCommandArray -----");
				for(BaseMajorCommand ycmMajorCommand:ycmMajorCommandArr){
					System.out.println("ycmMajorCommand:" + ycmMajorCommand);
				}
				List<BaseMajorCommand> ycmOtherCommandArr = YcmMajorCommand.getOtherCommandArray();
				System.out.println("----- YcmMajorCommand.getOtherCommandArray -----");
				for(BaseMajorCommand ycmOtherCommand:ycmOtherCommandArr){
					System.out.println("ycmOtherCommand:" + ycmOtherCommand);
				}
				
				break;
			case 3:
				//YcmMajorCommand ycmMajorCommand = YcmMajorCommand.getCmdNameByMajor(3);
				//System.out.println("ycmMajorCommand:" + ycmMajorCommand);
				List<BaseMajorCommand> deltaMajorCommandArr = DeltaMajorCommand.getMoveCommandArray();
				System.out.println("----- DeltaMajorCommand.getMoveCommandArray -----");
				for(BaseMajorCommand deltaMajorCommand:deltaMajorCommandArr){
					System.out.println("deltaMajorCommandArr:" + deltaMajorCommand);
				}
				List<BaseMajorCommand> deltaOtherCommandArr = DeltaMajorCommand.getOtherCommandArray();
				System.out.println("----- DeltaMajorCommand.getOtherCommandArray -----");
				for(BaseMajorCommand deltaOtherCommand:deltaOtherCommandArr){
					System.out.println("deltaOtherCommandArr:" + deltaOtherCommand);
				}
				
				break;
			
			case 4:
				System.out.println("------ test DELTA/YCM getRevcResult ------");
				System.out.println("plz input command:");
				Scanner scannerCommand = new Scanner(System.in);
				int command = scannerCommand.nextInt();
				System.out.println("plz input convertInt:");
				Scanner scannerConvertInt = new Scanner(System.in);
				int convertInt = scannerConvertInt.nextInt();
				
				String getRevcResult = clientBase.operatCmd.getRevcResult(command, convertInt);
				System.out.println("getRevcResult DELTA/YCM result : " + getRevcResult);
				break;
				
			case 5:
				System.out.println("------ test DELTA/YCM getCommandLine( source, target) ------");
				System.out.println("plz input source:");
				Scanner scannerSource = new Scanner(System.in);
				String sourceStr = scannerSource.nextLine();
				Module source = Module.valueOf(sourceStr);
				System.out.println("plz input target:");
				Scanner scannerTarget = new Scanner(System.in);
				String targetStr = scannerTarget.nextLine();
				Module target = Module.valueOf(targetStr);
				
				int major = clientBase.getCommandLine(source, target);
				BaseMajorCommand cmdName = clientBase.getCmdNameByMajor(major);
				System.out.println("major:" + major + " ,commandName:" + cmdName);
				break;
			case 6:
				System.out.println("------ test DELTA/YCM getCommandLine( source, target) ------");
				System.out.println("plz input number:");
				Scanner scannerNumber = new Scanner(System.in);
				int num = scannerNumber.nextInt();
				Test test = new Test();
				System.out.println("to binary Gap:" + test.solution(num));
				break;
				
			case 7:
				int resultInt = 1;
				int command2 = 241;
				String revcResult = clientBase.operatCmd.getRevcResult(command2, resultInt);
				DeltaReturnStatus area = DeltaReturnStatus.convertByString(revcResult.toString().toUpperCase());
				//判斷回傳值是對應哪個機械手區域的Module
				String areaStr = area.toString();
				String splitArear = areaStr.replaceAll("AREA_", "");
				Module resultArea = Module.convertByString((splitArear.toUpperCase()));
				System.out.println("Module resultArea:" + resultArea.toString());
				break;
				
			case 8:
				Module currentArea = Module.AREAA;
				Module targetArea = Module.AREAC;
				Integer resultCommandLine = null;
				//當要下達的command與當前位置不一樣時，要取得移動到該區域的指令
				if( !targetArea.equals(currentArea) ){
				//當前位置為起點source，參數targetArea為目標地
					resultCommandLine = clientBase.getCommandLine(currentArea, targetArea);
				}
				
				System.out.println("resultCommandLine:" + resultCommandLine);
				break;
				
				
			case 9:
				Integer command3 = 3;
				Integer resultInt2 = 255;
				
				String revcResultStr = clientBase.getRevcResult(command3, resultInt2);
				DeltaReturnStatus returnStatus = DeltaReturnStatus.getDeltaReturnStatusByStatusName(revcResultStr);
				System.out.println("returnStatus:" + returnStatus.toString());
				break;
				
			case 10:
				
				String x = "0E";
				Integer a = Integer.valueOf( x, 16 );
				System.out.println("result:" + a);
				break;
			}
			
			
		}
		
		
		
		
		
	}

	public int solution(int N) {
        String binary = Integer.toBinaryString(N);
        int i = binary.length()-1;
        while(binary.charAt(i)=='0') {
            i--;
        }
        int gap = 0;
        int counter = 0;
        for(; i>=0; i--) {
            if(binary.charAt(i)=='1') {
                gap = Math.max(gap, counter);
                counter = 0;
            } else {
                counter++;
            }
        }
        gap = Math.max(gap, counter);
        return gap;
    }
	
}
