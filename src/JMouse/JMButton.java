package JMouse;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
/**
 * 
 * This class is used to create a button which can draw a read rectangle around its self
 *
 */
public class JMButton extends JButton{
	public boolean isSelected;
	private char [] objective=new char[2];//to store the text of the button 
	public boolean isButtonReady=true;//to indicated if the button is ready to be used again
	
	public JMButton(String text){
		super(text);
		isSelected=false;
	}
	public  void setObjective (char ob1,char ob2){
		objective[0]=ob1;
		objective[1]=ob2;
	}
	public char getObjective(int n){
		if(n<=1 && n>=0){
			return objective[n];
		}
		else
			return ' ';
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(isSelected){
			g.setColor(Color.red);
			g.drawRect(1, 1, getWidth()-3, getHeight()-3);
		}
	}
	

}
