package JMouse;

import java.util.logging.Level;
import  java.util.logging.Logger;

import org.apache.commons.cli.*;;
public class CLI {
	private static final Logger log=Logger.getLogger(CLI.class.getName());
	private String[] args=null;
	private JMouse jm;
	
	private Options option =new Options();
	
	public CLI(String [] args ,JMouse jm ){
		for(int i=0;i<args.length;i++)
			System.out.println(args[i]);
		this.args=args;
		this.jm=jm;
		option.addOption("h",false,"Help");
		option.addOption("SM", true, "Set speed of mouse ");
		option.addOption("NB",true,"Set the number off buttons (14 by default)");
		option.addOption("RS",false,"reset data");
		
		parse();
	}
	public void parse(){
		CommandLineParser parser=new BasicParser();
		CommandLine cmd=null;
		try{
			cmd=parser.parse(option, args);
//			System.out.println("every thing is working sofar");
			if(cmd.hasOption("h")){
				help();
//				System.out.println("amm calling help");
			}
			if(cmd.hasOption("RS")){
				jm.reset();
//				System.out.println("amm calling help");
			}
			
			if(cmd.hasOption("SM")){
				try{
				jm.setMouseSpeed(Integer.parseInt(cmd.getOptionValue("SM")));
				
				}catch(Exception e){
					log.log(Level.SEVERE,"wrong argument",e);
				}
			}
			if(cmd.hasOption("NB")){
				try{
				jm.setNumberOfButtons(Integer.parseInt(cmd.getOptionValue("NB")));
				}catch(Exception e){
					log.log(Level.SEVERE,"wrong argument",e);
				}
			}
		} catch (ParseException e) {
			   log.log(Level.SEVERE, "Failed to parse comand line properties", e);
			   help();
		}
	}
	
	private void help(){
		HelpFormatter formater=new HelpFormatter();
		formater.printHelp("Main", option);
		System.exit(0);
	}
	
	
	
	
	
	

}
