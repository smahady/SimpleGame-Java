import java.io.*; 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame; 

/** 
 * Title Screen
 */ 
public class SimpleGameDemo extends JFrame implements ActionListener
{
  
	JButton btn1 = new JButton("Level 1");
	JButton btn2 = new JButton("Level 2");
	JButton btn3 = new JButton("Generate Level");

	public SimpleGameDemo(){
    
		this.setUpGUI(); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(640, 480);
		this.setTitle("Game Menu");
	} // end constructor
  
	public void setUpGUI(){
		//like it says, set up GUI
		Container pnlMain = this.getContentPane();
    
		pnlMain.setLayout(new FlowLayout());

		pnlMain.add(btn1);
		pnlMain.add(btn2);


		//add action listeners
		btn1.addActionListener(this);
		btn2.addActionListener(this);



	} // end setUpGUI

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == btn1){
			GameLevel myGame = new GameLevel("Level1");
			this.dispose();
		} else if (e.getSource() == btn2){  
			GameLevel2 myGame = new GameLevel2("Level2");
			this.dispose();
		} else if (e.getSource() == btn3){  
			//MakeLevel = new MakeLevel("Level1");
			this.dispose();
 
		} else {  
			System.out.println("action not defined");
		} // end if
	} // end actionPerformed

	public static void main(String[] args){
		new SimpleGameDemo();
	} // end main


} // end class def
