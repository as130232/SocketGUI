package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import client.ClientBase;
import client.ClientFactory;
import command.BaseMajorCommand;
import command.com.Company;
import command.delta.DeltaSubCommand;
import model.ConnectInfo;

public class TestGUI extends JFrame {

	private static Socket socket = null;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGUI frame = new TestGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 * @throws ParseException 
	 */
	public TestGUI() throws Exception{
		
		ClientFactory clientFactory= new ClientFactory();
		final ClientBase clientBase = clientFactory.getClientTemplate(Company.YCM);
		
		final ConnectInfo connectInfo = new ConnectInfo();
		connectInfo.setHostAndPort("127.0.0.1", 666);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 441);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//title
		JLabel lblSocketTest = new JLabel("Socket Connect Test");
		lblSocketTest.setFont(new Font("Yu Gothic", Font.PLAIN, 30));
		lblSocketTest.setBounds(56, 22, 572, 27);
		lblSocketTest.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSocketTest);
				
		
		//Robot Status 取得機器手臂參數 
		JScrollPane scrollPaneRobotStatus = new JScrollPane();
		scrollPaneRobotStatus.setBounds(28, 210, 262, 131);
		contentPane.add(scrollPaneRobotStatus);
		
		final JTextArea taStatus = new JTextArea();
		taStatus.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
		scrollPaneRobotStatus.setViewportView(taStatus);
		
		JLabel lblStatus = new JLabel("Robot Status");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPaneRobotStatus.setColumnHeaderView(lblStatus);
		
		
		JLabel lblIP = new JLabel("IP");
		lblIP.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
		lblIP.setBounds(34, 68, 57, 19);
		contentPane.add(lblIP);
		
		JLabel lblPort = new JLabel("Host Port");
		lblPort.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
		lblPort.setBounds(34, 104, 79, 19);
		contentPane.add(lblPort);
		
		//IP address
		final JTextField txIp = new JTextField("127.0.0.1");
		txIp.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		txIp.setColumns(10);
		txIp.setBounds(66, 63, 224, 25);
		contentPane.add(txIp);
		
		//IP address(MaskFormatter)
		MaskFormatter mfIpAddress = null;
		try {
			//mfIpAddress = new MaskFormatter("###.###.###.###");
			mfIpAddress = new MaskFormatter("###.#.#.#");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
//		final JFormattedTextField txIpInputPanel = new JFormattedTextField(mfIpAddress);
//		txIpInputPanel.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
//	    txIpInputPanel.setPreferredSize(new java.awt.Dimension(100, 20));
//	    txIpInputPanel.setBounds (new Rectangle(66, 63, 202, 27));
//	    txIpInputPanel.setText("127.0.0.1");
//	    contentPane.add(txIpInputPanel);
		
		final JTextField txPort = new JTextField("666");
		txPort.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		txPort.setBounds(151, 99, 139, 25);
		contentPane.add(txPort);
		txPort.setColumns(10);
		
		JLabel lblClientPort = new JLabel("Client Port");
		lblClientPort.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
		lblClientPort.setBounds(34, 139, 93, 19);
		contentPane.add(lblClientPort);
		
		final JTextField txClientPort = new JTextField("1313");
		txClientPort.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		txClientPort.setColumns(10);
		txClientPort.setBounds(151, 134, 139, 25);
		contentPane.add(txClientPort);
		
		//set Socket connect
		JButton btnConnect = new JButton("Connect");
		btnConnect.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		btnConnect.setBounds(28, 171, 116, 27);
		contentPane.add(btnConnect);
		
		//shut down Socket
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		btnDisconnect.setBounds(154, 171, 137, 27);
		contentPane.add(btnDisconnect);
		
		List<BaseMajorCommand> moveCommandArrayList = clientBase.getMoveCommands();
		BaseMajorCommand[] majorCommandArray = new BaseMajorCommand[moveCommandArrayList.size()];
		for(int i = 0; i < moveCommandArrayList.size(); i++){
			majorCommandArray[i] = moveCommandArrayList.get(i);
		}
		final JComboBox selectMajorCommand = new JComboBox(majorCommandArray);
		selectMajorCommand.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		selectMajorCommand.setBounds(306, 109, 231, 25);
		contentPane.add(selectMajorCommand);
		
		final JComboBox selectSubCommand = new JComboBox(DeltaSubCommand.values());
		selectSubCommand.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		selectSubCommand.setBounds(306, 150, 231, 25);
		contentPane.add(selectSubCommand);
		
		final JButton btnSendMoveCmd = new JButton("Send");
		//btnSendMoveCmd.setEnabled(false);
		btnSendMoveCmd.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
		btnSendMoveCmd.setBounds(560, 130, 99, 27);
		contentPane.add(btnSendMoveCmd);
		
		JLabel lblMovementCommand = new JLabel("Movement Command");
		lblMovementCommand.setFont(new Font("Yu Gothic", Font.PLAIN, 22));
		lblMovementCommand.setBounds(365, 71, 241, 23);
		contentPane.add(lblMovementCommand);
		
		JLabel lblOtherCommand = new JLabel("Other Command");
		lblOtherCommand.setFont(new Font("Yu Gothic", Font.PLAIN, 22));
		lblOtherCommand.setBounds(365, 209, 241, 23);
		contentPane.add(lblOtherCommand);
		

		List<BaseMajorCommand> otherCommandArrayList = clientBase.getOtherCommands();
		BaseMajorCommand[] otherCommandArray = new BaseMajorCommand[otherCommandArrayList.size()];
		for(int i = 0; i < otherCommandArrayList.size(); i++){
			otherCommandArray[i] = otherCommandArrayList.get(i);
		}
		final JComboBox selectOtherCommand = new JComboBox(otherCommandArray);
		selectOtherCommand.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		selectOtherCommand.setBounds(306, 250, 231, 25);
		contentPane.add(selectOtherCommand);
		
		final JButton btnSendOtherCmd = new JButton("Send");
		btnSendOtherCmd.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
		//btnSendOtherCmd.setEnabled(false);
		btnSendOtherCmd.setBounds(560, 272, 99, 27);
		contentPane.add(btnSendOtherCmd);
		
		JLabel lblDiDo = new JLabel("DI / Do index:");
		lblDiDo.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		lblDiDo.setBounds(312, 291, 123, 23);
		contentPane.add(lblDiDo);
		
		//DiDoIndex
		final JTextField txDiDoIndex = new JTextField("0");
		txDiDoIndex.setColumns(10);
		txDiDoIndex.setBounds(443, 291, 95, 25);
		//txDiDoIndex.setEnabled(false);
		contentPane.add(txDiDoIndex);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taStatus.setText("");
			}
		});
		btnClear.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
		btnClear.setBounds(110, 354, 99, 27);
		contentPane.add(btnClear);
		
		//若選擇是READ_DI、READ_DO打開index輸出
		selectOtherCommand.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				taStatus.setText("");
				//txDiDoIndex.setEnabled(false);
				String other = selectOtherCommand.getSelectedItem().toString();
				if(other.equals("READ_DI") || other.equals("READ_DO") ||  other.equals("READ_ROBOT_DO") ||  other.equals("READ_ROBOT_DI")){
					//txDiDoIndex.setEnabled(true);
				}
			}
		});
		
		
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taStatus.setText("");
				String host = txIp.getText();
				int port = Integer.parseInt(txPort.getText());
