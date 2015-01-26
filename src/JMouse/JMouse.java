package JMouse;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;




import net.java.games.input.Component;
import net.java.games.input.Controller;

import net.java.games.input.ControllerEnvironment;

public class JMouse implements Runnable{
	public Thread thread;
	private Robot robot;
	private int mouseSpeed=60;//this variable is used to determine the mouse's speed
	private int L2=10-mouseSpeed;/*this variable is used to either increase or decrease the mouse's speed  
		 positive values are used to increase the speed 
	*/	
	private int numberOfButtons=14;
	boolean isWorking;//this going to be used to stop the Thread!
	private boolean isKeyboardOn=false;//this going to be used to determine whether keyboard is on screen or not
	private Controller controller;//the main controller
	private Component [] components=new Component [numberOfButtons];//
	JKeyBoard keyboard;
	private boolean isMouseSpeedChanged=false;
	
	
	public JMouse(){
		
		
		keyboard=new JKeyBoard();
		thread=new Thread(this);
		try {
			robot=new Robot();
		} catch (AWTException e) {
			
			e.printStackTrace();
		}
		isWorking=true;
		
		
	}
	public void reset(){
		try{
		java.io.File f=new java.io.File("JMouseData.data");
		f.delete();
		System.exit(0);
		}catch (Exception e){
			
		}
		
	}
	boolean isAllComponentsHaveBeenInitialized(){
		for(int i=0;i<numberOfButtons;i++){
			if(components[i]==null)
				return false;
		}
		return true;
	}
	public boolean loadComponents(){
		try {
			int id=0;
			BufferedReader reader = new BufferedReader(new FileReader("JMouseData.data"));
			String line=null;
			line=reader.readLine();
			if(isMouseSpeedChanged==false){//if it's not the default speed(the user didn't set it up)
			mouseSpeed=Integer.valueOf(line);
			L2=10-mouseSpeed;
			}
			while((line=reader.readLine())!=null){	
				String parts[]=line.split(":");
				for(int i=0;i<numberOfButtons;i++){
					String temp=IDS.IdToName(i);
					if(temp.equals(parts[0])){
						id=i;
						break;
						
					}
				}
					for(Component c:controller.getComponents()){
						if(c.getIdentifier().toString().equals(parts[1]))
							components[id]=c;
					}	
			}
				reader.close();
		}
		catch(FileNotFoundException e){
			
			;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("An error occurs during lodading the components\n");
			return false;	
		}
		catch (Exception e){
			return false;	
		}
		
		return isAllComponentsHaveBeenInitialized();
	}
	public void saveComponent(){//to write the components to a file so we can load it next time
		try {
			PrintWriter write= new PrintWriter("JMouseData.data");
			write.println(mouseSpeed);
			for(int id=0;id<numberOfButtons;id++)
				write.println(IDS.IdToName(id)+":"+components[id].getIdentifier());
			
			write.close();
			System.out.println("Writing done ");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Something happend couldn't wirte data\n"+
					"you will have to  re-dedect buttons next time you use it");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(isMouseSpeedChanged==true)
			saveComponent();
		
	}
	boolean isControllerInitialized(){
		return!(controller==null);
	}
	
