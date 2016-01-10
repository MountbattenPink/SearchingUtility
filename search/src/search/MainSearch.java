/**
 * @author Olha Shekera
 * @since 07.02.2015
 * @version 1.0
 * 
 * 	This program is quite modular. Version 1.0 expects only -f, directory and -p (optional)
 *  parameters that's why I used these static variables:
 *  
 *  @param  nameToSearch - value of parameter -f
 *  @param  textToSearch - value of parameter -p
 *  
 *  but if you want to scale program functionality, you can use instead 
 *  above mentioned variables arrays of necessary and unnecessary
 *  arguments and values as you can see at example:
 *  
 * 	@param private static String [] obligatoryArguments = {"-f"};
 *	@param private static String [] optionalArguments = {"-p"};
 *	@param private static String [] obligatoryValues;
 *	@param private static String [] optionalValues;	
 * 
 * 	doing in such way you also can change validate method replacing
 * 	some "if" checking expression by cycle
 */

package search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;




public class MainSearch {
	private static String textToSearch;
	private static String nameToSearch;
	private static String directoryPath;
	public static String output;

	//as modular as possible
	public static void main(String[] args) throws IOException {
		output="";
		validate(args);
		System.out.println(output);
		}
		
	
	/*calling this program user should enter such string: search -f value1 DIRECTORY <-p> <value2>
	  so in input args array first element is always -f, second - name to search, 3th - directory to search
	  after this (since 3 index) elements with odd indexes (starting with 0) always must be
	  parameters and with even indexes - values of these parameters. Even if we'll reuse 
	  this program and add new parameters with values into code, above mentioned rules will be the same.*/
	public static void validate (String[] args) throws IOException{
	
		//to match validation user should enter odd amount of arguments/values units
		if ((args.length%2==0)||(args.length<3)){
			output="Invalid amount of arguments and values"; return;
			}
		else 
			//if there some other parameters besides -f and -p
		for (int i=3;i<args.length;i+=2){
			if (!args[i].equals("-p")){
				output="Unexpected parameter detected"; return;
			}
		}
	
		
		//find -f parameter and responsible value
		if (!args[0].equals("-f")){
			output="Cannot find -f parameter or it is on wrong place"; return;}
		else {
			directoryPath=args[2];
			nameToSearch=args[Arrays.asList(args).indexOf("-f")+1];
			}
		
		//find -p parameter and responsible value
		if ((Arrays.asList(args).indexOf("-p")%2!=0)&&(Arrays.asList(args).indexOf("-p")!=-1)){
			directoryPath=args[2];
			textToSearch=args[Arrays.asList(args).indexOf("-p")+1];
			}	
		File directory = new File(directoryPath);
		findInName(directory);
	}
	
	
	//Recursive method to find file with matching name 
		public static void findInName (File currentDir) throws IOException{
			//if matches with regexp, try to find in content
			try{
			if (currentDir.isFile() && 
					Pattern.compile(nameToSearch).matcher(currentDir.getName()).find()){
				findInContent(currentDir);
				}
			else
			if (currentDir.isDirectory()){
				//go to subfolders/files in current folder
				for (File son: currentDir.listFiles())
					findInName(son);
				}}catch (PatternSyntaxException e){
					output="Can't compile regexp "; return;
				}
		}
		
		
		//line by line text searching
		public static void findInContent (File currentDir) throws IOException {
			if (textToSearch!=null){
			BufferedReader inputStream = null; 
			try { inputStream = new BufferedReader(new FileReader(currentDir));
	    	String s;
	    	Pattern pattern = Pattern.compile(textToSearch);
	    	     labelInsideFile:  
	    	    	 while ((s = inputStream.readLine()) != null) { 	
	    	    		 if (pattern.matcher(s).find()){
	    	    			 output+=currentDir.getPath()+"\n";
	    	    			 break labelInsideFile;
	    	    		 		}
	    	    	 	} 
	        } catch (PatternSyntaxException e){
				output="Can't compile regexp "; return;
			}
	        finally { if (inputStream != null) { inputStream.close(); } 
	        }       
			}
			//if parameter -p is absent just pring the file name
			else {
				output+=currentDir.getPath()+"\n";
			}
		}
	} 
		


