package mas;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.*;
public class MasterAgent extends Agent {
	
	private MasterAgentGUI agentGUI;
	public ArrayList<String> attackerAgents = new ArrayList<String>();
	int armySize =0;
	
	//launch agent automatically
	protected void setup() {
		
		System.out.println("Master Agent "+getAID().getName()+" is ready to attack enemies' military base!!!");
		 
		 agentGUI = new MasterAgentGUI(this);
		 agentGUI.showGUI();
		 
		// Register the master agent service in the yellow pages
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("master-agent");
			sd.setName(getLocalName()+"-Master agent");
			dfd.addServices(sd);
			try {
				DFService.register(this, dfd);
			}
			catch (FIPAException fe) {
				fe.printStackTrace();
			}
		
	}
	
	protected void takeDown() {
		// Dispose the GUI if it is there
		if (agentGUI != null) {
			agentGUI.dispose();
		}
		
		// Deregister agent from the Directory Facilitator 
		// which tracks advertised services
		try {
			DFService.deregister(this);
			System.out.println("Master-agent "+getAID().getName()+" has been signed off.");
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Printout a dismissal message
		System.out.println("Master-agent "+getAID().getName()+"terminated.");
	}
	
	public void CreateAgents(String agentsNumber,String host,String port,String tickerTime, String fibb) throws ControllerException {
	
		String[] args = { tickerTime, host, port, fibb };
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		
		//Create a Profile, where the launch arguments are stored
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		
		//create a non-main agent container
		AgentContainer container = runtime.createAgentContainer(profile);
        AgentController ag;
        
        if(armySize==0) {
        	agentGUI.logTA.append("Attack STARTED!\n");
        }
        
        for (int i = 0; i < Integer.parseInt(agentsNumber); i++) {
			try {
				ag = container.createNewAgent("SoldierAgent" + (armySize+i), 
				                               "mas.SoldierAgent", 
				                               new Object[] {host, port, tickerTime, fibb});
				ag.start();
		
			} catch (StaleProxyException e) {
				e.printStackTrace();
			}
        }
        agentGUI.logTA.append(agentsNumber+" soldiers joined attack\n");
		armySize += Integer.parseInt(agentsNumber);
		agentGUI.existingAgentsNumberLabel.setText(""+armySize);
	}
	
	
	public void stopAttack() {
		addBehaviour(new OneShotBehaviour() {

		@Override
		public void action() {
		//	updateAttackerAgents();
			if (0 != armySize) {
				int counter = armySize;
				for (int i=1; i<=counter; i++) {
					ACLMessage msgACL = createMessage(ACLMessage.REQUEST, "Stop", new AID("SoldierAgent" + (counter-i), AID.ISLOCALNAME));
					send(msgACL);
					armySize--;
					
				}
			//	armySize = counter;

				agentGUI.logTA.setText("");
				agentGUI.logTA.append("Attack STOPPED!\n");
				agentGUI.existingAgentsNumberLabel.setText(""+0);
				
			} 
			else {
				armySize=0;
				
				JOptionPane.showMessageDialog(agentGUI, "No active soldiers to stop!", "WARNING", JOptionPane.WARNING_MESSAGE);
			}
			
		}
			
		private ACLMessage createMessage (int mp, String content, AID dest) {
			ACLMessage msgACL;
			msgACL = new ACLMessage(mp);
			msgACL.setContent(content);
			msgACL.addReceiver(dest);
			
			return msgACL;
			}
		});
		
	}
	
}