//				try {
//					socket = client.connect(host , port, connectInfo);
//				} catch (Exception e1) {
//					System.out.println("connect fail.");
//				}
				taStatus.append("IP: " + host + " , port: " + port + "\n");
				
				//btnSendMoveCmd.setEnabled(true);
				//btnSendOtherCmd.setEnabled(true);
			}
		});

		
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clientBase.disConnect(socket);
				} catch (Exception e1) {
					System.out.println("disConnect fail.");
				}
				taStatus.setText("Client Socket is Close");
				//System.exit(0);
			}
		});
		
		
		btnSendMoveCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//getMajor
				String majorStr = selectMajorCommand.getSelectedItem().toString();
				int majorValue = -1;
				try {
					majorValue = clientBase.getMajorByCmdName(majorStr);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				//getSub
				String subStr = (selectSubCommand.getSelectedItem()).toString();
				DeltaSubCommand subCommand = DeltaSubCommand.valueOf(subStr);
				int subValue = subCommand.getSub();
				
				int convertInt = -1;
				String revcResult = null;
				try {
					convertInt = clientBase.send(majorValue, subValue, connectInfo);
					revcResult = clientBase.getRevcResult(majorValue, convertInt);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				taStatus.append("--recv Result : (" + convertInt + ")" + revcResult + "--\n");
				taStatus.append("major:" + majorStr + "\nsub:" + subValue + "\n");
				String status = getStatus(convertInt);
				taStatus.append("status: " + status );
				//btnSendMoveCmd.setEnabled(false);
			}
		});
		
		
		btnSendOtherCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String otherStr = selectOtherCommand.getSelectedItem().toString();
				int otherValue = -1;
				try {
					otherValue = clientBase.getMajorByCmdName(otherStr);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				String indexStr = txDiDoIndex.getText();
				Integer index;
				try{
					index = Integer.parseInt(indexStr);
				}catch(NumberFormatException e1){
					index = null;
				}
				
				int convertInt = -1;
				String revcResult = null;
				try {
					convertInt = clientBase.send(otherValue, index, connectInfo);
					revcResult = clientBase.getRevcResult(otherValue, convertInt);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				taStatus.append("--recv Result : (" + convertInt + ") " + revcResult + "--\n");
				taStatus.append("other:" + otherStr + "\nindex:" + index + "\n");
				String status = getStatus(convertInt);
				taStatus.append("status: " + status);
				//btnSendOtherCmd.setEnabled(false);
			}
		});
	}

	public String getStatus(int converInt){
		String status = null;
		switch(converInt){
			case -1:
				status = "Input is unQualified.\nPlease re-enter.";
				break;
			default:
				status = "Send success.";
				break;
		}
		return status;
	}
}
