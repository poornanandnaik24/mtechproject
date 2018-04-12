package application;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import com.fazecast.jSerialComm.*;
import java.io.PrintWriter;

public class bluetooth {
public static void main(String[] args){
		
bluetooth j = new bluetooth();
j.Connect_Frame();
j.Frame();
		
	}

public SerialPort port;
	
	public void Frame(){
JFrame window = new JFrame();
JButton led_1 = new JButton("Authenticate");
JButton exit = new JButton("EXIT");
led_1.setFocusable(false);
		exit.setFocusable(false);
		window.setLayout(new FlowLayout());
		window.add(led_1);
		window.add(exit);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setSize(400,500);
		window.setResizable(true);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exit.addActionListener(new Exit());
		led_1.addActionListener(new Led_1());
	}
	public void Connect_Frame(){
		JFrame arduino = new JFrame();
		JComboBox<String> portList = new JComboBox<String>();
		JButton connectButton = new JButton("Connect");
		connectButton.setFocusable(false);
		arduino.setLayout(new FlowLayout());
		arduino.add(portList);
		arduino.add(connectButton);
		arduino.setLocationRelativeTo(null);
		arduino.setSize(400,500);
		arduino.setResizable(true);
		arduino.setVisible(true);
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());
		connectButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					
					port = SerialPort.getCommPort(portList.getSelectedItem().toString());
					port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(port.openPort()) {
						connectButton.setText("Disconnect");
						portList.setEnabled(false);
						
					}
				
					
					} 
				else {
					
					port.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
				}
			}
		});
	}
public class Exit implements ActionListener{
public void actionPerformed(ActionEvent a){
			System.exit(0);   
		}
   	}
	public int led_1_state = 0;
	public class Led_1 implements ActionListener{
		public void actionPerformed(ActionEvent a){
			PrintWriter output = new PrintWriter(port.getOutputStream());
			if (led_1_state == 0){
				output.print("1");
				output.flush();
				System.out.println("Authenticated. Continue to Login");
				led_1_state = 1;
			}
			else {
				output.print("0");
				output.print("1");
				output.print("0");
				output.print("1");
				output.print("0");
				output.print("1");
				output.print("0");
				output.flush();
				System.out.println("rechecking");
				led_1_state = 0;
			}
		}
   	}
}
