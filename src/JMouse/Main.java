package JMouse;

public class Main {

	public static void main(String[] args) {

		JMouse jmouse=new JMouse();
		new CLI(args, jmouse);
		
		jmouse.initController();
		if(jmouse.isControllerInitialized()==false){
			System.out.println("No Joystic was found ..\nTermnating");
			System.exit(0);
		}
		if(!jmouse.loadComponents()){
			jmouse.initComponets();
			jmouse.saveComponent();
		}
		jmouse.thread.start();
		while(true){
			for(int i=0;i<jmouse.getNumberOfButtons();i++){
				jmouse.printComponents(i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
