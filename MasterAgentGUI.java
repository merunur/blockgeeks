package mas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import jade.wrapper.ControllerException;

public class MasterAgentGUI extends JFrame{

	private MasterAgent masterAgent;
	
	JPanel mainPanel, existingAgentsPanel, newAgentsPanel, checkServerPanel, agentsNumberPanel, tickerPanel, logPanel, fibbPanel;
	JLabel newAgentsLabel, existingAgentsLabel, checkServerLabel, serverStatusLabel, hostAddressLabel, tickerLabel, fibbLabel, existingAgentsNumberLabel;
	JTextField agentsToCreateTF, serverAddressTF, hostAddressTF, tickerTF, fibbTF;
	JTextArea logTA;
	JButton createButton, pingButton, stopButton;
	
	public MasterAgentGUI(MasterAgent myAgent) {
		super(myAgent.getLocalName());
		
		masterAgent = myAgent;
		
		mainPanel = new JPanel();
		BoxLayout boxlayoutY = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainPanel.setLayout(boxlayoutY);
		
		existingAgentsPanel = new JPanel();
		BoxLayout boxlayoutX = new BoxLayout(existingAgentsPanel, BoxLayout.X_AXIS);
		existingAgentsPanel.setLayout(boxlayoutX);
		
		
		existingAgentsLabel = new JLabel("The army size: ");
		existingAgentsLabel.setPreferredSize(new Dimension(200, 30));
		
		existingAgentsNumberLabel = new JLabel("some number");
		existingAgentsNumberLabel.setPreferredSize(new Dimension(200, 30));
		
		existingAgentsPanel.add(existingAgentsLabel);
		existingAgentsPanel.add(existingAgentsNumberLabel);
		
		newAgentsPanel = new JPanel();
		BoxLayout boxlayoutX2 = new BoxLayout(newAgentsPanel, BoxLayout.X_AXIS);
		newAgentsPanel.setLayout(boxlayoutX2);
		
		agentsNumberPanel = new JPanel();
		BoxLayout boxlayoutX4 = new BoxLayout(agentsNumberPanel, BoxLayout.X_AXIS);
		agentsNumberPanel.setLayout(boxlayoutX4);
		
		newAgentsLabel = new JLabel("Number of soldiers to create:");
		newAgentsLabel.setPreferredSize(new Dimension(200, 30));
		
		agentsToCreateTF = new JTextField("2");
		agentsToCreateTF.setPreferredSize(new Dimension(200, 30));
		
		agentsNumberPanel.add(newAgentsLabel);
		agentsNumberPanel.add(agentsToCreateTF);
		
		tickerLabel = new JLabel("Ticker duration(ms):");
		tickerLabel.setPreferredSize(new Dimension(200, 30));
		
		tickerTF = new JTextField("1000");
		tickerTF.setPreferredSize(new Dimension(200, 30));
		
		tickerPanel = new JPanel();
		BoxLayout boxlayoutX5 = new BoxLayout(tickerPanel, BoxLayout.X_AXIS);
		tickerPanel.setLayout(boxlayoutX5);
		
		tickerPanel.add(tickerLabel);
		tickerPanel.add(tickerTF);
		
		fibbLabel = new JLabel("Fibbonacci to count:");
		fibbLabel.setPreferredSize(new Dimension(200, 30));
		
		fibbTF = new JTextField("10");
		fibbTF.setPreferredSize(new Dimension(200, 30));
		
		fibbPanel = new JPanel();
		BoxLayout boxlayoutX7 = new BoxLayout(fibbPanel, BoxLayout.X_AXIS);
		fibbPanel.setLayout(boxlayoutX7);
		
		fibbPanel.add(fibbLabel);
		fibbPanel.add(fibbTF);
	
		
		hostAddressLabel = new JLabel("Enter the enemy host:port");
		hostAddressLabel.setPreferredSize(new Dimension(200, 30));
		
		hostAddressTF = new JTextField("localhost:9090");
		hostAddressTF.setPreferredSize(new Dimension(200, 30));
		
		createButton = new JButton("Create");
		createButton.setPreferredSize(new Dimension(200, 40));
		
		createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	String agentsNumber = agentsToCreateTF.getText(); //number of agents to create
            	String address = hostAddressTF.getText(); //host address
            	String fullAddress[]=address.split(":");
        		String host=fullAddress[0];
        		String port = fullAddress[1];
        		String fibb = fibbTF.getText();
        		String tickerTime = tickerTF.getText();
             	try {
					masterAgent.CreateAgents(agentsNumber, host, port, tickerTime, fibb);
				} catch (ControllerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
		newAgentsPanel.add(hostAddressLabel); 
		newAgentsPanel.add(hostAddressTF);
		
		
	
		
		createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	String hostAddress = hostAddressTF.getText(); //host adress
            // 	masterAgent.CheckServer(hostAddress);
            }
        });
		
		logPanel = new JPanel();
		logTA = new JTextArea();
		logTA.setEditable(false);
		logTA.setPreferredSize(new Dimension(400, 250));
		//logTA.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		JScrollPane logScroll = new JScrollPane(logTA);
		logScroll.setPreferredSize(new Dimension(400, 250));
		logPanel.add(logScroll);
		
		
		stopButton = new JButton("Stop");
		stopButton.setPreferredSize(new Dimension(200, 40));
		
		stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
             masterAgent.stopAttack();
            }
        });
		
		
		mainPanel.add(existingAgentsPanel);
		mainPanel.add(agentsNumberPanel);
		mainPanel.add(tickerPanel);
		mainPanel.add(fibbPanel);
		mainPanel.add(newAgentsPanel);
		mainPanel.add(createButton);
		mainPanel.add(logPanel);
		mainPanel.add(stopButton);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,10,10,10));
			
		setLayout(new FlowLayout());  // "super" Frame sets to BorderLayout
		add(mainPanel);
		
		setTitle("Master Agent"); 
	    setSize(500, 550); 
		
	}
	
	public void showGUI() {
	//	pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	
	}
}