	public void run() {
		boolean isSequre=false;//this variable is used as a flag to prevent the overlapping (keep holding the sequre button)
		boolean isShiftHold=false;//to determine whether shift is hold or not
		boolean isKeyboardShiffting=false;//to determine if the keyboard is in the shifting mode or not
		boolean isR1=false;// this variable is used as a flag to prevent the overlapping (keep holding the R1 button)
		boolean isX=false;//this variable is used as a flag to prevent the overlapping (keep holding the X button)
		boolean isTRIANGLE=false;//this variable is used as a flag to prevent the overlapping (keep holding the TRIANGLE button)
		boolean isCIRCLE=false;///this variable is used as a flag to prevent the overlapping (keep holding the CIRCLE button)
		boolean isL1=false;//this variable is used as a flag to prevent the overlapping (keep holding the L1 button)
		int l2=0;
		
		while (isWorking ==true){
			controller.poll();
			for(int i=0;i<numberOfButtons;i++){
				switch (i){
					case IDS.ID_DOWN:
						if(components[IDS.ID_DOWN].getPollData()==1){	
							if(isKeyboardOn){
								if(!isKeyboardShiffting)
								keyboard.down();
								else{
									Point p=keyboard.getLocation();
									p.y+=mouseSpeed;
									keyboard.setLocation(p);
								}
							}
							else{
								robot.mouseMove((int)MouseInfo.getPointerInfo().getLocation().getX(),(int) MouseInfo.getPointerInfo().getLocation().getY()+(mouseSpeed+l2));
							}
						}
						else if(components[IDS.ID_DOWN].getPollData()==-1){
							if(isKeyboardOn){
								if(!isKeyboardShiffting)
									keyboard.up();
								else{
									Point p=keyboard.getLocation();
									p.y-=mouseSpeed;
									keyboard.setLocation(p);
									
								}
							}
							else{
								robot.mouseMove((int)MouseInfo.getPointerInfo().getLocation().getX(),(int) MouseInfo.getPointerInfo().getLocation().getY()-(mouseSpeed+l2));

								
							}
						}
					break;
					
					case IDS.ID_LEFT:
						if(components[IDS.ID_LEFT].getPollData()==1){	
							if(isKeyboardOn){
								if(!isKeyboardShiffting)
									keyboard.right();
								else{
									Point p=keyboard.getLocation();
									p.x+=mouseSpeed;
									keyboard.setLocation(p);
									
								}
							}
							else{
								robot.mouseMove((int)MouseInfo.getPointerInfo().getLocation().getX()+(mouseSpeed+l2),(int) MouseInfo.getPointerInfo().getLocation().getY());

							}
						}
						else if(components[IDS.ID_LEFT].getPollData()==-1){
							if(isKeyboardOn){
								if(!isKeyboardShiffting)
									keyboard.left();
								else{
									Point p=keyboard.getLocation();
									p.x-=mouseSpeed;
									keyboard.setLocation(p);
								}
							}
							else{
								robot.mouseMove((int)MouseInfo.getPointerInfo().getLocation().getX()-(mouseSpeed+l2),(int) MouseInfo.getPointerInfo().getLocation().getY());

							}
						}
					break;
					
					case IDS.ID_X:
						if(components[IDS.ID_X].getPollData()==1){	
							if(!isX){
								isX=true;
								if(isKeyboardOn){
									robot.keyPress(KeyEvent.VK_SPACE);
									robot.keyRelease(KeyEvent.VK_SPACE);
								}
							}
								if(!isKeyboardOn){
									robot.mouseWheel(1); 
								}
							 
						}
						else {
							isX=false;
							
						}
					break;
					
					case IDS.ID_SQUARE:
						if(components[IDS.ID_SQUARE].getPollData()==1&!isSequre){	
							isKeyboardOn=!isKeyboardOn;//will switch the boolean flag
							keyboard.setVisible(isKeyboardOn);//
							isSequre=true;//
						}
						else if(components[IDS.ID_SQUARE].getPollData()==0){
							isSequre=false;//
						}
					break;
					
					case IDS.ID_TRIANGLE:
						if(components[IDS.ID_TRIANGLE].getPollData()==1){	
							if(!isTRIANGLE){
								isTRIANGLE=true;
								if(isKeyboardOn){
									robot.keyPress(KeyEvent.VK_CONTROL);
								}
							}
								if(!isKeyboardOn){
									robot.mouseWheel(-1);
								}
							
						}
						else {
							isTRIANGLE=false;
							if(isKeyboardOn)
								robot.keyRelease(KeyEvent.VK_CONTROL);
							
						}
					break;
					
					case IDS.ID_CIRCLE:
						if(components[IDS.ID_CIRCLE].getPollData()==1){	
							if(!isCIRCLE){
								isCIRCLE=true;
								if(isKeyboardOn){
									robot.keyPress(KeyEvent.VK_ENTER);
									robot.keyRelease(KeyEvent.VK_ENTER);
									}
							}
						}
						else {
							isCIRCLE=false;
						}
							
					break;
					

					case IDS.ID_R1:
						if(components[IDS.ID_R1].getPollData()==1){	
							if(!isR1){
								isR1=true;
								if(isKeyboardOn){
									robot.keyPress(KeyEvent.VK_BACK_SPACE);
									robot.keyRelease(KeyEvent.VK_BACK_SPACE);
								}
								else{
									robot.mousePress(InputEvent.BUTTON3_MASK);
									robot.mouseRelease(InputEvent.BUTTON3_MASK);
								}
							}
						}
						else{
							isR1=false;
							
						}
				
					break;
					
					case IDS.ID_R2:
						if(components[IDS.ID_R2].getPollData()==1){	
							if(isKeyboardOn){
								isKeyboardShiffting=true;
							}
						}
						else {
							if(isKeyboardOn){
								isKeyboardShiffting=false;
							}
							
							
						}
					break;
					
					case IDS.ID_L1:
						if(components[IDS.ID_L1].getPollData()==1){	
							if(isKeyboardOn){
								if(isShiftHold)
								keyboard.click(keyboard.getSelectedButton(1));
								else
									keyboard.click(keyboard.getSelectedButton(0));
							}
							else {
							
								robot.mousePress(InputEvent.BUTTON1_MASK);
								isL1=true;
															}
						}
						else {
							
							if(!isKeyboardOn&&isL1)
								robot.mouseRelease(InputEvent.BUTTON1_MASK);
							isL1=false;
							}
					break;
					
					case IDS.ID_L2:
						if(components[IDS.ID_L2].getPollData()==1){	
							if(isKeyboardOn){
								robot.keyPress(KeyEvent.VK_SHIFT);
								keyboard.setText(1);
								}
							else{
								l2=L2;
								System.out.println(l2);
							}
						}
						else {
							if(isKeyboardOn){
								robot.keyRelease(KeyEvent.VK_SHIFT);
								keyboard.setText(0);
							}
							else{
								l2=0;
							}
							
							
						}
					break;
					
					case IDS.ID_R3:
						if(components[IDS.ID_R3].getPollData()==1){	
							robot.mousePress(InputEvent.BUTTON1_MASK);
							
						}
						else if(components[IDS.ID_R3].getPollData()==-1){
							
						}
						
					break;
					
					case IDS.ID_SELECT:
						if(components[IDS.ID_SELECT].getPollData()==1){	
							System.exit(0);
						}
						else if(components[IDS.ID_SELECT].getPollData()==-1){
							
						}
					break;
					
					case IDS.ID_START:
						if(components[IDS.ID_START].getPollData()==1){	
						}
						else if(components[IDS.ID_START].getPollData()==-1){
							
						}
					break;
					
				}
			}
			try {
				if(isKeyboardOn)
					thread.sleep(150);
				else
					thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public boolean areAllComponentsInitialized(){//check whether the components were all initialized or not  
		for(int i=0;i<numberOfButtons;i++)
			if(components[i]==null)
				return false;
		return true;
	}
	public void initComponets(){//initialize the components array to buttons
		
		for(Component c:controller.getComponents()){
			if(c.getIdentifier()==Component.Identifier.Axis.Y)
				components[IDS.ID_DOWN]=c;

		else if(c.getIdentifier()==Component.Identifier.Axis.X){
			components[IDS.ID_LEFT]=c;
		 }
		}
		
		for(int i=2;i<numberOfButtons;i++){
			System.out.println("please press the "+IDS.IdToName(i));
			while(components[i]==null){
				controller.poll();
				for(Component c:controller.getComponents()){
					if(c.getPollData()==1){
						components[i]=c;
						break;
					}
				}
			}
		System.out.println(IDS.IdToName(i)+" is done");
		try {
			Thread.sleep(1000);
			} 
		catch (InterruptedException e) {
			e.printStackTrace();
			}
		}
	}
	
	
	
	public boolean initController(){//it will initialize the controller variable to the first valid available controller 
		for(Controller c:ControllerEnvironment.getDefaultEnvironment().getControllers())
			if(c.getType()== Controller.Type.GAMEPAD || c.getType()==Controller.Type.STICK){
				controller=c;
				return true;
			}
		return false;
	}
	public void printComponents(int id){
		controller.poll();
		System.out.println(IDS.IdToName(id)+":"+components[id].getPollData());
	}
	/*Setters              Getters */
	public int getL2(){
		return L2;
	}
	
	public int getMouseSpeed(){
		return mouseSpeed;
	}
	public void setMouseSpeed(int speed){
		mouseSpeed=speed;
		isMouseSpeedChanged=true;
		L2=10-mouseSpeed;
		
		
	}
	public void setNumberOfButtons(int n){
		this.numberOfButtons=n;
	}
	public int getNumberOfButtons(){
		return this.numberOfButtons;
	}
	
	public boolean isControllerNull(){
		if(controller!=null)
			return false;
		return true;
	}
	
	public static class IDS {
		/**
		 * This class is responsible of handling mapping between physical buttons and an array of components
		 * 
		 * 
		 *
		 */
		public static final int /**those constants are be used as keys for mapping between an array of components and the physical
		buttons */
		ID_LEFT=0,
		ID_DOWN=1,
		ID_TRIANGLE=2,
		ID_SQUARE=3,
		ID_CIRCLE=4,
		ID_X=5,
		ID_R1=6,
		ID_R2=7,
		ID_L1=8,
		ID_L2=9,
		ID_R3=10,
		ID_L3=11,
		ID_SELECT=12,
		ID_START=13
		;
		
		
		public static String IdToName(int id ){
			/***
			 * It gives the name of a component(button) using its id
			 */
			switch(id){
			case 0:return "LEFT BUTTON";
			case 1:return "DOWN BUTTON";
			case 2:return "TRIANGLE BUTTON";
			case 3:return "SQUARE BUTTON";
			case 4:return "CIRCLE BUTTON";
			case 5:return "X BUTTON";
			case 6:return "R1 BUTTON";
			case 7:return "R2 BUTTON";
			case 8:return "L1 BUTTON";
			case 9:return "L2 BUTTON";
			case 10:return "R3 BUTTON";
			case 11:return "L3 BUTTON";
			case 12:return "SELECT BUTTON";
			case 13:return "START BUTTON";
			default:return "NON-DEFINED BUTTON";
			}
		}

	}

	

	


}
