package JMouse;

import java.util.TimerTask;
/*
 * this timer is used to re-enable  a JMbutton after some time
 */
class TimerEnableButton extends TimerTask{
	JMButton b;
	TimerEnableButton(JMButton b){
		this.b=b;
	}
	@Override
	public void run() {
		b.isButtonReady=true;
		
	}
	
	
}